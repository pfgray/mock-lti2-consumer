package net.paulgray.mocklti2.tools

import javax.transaction.Transactional

import net.paulgray.mocklti2.gradebook.Gradebook
import net.paulgray.mocklti2.tools.GradebooksService.{Page, PagedResults}
import org.hibernate.criterion.Projections
import org.hibernate.transform.{DistinctResultTransformer, ResultTransformer}
import org.hibernate.{Criteria, SessionFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._

/**
 * Created by nicole on 11/22/16.
 */
@Service
class HibernateGradebooksService extends GradebooksService {

  @Autowired
  var sessionFactory: SessionFactory = null

  @Transactional
  override def getPagedGradebooks(page: Page): PagedResults[Gradebook] = {
    val crit = sessionFactory.getCurrentSession.createCriteria(classOf[Gradebook])
    crit.setPage(page)
    val gbs = crit.list().asInstanceOf[java.util.List[Gradebook]].asScala.toList
    PagedResults(page, crit.count(), gbs)
  }

  @Transactional
  override def getColumns(courseId: String, page: Page): PagedResults[GradebookLineItem] = {
    sessionFactory.getCurrentSession.createCriteria(classOf[GradebookLineItem])
      .setFetchMode("gradebook", FetchMode.JOIN)
      .add(Restrictions.eq("gradebook.context", courseId))
      .toResults[GradebookLineItem](page)
  }

  @Transactional
  override def deleteGradebook(id: Integer): Unit = {
    val sess = sessionFactory.getCurrentSession
    sess.delete(new Gradebook(id))
  }

  implicit class CriteriaOps(c: Criteria) {

    final val PageSize: Int = 10

    def count(): Long = {
      c.setFirstResult(0)
      c.setMaxResults(0)
      c.setProjection(Projections.rowCount)
      c.uniqueResult().asInstanceOf[java.lang.Long]
    }

    def setPage(page: Page): Criteria = {
      page.selector match {
        case Offset(i) => c.setFirstResult(i)
        case PageNumber(n) => c.setFirstResult(n * page.limit)
      }
      c.setMaxResults(page.limit)
    }

    def toResults[T](page: Page): PagedResults[T] = {
      val items = c.setPage(page).list().asInstanceOf[java.util.List[T]].asScala.toList
      val total = c.count()
      PagedResults(page, total, items)
    }
  }

}
