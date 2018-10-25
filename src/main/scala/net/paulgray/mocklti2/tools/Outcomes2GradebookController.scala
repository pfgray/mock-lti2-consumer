package net.paulgray.mocklti2.tools

import jdk.management.resource.ResourceId
import net.paulgray.mocklti2.gradebook.{Gradebook, GradebookLineItem}
import net.paulgray.mocklti2.tools.GradebooksService.{Page, PageNumber, PagedResults}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.{PathVariable, RequestMapping, RequestMethod, RequestParam}

class Outcomes2GradebookController {

  case class LineItem()

  @Autowired
  var gbService: GradebooksService = null

  @RequestMapping(value = Array("/api/ags/{contextId}/lineitems"), method = Array(RequestMethod.GET))
  def getGradebooks(
    @PathVariable("contextId") contextId: String,
    @RequestParam(value = "limit", defaultValue = "10") limit: Int,
    @RequestParam(value = "page", defaultValue = "0") pageNumber: Int,
    @RequestParam(value = "lti_link_id") ltiLinkId: String,
    @RequestParam(value = "tag") tag: String,
    @RequestParam(value = "resource_id") resourceId: String
  ): ResponseEntity[PagedResults[GradebookLineItem]] = {
    val columns = gbService.getColumns(contextId, Page(PageNumber(pageNumber), limit))
    // new ResponseEntity(gradebooksService.getPagedGradebooks(Page(offset, limit)), HttpStatus.OK)
    new ResponseEntity(columns, HttpStatus.OK)
  }

}
