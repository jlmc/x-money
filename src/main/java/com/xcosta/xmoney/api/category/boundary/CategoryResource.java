package com.xcosta.xmoney.api.category.boundary;

import com.xcosta.xmoney.api.category.control.CategoryRepository;
import com.xcosta.xmoney.api.category.entity.Category;
import com.xcosta.xmoney.api.event.ResourceCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    private final CategoryRepository categoryRepository;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public CategoryResource(CategoryRepository categoryRepository, ApplicationEventPublisher publisher) {
        this.categoryRepository = categoryRepository;
        this.publisher = publisher;
    }

    @GetMapping
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Category> save(@RequestBody @Valid Category category, HttpServletResponse response) {
        Category categorySaved = this.categoryRepository.save(category);

        /*URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(categorySaved.getCode()).toUri();
        response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.created(uri).body(categorySaved);
        */

        this.publisher.publishEvent(new ResourceCreatedEvent(this, response, category));
        return ResponseEntity.status(HttpStatus.CREATED).body(categorySaved);
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
