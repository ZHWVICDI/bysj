package com.qinhan.videoblog.dal;

import com.qinhan.videoblog.dal.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category,Integer> {

    List<Category> findAllByDepth(int depth);
}
