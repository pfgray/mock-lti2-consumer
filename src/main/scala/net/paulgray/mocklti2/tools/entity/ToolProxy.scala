package net.paulgray.mocklti2.tools.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
  * @author pgray
  */
@JsonIgnoreProperties(ignoreUnknown = true)
case class ToolProxy(
  //    id: String,
  lti_version: String,
  //    tool_consumer_profile: String,

  security_contract: SecurityContract,

  tool_profile: ToolProfile
)

@JsonIgnoreProperties(ignoreUnknown = true)
case class SecurityContract(
  shared_secret: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
case class ProductInstance(
  guid: String,
  product_info: ProductInfo
)

@JsonIgnoreProperties(ignoreUnknown = true)
case class ProductInfo(
  product_name: KeyedValue,
  description: KeyedValue
)

@JsonIgnoreProperties(ignoreUnknown = true)
case class ToolProfile(
  product_instance: ProductInstance,
  base_url_choice: Seq[BaseUrlChoice],
  resource_handler: Seq[ResourceHandler]
)

@JsonIgnoreProperties(ignoreUnknown = true)
case class ResourceHandler(
  resource_type: ResourceType,
  resource_name: KeyedValue,
  description: KeyedValue,
  message: Seq[Message],
  icon_info: Seq[IconInfo]
)

@JsonIgnoreProperties(ignoreUnknown = true)
case class ResourceType(code: String)

@JsonIgnoreProperties(ignoreUnknown = true)
case class Message(
  message_type: String,
  path: String,
  enabled_capability: Seq[String],
  parameter: Seq[String]
)

@JsonIgnoreProperties(ignoreUnknown = true)
case class BaseUrlChoice(
  default_base_url: String,
  secure_base_url: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
case class KeyedValue(
  default_value: String,
  key: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
case class IconInfo(
  default_location: Path,
  key: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
case class Path(
  path: String
)
