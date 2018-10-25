package net.paulgray.mocklti2.tools

import net.paulgray.mocklti2.gradebook.{Gradebook, GradebookLineItem}
import net.paulgray.mocklti2.tools.GradebooksService.{Page, PagedResults}

/**
 * Created by nicole on 11/22/16.
 */
trait GradebooksService {

  def getPagedGradebooks(page: Page): PagedResults[Gradebook]

  def getColumns(context: String, page: Page): PagedResults[GradebookLineItem]

  def deleteGradebook(id: Integer): Unit

}

object GradebooksService {

  final val DefaultPageSize = 10

  sealed trait PageSelector
  case class Offset(i: Int) extends PageSelector
  case class PageNumber(i: Int) extends PageSelector

  case class Page(selector: PageSelector, limit: Int = 10)

  case class PagedResults[T](page: Page, total: Long, results: List[T])

  object PagedResults {
    def empty[T]: PagedResults[T] = new PagedResults(Page(Offset(0)), 0, List())
  }
}
