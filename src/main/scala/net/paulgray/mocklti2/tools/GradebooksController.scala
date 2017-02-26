package net.paulgray.mocklti2.tools

import net.paulgray.mocklti2.gradebook.{Gradebook, GradebookService}
import net.paulgray.mocklti2.tools.GradebooksService.{Page, PagedResults}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{PathVariable, RequestMapping, RequestMethod, RequestParam}

/**
 * Created by nicole on 11/22/16.
 */
@Controller
class GradebooksController {

  @Autowired
  var gradebooksService: GradebooksService = null

  @RequestMapping(value = Array("/api/gradebooks"), method = Array(RequestMethod.GET))
  def getGradebooks(
    @RequestParam(value = "limit", defaultValue = "10") limit: Int,
    @RequestParam(value = "offset", defaultValue = "0") offset: Int
  ): ResponseEntity[PagedResults[Gradebook]] = {
    new ResponseEntity(gradebooksService.getPagedGradebooks(Page(offset, limit)), HttpStatus.OK)
  }

  @RequestMapping(value = Array("/api/gradebooks/{gradebookId}"), method = Array(RequestMethod.DELETE))
  def deleteGradebook(
    @PathVariable(value = "gradebookId") id: Integer
  ): ResponseEntity[Void] = {
    gradebooksService.deleteGradebook(id)
    ResponseEntity.accepted().build()
  }

}
