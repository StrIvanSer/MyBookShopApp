package com.example.MyBookShopApp.services;


import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.Tag;
import com.example.MyBookShopApp.repo.BookRepository;
import com.example.MyBookShopApp.repo.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
