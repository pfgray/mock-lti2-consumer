package net.paulgray.mocklti2.tools

import net.paulgray.mocklti2.gradebook.Gradebook
import net.paulgray.mocklti2.tools.GradebooksService.{PagedResults, Page}

/**
 * Created by nicole on 11/22/16.
 */
trait GradebooksService {

  def getGradebooks(page: Page): java.util.List[Gradebook]

}

object GradebooksService {
  case class Page(offset: Int, limit: Int)
  case class PagedResults[T](page: Page, results: java.util.List[T])
}
