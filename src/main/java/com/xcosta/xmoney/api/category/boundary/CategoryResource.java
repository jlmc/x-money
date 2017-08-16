package com.xcosta.xmoney.api.category.boundary;

import com.xcosta.xmoney.api.category.control.CategoryRepository;
import com.xcosta.xmoney.api.category.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/echo")
    public String echo() {
        return "categories - " + System.currentTimeMillis();
    }


}
