package net.paulgray.mocklti2.tools

import com.fasterxml.jackson.databind.ObjectMapper
import javax.servlet.http.HttpServletRequest
import net.paulgray.mocklti2.tools.AgsGradebookController.CreateLineItemRequest
import net.paulgray.mocklti2.web.entities.LineItem
import org.apache.commons.io.IOUtils

import scala.util.Try

/**
 * A generalized data construct which can represent an CreateLineItemRequest from AGS
 * or a deprecated Outcomes2 LineItem request
 */
case class CreateColumnRequest(
  lineItem: CreateLineItemRequest Either LineItem,
  source: String
) {
  def title: String = lineItem.fold(_.label, _.getLabel.get("@value").asText())

  def scoreMaximum: java.math.BigDecimal = lineItem.fold(_.scoreMaximum, li => {
    new java.math.BigDecimal(li.getScoreConstraints
      .get("http://purl.imsglobal.org/vocab/lis/v2/outcomes#totalMaximum")
      .get("@value").asLong())
  })
}

object CreateColumnRequest {
  def fromRequest(om: ObjectMapper, req: HttpServletRequest): CreateColumnRequest = {
    val body = IOUtils.toString(req.getInputStream)

    CreateColumnRequest(
      asOutcomes2(om, body).map(Right(_))
        .orElse(asAGS(om, body).map(Left(_)))
        .getOrElse(throw new Exception(s"Could not parse line item body: $body")),
      body
    )
  }

  def asOutcomes2(om: ObjectMapper, body: String): Option[LineItem] =
  // heuristic to guess if we're dealing with outcomes2 or AGS
    if(body.contains("http://purl.imsglobal.org/vocab/lis/v2/outcomes#label"))
      Try(om.readValue(body, classOf[LineItem])).toOption
    else
      None

  def asAGS(om: ObjectMapper, body: String): Option[CreateLineItemRequest] =
      Try(om.readValue(body, classOf[CreateLineItemRequest])).toOption
}
