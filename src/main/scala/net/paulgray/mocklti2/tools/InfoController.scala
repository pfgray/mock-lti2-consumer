package net.paulgray.mocklti2.tools

import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod}

/**
 * Created by nicole on 11/13/16.
 */
@Controller
class InfoController {

  @RequestMapping(value = Array("/api/info"), method = Array(RequestMethod.GET))
  def getTools: ResponseEntity[Any] = {
    new ResponseEntity[Any](526, HttpStatus.OK)
  }

}
