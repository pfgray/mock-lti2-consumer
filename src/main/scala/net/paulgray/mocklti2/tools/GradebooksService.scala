package net.paulgray.mocklti2.tools

import net.paulgray.mocklti2.gradebook.Gradebook
import net.paulgray.mocklti2.tools.GradebooksService.{PagedResults, Page}

/**
 * Created by nicole on 11/22/16.
 */
trait GradebooksService {

  def getPagedGradebooks(page: Page): PagedResults[Gradebook]

  def deleteGradebook(id: Integer): Unit

}

object GradebooksService {
  case class Page(offset: Int, limit: Int)
  case class PagedResults[T](page: Page, total: Long, results: Seq[T])
}
