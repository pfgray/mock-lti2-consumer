package net.paulgray.mocklti2.gradebook;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Gradebook getOrCreateGradebook(String contextId) {
        return getGradebook(contextId).orElseGet(() -> addGradebook(contextId));
    }

    @Override
    @Transactional
    public Gradebook addGradebook(String contextId) {
        sessionFactory.getCurrentSession().saveOrUpdate(new Gradebook(contextId));
        return this.getGradebook(contextId).get();
    }

    @Override
    @Transactional
    public Optional<List<GradebookLineItem>> getGradebookLineItems(Integer gradebookId) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(GradebookLineItem.class);
        crit.add(Restrictions.eq("gradebookId", gradebookId));
        return Optional.of(crit.list());
    }

    @Override
    @Transactional
    public Optional<GradebookLineItem> getGradebookLineItemByResourceId(Integer gradebookId, String resourceId) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(GradebookLineItem.class);

        crit.add(Restrictions.eq("gradebookId", gradebookId));
        crit.add(Restrictions.eq("resourceLinkId", resourceId));
        return Optional.ofNullable((GradebookLineItem) crit.uniqueResult());
    }

    @Override
    @Transactional
    public GradebookLineItem getOrCreateGradebookLineItemByResourceId(Integer gradebookId, String resourceId, String source) {
        return getGradebookLineItemByResourceId(gradebookId, resourceId)
                .orElseGet(() -> addLineItem(new GradebookLineItem(gradebookId, resourceId, source)));
    }

    @Override
    @Transactional
    public GradebookLineItem updateLineItem(GradebookLineItem lineItem) {
        sessionFactory.getCurrentSession().update(lineItem);

        Criteria crit = sessionFactory.getCurrentSession().createCriteria(GradebookLineItem.class);
        crit.add(Restrictions.eq("id", lineItem.getId()));
        return (GradebookLineItem) crit.uniqueResult();
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
    public Map<Integer, List<GradebookCell>> getGradebookCells(List<Integer> columnIds) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(GradebookCell.class);
        crit.add(Restrictions.in("gradebookLineItemId", columnIds));
        List<GradebookCell> cells = crit.list();

        return columnIds.stream().collect(Collectors.toMap(
                c -> c,
                c -> cells.stream().filter(cell -> cell.getGradebookLineItemId() == c).collect(Collectors.toList())
        ));
    }

    @Override
    @Transactional
    public Optional<GradebookCell> getGradebookCell(Integer lineItemId, String studentId) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(GradebookCell.class);
        crit.add(Restrictions.eq("gradebookLineItemId", lineItemId));
        crit.add(Restrictions.eq("studentId", studentId));
        return Optional.ofNullable((GradebookCell) crit.uniqueResult());
    }

    @Override
    @Transactional
    public GradebookCell getOrCreateGradebookCell(Integer lineItemId, String studentId, String source) {
        return getGradebookCell(lineItemId, studentId).orElseGet(() -> addCell(new GradebookCell(lineItemId, studentId, null, source)));
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
    @Transactional
    public GradebookCell updateGradebookCell(GradebookCell cell) {
        sessionFactory.getCurrentSession().saveOrUpdate(cell);

        Criteria crit = sessionFactory.getCurrentSession().createCriteria(GradebookCell.class);
        crit.add(Restrictions.eq("id", cell.getId()));
        return (GradebookCell) crit.uniqueResult();
    }

}
