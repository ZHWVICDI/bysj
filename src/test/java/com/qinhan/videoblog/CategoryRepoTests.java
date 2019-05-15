package com.qinhan.videoblog;

import com.qinhan.videoblog.dal.BlogRepo;
import com.qinhan.videoblog.dal.CategoryRepo;
import com.qinhan.videoblog.dal.UserRepo;
import com.qinhan.videoblog.dal.model.Blog;
import com.qinhan.videoblog.dal.model.Category;
import com.qinhan.videoblog.dal.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CategoryRepoTests {
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    BlogRepo blogRepo;

    @Test
    public void testgetCates(){
        List<Category> categories=categoryRepo.findAllByDepth(1);
        categories.forEach(x->System.out.println(x));
    }

    @Test
    public void testgetBlogId(){
        User user=userRepo.findByUsername("zhwvicdi");
        Blog videoblog=new Blog();
        videoblog.setUserId(user.getUserId());
        videoblog.setVideoUrl("测试链接1");
        videoblog.setCoverUrl("测试链接2");
        videoblog.setDuration("00:00:00:000");
        videoblog.setBlogContent("默认博客发表内容-这个博主有点懒");
        videoblog.setBlogInfo("默认博客简介-这个博主有点懒");
        videoblog.setBlogTitle("默认博客标题-这个博主有点懒");
        blogRepo.save(videoblog);
        int blogId=blogRepo.findByVideoUrl("测试链接1").getId();
        System.out.println("blogId:"+blogId);
    }

    @Test
    public void testgetPageByUserId(){
        User user=userRepo.findByUsername("zhwvicdi");
        Blog blog=new Blog();
        blog.setUserId(7);
        Example<Blog> example=Example.of(blog);/*
        ExampleMatcher matcher=ExampleMatcher.matching()
                .withMatcher("userId",7)*/
        Sort sort=new Sort(Sort.Direction.DESC,"createDate");
        Pageable pageable= PageRequest.of(0,10,sort);
        Page<Blog> blogs=blogRepo.findAll(example,pageable);
        blogs.getContent().forEach(x->System.out.println("blog :"+x));
    }
}
