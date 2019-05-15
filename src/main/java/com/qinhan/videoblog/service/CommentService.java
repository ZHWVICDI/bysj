package com.qinhan.videoblog.service;

import com.qinhan.videoblog.dal.model.Comment;
import com.qinhan.videoblog.web.modelvo.CommentForm;
import com.qinhan.videoblog.web.modelvo.ReplyCommentForm;

import java.util.List;

public interface CommentService {
    boolean sendComment(CommentForm commentForm);

    List<Comment> getAllCommentBy(Integer videoId);

    void sendReply(ReplyCommentForm replyCommentForm);
}
