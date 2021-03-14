package com.example.MyBookShopApp.repo.test;

import com.example.MyBookShopApp.data.test.TestEntity;
import com.example.MyBookShopApp.repo.AbstractHibernateDao;
import org.springframework.stereotype.Repository;

@Repository
public class TestEntityDao extends AbstractHibernateDao<TestEntity> {

    public TestEntityDao(){
        super();
        setClazz(TestEntity.class);
    }

}
