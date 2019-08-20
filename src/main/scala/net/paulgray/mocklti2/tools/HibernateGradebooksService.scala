package net.paulgray.mocklti2.tools

import java.util.Optional

import javax.transaction.Transactional
import net.paulgray.mocklti2.gradebook.{Gradebook, GradebookCell, GradebookLineItem}
import net.paulgray.mocklti2.tools.GradebooksService.{Offset, Page, PageNumber, PagedResults}
import net.paulgray.mocklti2.web.entities.Result
import org.hibernate.criterion.{CriteriaSpecification, Order, Projections, Restrictions}
import org.hibernate.transform.{DistinctResultTransformer, ResultTransformer}
import org.hibernate.{Criteria, FetchMode, Query, SessionFactory}
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

  override def getGradebook(contextId: String): Option[Gradebook] = {
    Option(sessionFactory.getCurrentSession.createCriteria(classOf[Gradebook])
      .add(Restrictions.eq("context", contextId))
      .uniqueResult()).map(_.asInstanceOf[Gradebook])
  }

  @Transactional
  override def getPagedGradebooks(page: Page): PagedResults[Gradebook] = {
    val crit = sessionFactory.getCurrentSession.createCriteria(classOf[Gradebook])
    crit.setPage(page)
    val count = crit.count()
    crit.setProjection(null)
    crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY)
    crit.addOrder(Order.desc("created"))
    val gbs = crit.list().asInstanceOf[java.util.List[Gradebook]].asScala.toList
    PagedResults(page, count, gbs)
  }

  @Transactional
  override def getPagedCells(page: Page, lineItemId: Integer): PagedResults[GradebookCell] =
    sessionFactory.getCurrentSession.createCriteria(classOf[GradebookCell])
      .add(Restrictions.eq("gradebookLineItemId", lineItemId))
      .toResults[GradebookCell](page)

  @Transactional
  override def getCell(lineItemId: Integer, studentId: String): Option[GradebookCell] =
    sessionFactory.getCurrentSession.createCriteria(classOf[GradebookCell])
      .add(Restrictions.eq("gradebookLineItemId", lineItemId))
      .add(Restrictions.eq("studentId", studentId))
      .toResult[GradebookCell]

  @Transactional
  override def createOrUpdateCell(gradebookCell: GradebookCell): Unit =
    sessionFactory.getCurrentSession.saveOrUpdate(gradebookCell)

  @Transactional
  override def getColumns(gradebook: Gradebook, page: Page): PagedResults[GradebookLineItem] =
    sessionFactory.getCurrentSession.createCriteria(classOf[GradebookLineItem])
      .add(Restrictions.in("gradebookId", List(gradebook.getId).asJava))
      .toResults[GradebookLineItem](page)

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

    def toResult[T]: Option[T] = {
      Option(c.uniqueResult()).map(_.asInstanceOf[T])
    }
  }

  implicit class QueryOps(q: Query) {

    def setPage(page: Page): Query = {
      page.selector match {
        case Offset(i) => q.setFirstResult(i)
        case PageNumber(n) => q.setFirstResult(n * page.limit)
      }
      q.setMaxResults(page.limit)
    }

    def toResults[T](page: Page, countQuery: Query): PagedResults[T] = {
      val items = q.setPage(page).list().asInstanceOf[java.util.List[T]].asScala.toList
      val total = countQuery.uniqueResult().asInstanceOf[java.lang.Long]
      PagedResults(page, total, items)
    }
  }

}
