package net.paulgray.mocklti2.tools

import javax.persistence._

import com.fasterxml.jackson.annotation.{JsonIgnore, JsonProperty}
import net.paulgray.mocklti2.tools.entity.Message
import net.paulgray.mocklti2.tools.utils.JacksonUtils

/**
  * @author pgray
  */
@Entity
@Table(name = "resource_handlers")
class LtiToolResourceHandler {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private var id: Integer = null

  @JsonIgnore
  @JoinColumn(name = "tool_proxy")
  @ManyToOne(fetch = FetchType.EAGER)
  private var toolProxy: LtiToolProxy = null

  @Column(name = "code") private var code: String = null
  @Column(name = "description") private var description: String = null
  @Column(name = "name") private var name: String = null
  @Column(name = "icon") private var icon: String = null
  @Column(name = "messages") private var messages: String = null

  def getId: Integer = id

  def setId(id: Integer) {
    this.id = id
  }

  def getToolProxy: LtiToolProxy = toolProxy

  def setToolProxy(toolProxy: LtiToolProxy) {
    this.toolProxy = toolProxy
  }

  def getCode: String = code

  def setCode(code: String) {
    this.code = code
  }

  def getDescription: String = description

  def setDescription(description: String) {
    this.description = description
  }

  def getName: String = name

  def setName(name: String) {
    this.name = name
  }

  def getIcon: String = icon

  def setIcon(icon: String) {
    this.icon = icon
  }

  @JsonIgnore
  def getMessages: String = messages

  @JsonProperty("messages")
  def deserializeMessages: Seq[Message] =
    JacksonUtils.mapper.readValue[Seq[Message]](messages)

  def setMessages(messages: String) {
    this.messages = messages
  }

  def updateMessages(messages: Seq[Message]): Unit = {
    this.messages = JacksonUtils.mapper.writeValueAsString(messages)
  }
}
