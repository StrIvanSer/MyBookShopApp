package com.example.MyBookShopApp.services;


import com.example.MyBookShopApp.data.Book.Tag;
import com.example.MyBookShopApp.repo.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    public Tag getTag(Integer tagId) {
        return tagRepository.getOne(tagId);
    }
}
