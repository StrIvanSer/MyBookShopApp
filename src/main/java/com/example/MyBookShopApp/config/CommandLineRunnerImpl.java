package com.example.MyBookShopApp.config;

import com.example.MyBookShopApp.data.TestEntity;
import com.example.MyBookShopApp.repo.TestEntityCrudRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.logging.Logger;

@Configuration
public class CommandLineRunnerImpl implements CommandLineRunner {

    TestEntityCrudRepository testEntityCrudRepository;

    @Autowired
    public CommandLineRunnerImpl(TestEntityCrudRepository testEntityCrudRepository) {
        this.testEntityCrudRepository = testEntityCrudRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 5; i++) {
            createTestEntity(new TestEntity());
        }

        TestEntity readTestEntity = readTestEntityById(3L);
        if (readTestEntity != null) {
            Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info(readTestEntity.toString());
        } else {
            throw new NullPointerException();
        }
    }

    private TestEntity updateTestEntityById(Long id) {
        TestEntity testEntity = testEntityCrudRepository.findById(id).get();
        testEntity.setData("123123123");
        return testEntityCrudRepository.save(testEntity);

    }


    private TestEntity readTestEntityById(Long id) {
        return testEntityCrudRepository.findById(id).get();

    }


    private void createTestEntity(TestEntity entity) {
        testEntityCrudRepository.save(entity);
    }

}
