package net.paulgray.mocklti2.tools

import javax.transaction.Transactional

import net.paulgray.mocklti2.gradebook.Gradebook
import net.paulgray.mocklti2.tools.GradebooksService.{PagedResults, Page}
import org.hibernate.SessionFactory
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
  override def getGradebooks(page: Page): PagedResults[Gradebook] = {
    val crit = sessionFactory.getCurrentSession.createCriteria(classOf[Gradebook])
    crit.setFirstResult(page.offset)
    crit.setMaxResults(page.limit)
    PagedResults(page, crit.list().asInstanceOf[java.util.List[Gradebook]].asScala)
  }
}
