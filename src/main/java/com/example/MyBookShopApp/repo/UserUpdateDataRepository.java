package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.UserUpdateData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserUpdateDataRepository extends JpaRepository<UserUpdateData, Integer> {

    UserUpdateData findUserUpdateDataByToken(String token);

}
