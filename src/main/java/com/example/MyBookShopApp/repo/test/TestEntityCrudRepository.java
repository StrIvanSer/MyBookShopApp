package com.example.MyBookShopApp.repo.test;

import com.example.MyBookShopApp.data.test.TestEntity;
import org.springframework.data.repository.CrudRepository;

public interface TestEntityCrudRepository extends CrudRepository<TestEntity,Long> {
}
