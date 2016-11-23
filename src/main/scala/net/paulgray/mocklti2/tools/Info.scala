package net.paulgray.mocklti2.tools

import com.fasterxml.jackson.annotation.JsonProperty

/**
  * Created by paul on 11/23/16.
  */
case class Info(@JsonProperty(value = "wut") processors: Int, freeMemory: Long)
