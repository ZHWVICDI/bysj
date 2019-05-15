package com.qinhan.videoblog.service.impl;

import com.qinhan.videoblog.dal.CategoryRepo;
import com.qinhan.videoblog.dal.model.Category;
import com.qinhan.videoblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepo categoryRepo;
    @Override
    public List<Category> getCategories() {
        List<Category> categories=categoryRepo.findAllByDepth(1);
        if(categories.size()==0||categories==null){
            throw new RuntimeException("FIRST_CATEGORY_GET_ERROR");
        }
        return categories;
    }

    @Override
    public Category getCategoryForCurVideoBlog(Integer categoryId) {
        Optional<Category> category = categoryRepo.findById(categoryId);
        if(!category.isPresent()){
            throw new RuntimeException("CATEGORY_NOT_FOUND");
        }
        return category.get();
    }
}
