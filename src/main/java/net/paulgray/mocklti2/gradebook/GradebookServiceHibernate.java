package net.paulgray.mocklti2.gradebook;

import net.paulgray.mocklti2.tools.LtiTool;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Created by paul on 10/23/16.
 */
@Service
public class GradebookServiceHibernate implements GradebookService {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    @Transactional
    public Optional<Gradebook> getGradebook(String contextId) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Gradebook.class);
        crit.add(Restrictions.eq("context", contextId));
        return Optional.ofNullable((Gradebook) crit.uniqueResult());
    }

    @Override
    @Transactional
    public Gradebook addGradebook(String contextId) {
        sessionFactory.getCurrentSession().saveOrUpdate(new Gradebook(contextId));
        return this.getGradebook(contextId).get();
    }

    @Override
    @Transactional
    public GradebookLineItem addLineItem(GradebookLineItem lineItem) {
        sessionFactory.getCurrentSession().saveOrUpdate(lineItem);

        Criteria crit = sessionFactory.getCurrentSession().createCriteria(GradebookLineItem.class);
        crit.add(Restrictions.eq("id", lineItem.getId()));
        return (GradebookLineItem) crit.uniqueResult();
    }

    @Override
    @Transactional
    public GradebookCell addCell(GradebookCell cell) {
        sessionFactory.getCurrentSession().saveOrUpdate(cell);

        Criteria crit = sessionFactory.getCurrentSession().createCriteria(GradebookCell.class);
        crit.add(Restrictions.eq("id", cell.getId()));
        return (GradebookCell) crit.uniqueResult();
    }

    @Override
    public Gradebook updateGradebookCell(Integer contextId, String resultSourcedId, String grade) {
        return null;
    }
}
