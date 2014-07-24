/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.paulgray.mocklti2.tools;

import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author paul
 */
@Service
public class LtiToolServiceHibernate implements LtiToolService {
    
    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public LtiTool getToolForId(String id) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(LtiTool.class);
        crit.add(Restrictions.eq("id", id));
        return (LtiTool) crit.uniqueResult();
    }

    @Transactional
    public List<LtiTool> getAll() {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(LtiTool.class);
        return crit.list();
    }
    
}
