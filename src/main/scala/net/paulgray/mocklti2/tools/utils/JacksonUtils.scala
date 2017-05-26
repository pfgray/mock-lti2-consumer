package net.paulgray.mocklti2.tools.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

/**
  * @author pgray
  */
object JacksonUtils {

  def mapper: ObjectMapper with ScalaObjectMapper = {
    val m = new ObjectMapper() with ScalaObjectMapper
    m.registerModule(DefaultScalaModule)
    m
  }


}
