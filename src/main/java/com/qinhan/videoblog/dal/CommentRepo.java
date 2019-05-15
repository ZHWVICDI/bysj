package com.qinhan.videoblog.dal;

import com.qinhan.videoblog.dal.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
}
