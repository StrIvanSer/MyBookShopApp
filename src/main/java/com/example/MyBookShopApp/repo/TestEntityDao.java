package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.TestEntity;
import com.example.MyBookShopApp.repo.AbstractHibernateDao;
import org.springframework.stereotype.Repository;

@Repository
public class TestEntityDao extends AbstractHibernateDao<TestEntity> {

    public TestEntityDao(){
        super();
        setClazz(TestEntity.class);
    }

}
