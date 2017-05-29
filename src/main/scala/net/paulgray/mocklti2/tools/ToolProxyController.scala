package net.paulgray.mocklti2.tools

import java.lang.Boolean
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import com.github.jsonldjava.core.{JsonLdOptions, JsonLdProcessor}
import com.github.jsonldjava.utils.JsonUtils
import net.paulgray.mocklti2.tools.{GradebooksService, ToolProxyService}
import net.paulgray.mocklti2.web.HttpUtils
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.exception.ExceptionUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.{PathVariable, RequestMapping, RequestMethod, RequestParam}
import java.util.{UUID, Map => JMap}

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.{Module, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import net.paulgray.mocklti2.tools.entity.{ToolProxy, ToolProxyReceipt}
import net.paulgray.mocklti2.tools.utils.JacksonUtils
import org.imsglobal.lti2.objects.provider.BaseUrlChoice

import scala.collection.JavaConversions
import scala.collection.JavaConverters._
import scala.util.control.Exception.Described

/**
  * @author pgray
  */
@Controller
class ToolProxyController {

  case class ToolProxyRequestResult(success: Boolean, proxy: Option[Any])

  @Autowired
  var toolProxyService: ToolProxyService = null

  @Autowired
  var ltiToolService: LtiToolService = null

  @RequestMapping(value = Array("/api/toolProxyCheck"), method = Array(RequestMethod.GET))
  def toolProxyCheck(
    @RequestParam(value = "toolurl") toolUrl: String
  ): ResponseEntity[ToolProxyRequestResult] = {
    // get a url
    try {

      val options = new JsonLdOptions()
      val proxy = JsonUtils.fromReader(io.Source.fromURL(toolUrl).reader())

      val compactProxy = JsonLdProcessor.compact(proxy, Map().asJava, options)

      new ResponseEntity(
        ToolProxyRequestResult(true, Some(compactProxy)), HttpStatus.OK)
    } catch {
      case e: Exception =>
        new ResponseEntity(
          ToolProxyRequestResult(false, None), HttpStatus.OK)
    }
  }

  @RequestMapping(value = Array("/registerTool"), method = Array(RequestMethod.GET))
  def registerTool(
    @RequestParam(value = "toolurl") toolUrl: String,
    request: HttpServletRequest, model: ModelMap
  ): String = {
    val regRequest = toolProxyService.createToolRegistrationRequest(toolUrl)
    // lti_message_type, reg_key, reg_password, tc_profile_url, launch_presentation_return_url
    val origin = HttpUtils.getOrigin(request).orElse("")
    val params = Map(
      "lti_message_type" -> "ToolProxyRegistrationRequest",
      "reg_key" -> regRequest.getKey,
      "reg_password" -> regRequest.getSecret,
      "tc_profile_url" -> s"$origin/api/tcp?registrationGuid=${regRequest.getGuid}",
      "launch_presentation_return_url" -> s"$origin/toolProxyRegistrationReceipt?registrationGuid=${regRequest.getGuid}",
      "lti_version" -> "LTI-2p0"
    )

    model.put("url", toolUrl)
    model.put("params", JavaConversions.mapAsJavaMap(params))

    "autoPost"
  }

  @RequestMapping(value = Array("/api/tcp"), method = Array(RequestMethod.GET))
  def toolConsumerProfile(
    @RequestParam(value = "registrationGuid") registrationGuid: String,
    req: HttpServletRequest,
    resp: HttpServletResponse
  ): Unit = {
    // todo: validate oauth signature

    // we might tie certain information to the registration guid,
    // for example, if an admin wanted to limit the capabilitis_offered for
    // a specific tool
    // for now, we'll serve the same one for everybody
    val origin = HttpUtils.getOrigin(req).orElse("")

    val tcpStream = getClass().getClassLoader().getResourceAsStream("json/ToolConsumerProfile.json")
    val tcpString = IOUtils.toString(tcpStream)

    val interpolate = interpolator(Map(
      "origin" -> origin,
      "registrationGuid" -> registrationGuid
    ))(_)

    IOUtils.write(interpolate(tcpString), resp.getOutputStream)
  }

  @RequestMapping(value = Array("/api/toolProxies"), method = Array(RequestMethod.POST))
  def createToolProxy(
    @RequestParam(value = "registrationGuid") registrationGuid: String,
    req: HttpServletRequest,
    resp: HttpServletResponse
  ): ResponseEntity[ToolProxyReceipt] = {
    try {

      val registrationRequest = toolProxyService.getToolRegistrationRequest(registrationGuid)

      val proxyString = IOUtils.toString(req.getInputStream)

      val toolProxy = JacksonUtils.mapper.readValue[ToolProxy](proxyString)

      val secret = toolProxy.security_contract.shared_secret

      val baseUrlChoice = toolProxy.tool_profile.base_url_choice.head

      // todo: this assumes only basicltilaunch, add support for more types of launches?
      val launchPath = toolProxy.tool_profile.resource_handler.head.message.head.path

      resp.setStatus(HttpStatus.OK.value())

      val key = UUID.randomUUID().toString

      // TODO: let's handle all these resources...
      val tp = toolProxyService.addToolProxy(
        key,
        secret,
        Option(baseUrlChoice.default_base_url),
        Option(baseUrlChoice.secure_base_url),
        toolProxy.tool_profile.product_instance.product_info.product_name.default_value,
        toolProxy,
        registrationRequest
      )

      val tpUrl = s"${HttpUtils.getOrigin(req)}/api/toolProxies/${tp.getId}"

      new ResponseEntity[ToolProxyReceipt](ToolProxyReceipt(tpUrl, key), HttpStatus.CREATED)
    } catch {
      case e: Exception =>
        e.printStackTrace()
        new ResponseEntity[ToolProxyReceipt](HttpStatus.INTERNAL_SERVER_ERROR)
    }
  }

  @RequestMapping(value = Array("/toolProxyRegistrationReceipt"), method = Array(RequestMethod.GET))
  def registrationReceipt(
    @RequestParam(value = "registrationGuid") registrationGuid: String,
    @RequestParam(value = "status", required = false) status: String,
    request: HttpServletRequest, model: ModelMap
  ): String = {
    //get the registration request
    // find the tool for this registration request?
    val reg = toolProxyService.getToolRegistrationRequest(registrationGuid)
    val proxy = reg.getToolProxy

    val success = status == ""

    model.put("proxyJson", JacksonUtils.mapper.writeValueAsString(proxy))
    model.put("proxy", proxy)
    model.put("success", new Boolean(success))

    "registrationReceipt"
  }

  @RequestMapping(value = Array("/api/toolProxies/{id}"), method = Array(RequestMethod.GET))
  def getToolProxy(
    @RequestParam(value = "registrationGuid") registrationGuid: String
  ): ResponseEntity[ToolProxy] = {
    new ResponseEntity[ToolProxy](HttpStatus.OK)
  }

  def interpolator(map: Map[String, String])(str: String) = {
    map.foldLeft(str){ (str, entry) =>
      val (key, value) = entry
      str.replaceAll("\\$" + entry._1 + "\\$", entry._2)
    }
  }

}
