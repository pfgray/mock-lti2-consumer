package net.paulgray.mocklti2.tools

import java.util.UUID
import javax.transaction.Transactional

import net.paulgray.mocklti2.gradebook.Gradebook
import net.paulgray.mocklti2.tools.GradebooksService.PagedResults
import net.paulgray.mocklti2.tools.entity.ToolProxy
import net.paulgray.mocklti2.tools.utils.JacksonUtils
import org.hibernate.SessionFactory
import org.hibernate.criterion.{Criterion, Restrictions}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
  * @author pgray
  */
@Service
class HibernateToolProxyService extends ToolProxyService {

  @Autowired
  var sessionFactory: SessionFactory = null

  @Transactional
  override def createToolRegistrationRequest(registrationUrl: String): ToolRegistrationRequest = {
    val req = new ToolRegistrationRequest(rand, rand, rand)
    req.setRegistrationUrl(registrationUrl)
    sess.save(req)
    req
  }

  private def sess = sessionFactory.getCurrentSession

  private def rand = UUID.randomUUID().toString

  @Transactional
  override def getToolRegistrationRequest(guid: String): ToolRegistrationRequest = {
    val crit = sess.createCriteria(classOf[ToolRegistrationRequest])
    crit.add(Restrictions.eq("guid", guid))
    crit.uniqueResult().asInstanceOf[ToolRegistrationRequest]
  }

  @Transactional
  override def addToolProxy(
    key: String,
    secret: String,
    defaultUrl: Option[String],
    secureUrl: Option[String],
    label: String,
    toolProxy: ToolProxy,
    registrationRequest: ToolRegistrationRequest
  ): LtiToolProxy = {

    val tool = new LtiTool()
    tool.setLabel(label)
    tool.setState(LtiTool.State.registered)
    // tool.setLatestToolProxySubmission(toolProxy)

    sess.save(tool)

    val proxy = new LtiToolProxy()
    proxy.setKey(key)
    proxy.setSecret(secret)
    defaultUrl foreach proxy.setDefaultUrl
    secureUrl foreach proxy.setSecureUrl
    proxy.setTool(tool)
    proxy.setRegistrationRequest(registrationRequest)
    proxy.setActive(false)
    sess.save(proxy)

    toolProxy.tool_profile.resource_handler.foreach(h => {
      val handler = new LtiToolResourceHandler()
      handler.setCode(h.resource_type.code)
      handler.setDescription(h.description.default_value)
      handler.setIcon(h.icon_info.headOption.map(p => s"${defaultUrl.get}${p.default_location.path}").getOrElse(null))
      handler.setName(h.resource_name.default_value)
      handler.setToolProxy(proxy)
      handler.updateMessages(h.message)
      sess.save(handler)
    })

    proxy
  }
}
