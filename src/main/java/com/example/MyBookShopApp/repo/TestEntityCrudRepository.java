package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.TestEntity;
import org.springframework.data.repository.CrudRepository;

public interface TestEntityCrudRepository extends CrudRepository<TestEntity,Long> {
}
