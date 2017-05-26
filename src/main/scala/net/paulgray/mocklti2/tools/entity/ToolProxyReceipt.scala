package net.paulgray.mocklti2.tools.entity

import com.fasterxml.jackson.annotation.JsonProperty

/**
  * @author pgray
  */
case class ToolProxyReceipt (
  @JsonProperty("@context") _context: String = "http://purl.imsglobal.org/ctx/lti/v2/ToolProxyId",
  @JsonProperty("@type") _type: String = "ToolProxy",
  @JsonProperty("@id") _id: String,
  tool_proxy_guid: String
)

object ToolProxyReceipt {
  val MimeType = "application/vnd.ims.lti.v2.toolproxy.id+json"

  def apply(id: String, guid: String): ToolProxyReceipt =
    new ToolProxyReceipt(_id = id, tool_proxy_guid = guid)
}