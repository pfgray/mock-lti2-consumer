package net.paulgray.mocklti2.tools

import net.paulgray.mocklti2.tools.entity.ToolProxy

/**
  * @author pgray
  */
trait ToolProxyService {

  def createToolRegistrationRequest(): ToolRegistrationRequest

  def getToolRegistrationRequest(guid: String): ToolRegistrationRequest

  def addToolProxy(
    key: String,
    secret: String,
    defaultUrl: Option[String],
    secureUrl: Option[String],
    label: String,
    toolProxy: ToolProxy
  ): LtiToolProxy

}
