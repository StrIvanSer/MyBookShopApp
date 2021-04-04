package com.example.MyBookShopApp.controllers.Rest;

import com.example.MyBookShopApp.data.book.Author;
import com.example.MyBookShopApp.services.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@Api(tags = {"Authors data"})
@Tag(name = "Authors data", description = "Сервис управления авторами")
public class AuthorsRestApiController {

    private final AuthorService authorService;

    @Autowired
    public AuthorsRestApiController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ApiOperation("get author by id")
    @GetMapping("/api/author/{authors_id:\\d+}")
    public Author getAuthor(@PathVariable Integer authors_id) {
        return authorService.getAuthorsById(authors_id);
    }


    @ApiOperation("method to get map of authors")
    @GetMapping("/api/authors")
    public Map<String, List<Author>> authors() {
        return authorService.getAuthorsInAlphabetOrder();
    }

}
