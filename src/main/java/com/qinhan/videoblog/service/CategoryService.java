package com.qinhan.videoblog.service;

import com.qinhan.videoblog.dal.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories();

    Category getCategoryForCurVideoBlog(Integer categoryId);
}
