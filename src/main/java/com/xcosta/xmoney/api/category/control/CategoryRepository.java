package com.xcosta.xmoney.api.category.control;

import com.xcosta.xmoney.api.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
