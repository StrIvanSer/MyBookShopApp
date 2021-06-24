package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.tag.Tag;
import com.example.MyBookShopApp.data.tag.TagCountI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query(value = "SELECT id, COUNT(id) AS size, name " +
            "FROM tag  " +
            "JOIN book2tag AS b ON b.tag_id = tag.id " +
            "GROUP BY id", nativeQuery = true)
    List<TagCountI> getTagSize();

}
