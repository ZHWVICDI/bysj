package com.qinhan.videoblog.dal;

import com.qinhan.videoblog.dal.model.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogCategoryRepo  extends JpaRepository<BlogCategory,Integer> {
}
