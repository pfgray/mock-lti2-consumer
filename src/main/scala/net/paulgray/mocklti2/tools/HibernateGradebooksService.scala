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
    crit.setFirstResult(page.offset)
    crit.setMaxResults(page.limit)
    val gbs = crit.list().asInstanceOf[java.util.List[Gradebook]].asScala
    PagedResults(page, crit.count(), gbs)
  }

  implicit class CriteriaOps(c: Criteria) {
    def count(): Long = {
      c.setProjection(Projections.rowCount)
      c.uniqueResult().asInstanceOf[java.lang.Long]
    }
  }
}
