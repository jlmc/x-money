package com.jcosta.xmoney.api.category.control;

import com.jcosta.xmoney.api.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
