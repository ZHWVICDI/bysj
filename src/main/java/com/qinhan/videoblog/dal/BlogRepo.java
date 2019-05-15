package com.qinhan.videoblog.dal;

import com.qinhan.videoblog.dal.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepo extends JpaRepository<Blog,Integer> {
    Blog findByVideoUrl(String videopath);

   /* Page<Blog> findAllByUserId(int userId);*/

    /*List<Blog> findByCategory(Category category);*/
}
