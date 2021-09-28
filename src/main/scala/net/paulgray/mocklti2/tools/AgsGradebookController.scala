package net.paulgray.mocklti2.tools

import java.time.Instant
import java.util.{Date, Optional, UUID}
import javax.servlet.http.HttpServletRequest
import javax.transaction.Transactional
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.ObjectMapper
import net.paulgray.mocklti2.MockLti2App
import net.paulgray.mocklti2.gradebook.{Gradebook, GradebookCell, GradebookLineItem, GradebookService}
import net.paulgray.mocklti2.tools.GradebooksService.{Page, PageNumber, PagedResults}
import net.paulgray.mocklti2.tools.AgsGradebookController.{ActivityProgress, CreateLineItemRequest, LineItem, UpdateScoreRequest}
import net.paulgray.mocklti2.web.HttpUtils
import org.apache.commons.logging.{Log, LogFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation._

import scala.compat.java8.OptionConverters._
import scala.util.Try

@Controller
class AgsGradebookController {

  import HttpStatus._
  import net.paulgray.mocklti2.tools.AgsGradebookController.AnyOps

  private val log = LogFactory.getLog(this.getClass)

  @Autowired
  var gradebookService: GradebookService = null

  @Autowired
  var gbService: GradebooksService = null

  @Autowired
  var ob: ObjectMapper = null

  @Transactional
  @RequestMapping(value = Array("/api/ags/{contextId}/lineitems"), method = Array(RequestMethod.GET))
  def getLineItems(
    @PathVariable("contextId") contextId: String,
    @RequestParam(value = "limit", defaultValue = "10") limit: Int,
    @RequestParam(value = "page", defaultValue = "0") pageNumber: Int,
    @RequestParam(value = "lti_link_id") ltiLinkId: Optional[String],
    @RequestParam(value = "tag") tag: Optional[String],
    @RequestParam(value = "resource_id") resourceId: Optional[String]
  ): ResponseEntity[_] =
    gbOrNotFound(contextId) { gb =>
      val columns = gbService.getColumns(gb, Page(PageNumber(pageNumber), limit))
      new ResponseEntity(columns, HttpStatus.OK)
    }

  @Transactional
  @RequestMapping(value = Array("/api/ags/{contextId}/lineitems"), method = Array(RequestMethod.POST))
  def createLineItem(
    req: HttpServletRequest,
    @PathVariable("contextId") contextId: String
  ): ResponseEntity[_] = {
    val body = CreateColumnRequest.fromRequest(MockLti2App.standardMapper, req)
    gbOrNotFound(contextId) { gb =>
      val resourceId = UUID.randomUUID().toString
      val li = gradebookService.getOrCreateGradebookLineItemByResourceLinkId(gb.getId, resourceId, body.source)
      li.setTitle(body.title)
      li.setScoreMaximum(Option(body.scoreMaximum).getOrElse(new java.math.BigDecimal(0)))
      body.lineItem match {
        case Left(createLineItemRequest) =>
          new ResponseEntity(LineItem(li, contextId, HttpUtils.getOrigin(req).get()), HttpStatus.OK)
        case Right(lineItem) =>
          val origin = HttpUtils.getOrigin(req).orElse("")
          val resultsUrl = origin + "/outcomes/v2.0/gradebook/" + contextId + "/lineitems/" + resourceId
          lineItem.setResultsUrl(Optional.of(resultsUrl))
          new ResponseEntity(lineItem, HttpStatus.OK)
      }

    }
  }

  @Transactional
  @RequestMapping(value = Array("/api/ags/{contextId}/lineitems/{lineItemId}"), method = Array(RequestMethod.GET))
  def getLineItem(
    req: HttpServletRequest,
    @PathVariable("contextId") contextId: String,
    @PathVariable("lineItemId") lineItemId: String
  ): ResponseEntity[_] =
    gbOrNotFound(contextId) { gb =>
      liOrError(gb.getId, lineItemId) { li =>
        new ResponseEntity(LineItem(li, contextId, HttpUtils.getOrigin(req).get()), HttpStatus.OK)
      }
    }

  @Transactional
  @RequestMapping(value = Array("/api/ags/{contextId}/lineitems/{lineItemId}"), method = Array(RequestMethod.PUT))
  def updateLineItem(
    req: HttpServletRequest,
    @PathVariable("contextId") contextId: String,
    @PathVariable("lineItemId") lineItemId: String,
    @RequestBody lineItem: CreateLineItemRequest
  ): ResponseEntity[_] =
    gbOrNotFound(contextId) { gb =>
      liOrError(gb.getId, lineItemId) { li =>
        li.setScoreMaximum(lineItem.scoreMaximum)
        li.setTitle(lineItem.label)
        li.setSource(ob.writeValueAsString(lineItem))
        gradebookService.updateLineItem(li)
        new ResponseEntity(LineItem(li, contextId, HttpUtils.getOrigin(req).get()), HttpStatus.OK)
      }
    }

  @Transactional
  @RequestMapping(value = Array("/api/ags/{contextId}/lineitems/{lineItemId}"), method = Array(RequestMethod.DELETE))
  def deleteLineItem(
    @PathVariable("contextId") contextId: String,
    @PathVariable("lineItemId") lineItemId: String,
    @RequestBody lineItem: CreateLineItemRequest
  ): ResponseEntity[_] =
    gbOrNotFound(contextId) { gb =>
      liOrError(gb.getId, lineItemId) { li =>
        gradebookService.deleteLineItem(li)
        new ResponseEntity("", HttpStatus.NO_CONTENT)
      }
    }

  @Transactional
  @RequestMapping(value = Array("/api/ags/{contextId}/lineitems/{lineItemId}/results"), method = Array(RequestMethod.GET))
  def getResults(
    @PathVariable("contextId") contextId: String,
    @PathVariable("lineItemId") lineItemId: String,
    @RequestParam(value = "limit", defaultValue = "10") limit: Int,
    @RequestParam(value = "page", defaultValue = "0") pageNumber: Int
  ): ResponseEntity[_] =
    gbOrNotFound(contextId) { gb =>
      liOrError(gb.getId, lineItemId) { li =>
        // TODO: map these to "results"
        gbService.getPagedCells(Page(PageNumber(pageNumber), limit), li.getId).resp(HttpStatus.OK)
      }
    }

  @Transactional
  @RequestMapping(value = Array("/api/ags/{contextId}/lineitems/{lineItemId}/scores"), method = Array(RequestMethod.POST))
  def updateScore(
    @PathVariable("contextId") contextId: String,
    @PathVariable("lineItemId") lineItemId: String,
    @RequestParam(value = "limit", defaultValue = "10") limit: Int,
    @RequestParam(value = "page", defaultValue = "0") pageNumber: Int,
    @RequestBody score: UpdateScoreRequest
  ): ResponseEntity[_] =
    gbOrNotFound(contextId) { gb =>
      liOrError(gb.getId, lineItemId) { li =>
        log.info(s"Got AGS Score for student: ${score.userId} with score: ${score.scoreGiven}")
        score.scoreGiven match {
          case Some(s) =>
            val existing = gbService.getCell(li.getId, score.userId)
            val grade = s.toString// s"${score.scoreGiven}/${score.scoreMaximum}:${score.activityProgress}:${score.gradingProgress}"
            val source = ob.writeValueAsString(score)
            val toSave = existing match {
              case Some(g) =>
                g.setSource(source)
                g.setGrade(grade)
                g
              case None =>
                new GradebookCell(li.getId, score.userId, grade, ob.writeValueAsString(score))
            }
            log.info(s"Attempting to insert grade into gradebook: $contextId into column: $lineItemId")
            gbService.createOrUpdateCell(toSave)

          case None =>
            log.info(s"Attempting to delete grade from gradebook: $contextId for column: $lineItemId");
            gbService.deleteCell(li.getId, score.userId)
        }
        score.resp(HttpStatus.OK)
      }
    }

  def gbOrNotFound(contextId: String)(f: Gradebook => ResponseEntity[_]): ResponseEntity[_] =
    gbService.getGradebook(contextId).fold[ResponseEntity[_]](
      new ResponseEntity(s"Gradebook for context with id: $contextId not found.", HttpStatus.NOT_FOUND)
    )(f)

  def liOrError(gradebookId: Integer, lineItemId: String)(f: GradebookLineItem => ResponseEntity[_]): ResponseEntity[_] =
    Try(Integer.valueOf(lineItemId)).toOption.fold[ResponseEntity[_]](s"Line item with id: $lineItemId is not an integer...".resp(NOT_ACCEPTABLE)) { lineItemId =>
      gradebookService.getGradebookLineItemById(gradebookId, lineItemId).asScala.fold[ResponseEntity[_]](s"Line item with id: $lineItemId not found".resp(NOT_FOUND)) {
        f(_)
      }
    }

}

object AgsGradebookController {

  sealed trait GradingProgress
  case object NotReady extends GradingProgress
  case object Failed extends GradingProgress
  case object Pending extends GradingProgress
  case object PendingManual extends GradingProgress
  case object FullyGraded extends GradingProgress

  sealed trait ActivityProgress
  case object Initialized extends ActivityProgress
  case object Started extends ActivityProgress
  case object InProgress extends ActivityProgress
  case object Submitted extends ActivityProgress
  case object Completed extends ActivityProgress

  case class UpdateScoreRequest(
    userId: String,
    scoreGiven: Option[java.math.BigDecimal],
    scoreMaximum: java.math.BigDecimal,
    comment: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    timestamp: Date,
    activityProgress: String,
    gradingProgress: String
  )

  case class CreateLineItemRequest(
    scoreMaximum: java.math.BigDecimal,
    label: String,
    tag: Option[String],
    resourceId: String
  )

  case class LineItem(
    id: String,
    scoreMaximum: BigDecimal,
    label: String,
    tag: Option[String],
    resourceId: String,
    resourceLinkId: String
  )

  object LineItem {
    def apply(gbLi: GradebookLineItem, contextId: String, origin: String) = {
      val id = s"${origin}/api/ags/${contextId}/lineitems/${gbLi.getId}"
      new LineItem(id, gbLi.getScoreMaximum, gbLi.getTitle, None, gbLi.getResourceLinkId, gbLi.getResourceLinkId)
    }
  }

  implicit class AnyOps[T](t: T) {
    def resp(h: HttpStatus): ResponseEntity[T] = {
      new ResponseEntity[T](t, h)
    }
  }

}
