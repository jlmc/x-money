package com.xcosta.xmoney.api.category.boundary;

import com.xcosta.xmoney.api.category.control.CategoryRepository;
import com.xcosta.xmoney.api.category.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryResource(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Category> save(@RequestBody @Valid Category category, HttpServletResponse response) {
        Category categorySaved = this.categoryRepository.save(category);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(categorySaved.getCode()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(categorySaved);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Category> getByCode(@PathVariable Long code) {
        return Optional.ofNullable(this.categoryRepository.findOne(code))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/echo")
    public ResponseEntity<?> echo() {
        return ResponseEntity.ok("categories - " + System.currentTimeMillis());
    }


}
