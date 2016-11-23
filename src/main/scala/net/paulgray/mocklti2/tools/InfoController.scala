package net.paulgray.mocklti2.tools

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.context.ApplicationContext
import org.springframework.http.{HttpStatus, MediaType, ResponseEntity}
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 * Created by nicole on 11/13/16.
 */
@Controller
class InfoController {

  @RequestMapping(value = Array("/api/info"), method = Array(RequestMethod.GET))
  def getInfo: ResponseEntity[Info] = {
    new ResponseEntity[Info](Info(
      Runtime.getRuntime().availableProcessors(),
      Runtime.getRuntime().freeMemory()
    ), HttpStatus.OK)
  }


}
