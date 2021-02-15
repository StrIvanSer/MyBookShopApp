package com.example.MyBookShopApp.repo;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;

@Resource
public abstract class AbstractHibernateDao<T> {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    public Class<T> clazz;

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T findOne(Long id) {
        return getSession().find(clazz, id);
    }

    public Session getSession() {
        return entityManagerFactory.createEntityManager().unwrap(Session.class);
    }
}
