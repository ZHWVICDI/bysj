package com.qinhan.videoblog.service.impl;

import com.qinhan.videoblog.dal.CommentRepo;
import com.qinhan.videoblog.dal.UserRepo;
import com.qinhan.videoblog.dal.model.Comment;
import com.qinhan.videoblog.dal.model.User;
import com.qinhan.videoblog.service.CommentService;
import com.qinhan.videoblog.web.modelvo.CommentForm;
import com.qinhan.videoblog.web.modelvo.ReplyCommentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepo commentRepo;
    @Autowired
    UserRepo userRepo;
    @Override
    public boolean sendComment(CommentForm commentForm) {
        Comment comment=new Comment();
        comment.setBlogId(commentForm.getVideoId());
        comment.setContent(commentForm.getComment());
        comment.setLevel(commentForm.getLevel());
        comment.setUserId(commentForm.getPosterId());
        comment.setNickname(commentForm.getNickname());
        comment.setReplyId(commentForm.getReplyId());
        comment.setReplyNickname(commentForm.getReplyNickname());
        comment.setPid(commentForm.getPid());
        commentRepo.save(comment);
        return true;
    }

    @Override
    public List<Comment> getAllCommentBy(Integer videoId) {
        Comment comment=new Comment();
        comment.setBlogId(videoId);
        Example<Comment>  example=Example.of(comment);
        List<Comment> comments = commentRepo.findAll(example);
        return comments;
    }

    @Override
    public void sendReply(ReplyCommentForm replyCommentForm) {
        Comment comment=new Comment();
        comment.setBlogId(replyCommentForm.getVideoId());
        comment.setUserId(replyCommentForm.getPosterId());
        comment.setReplyId(replyCommentForm.getReplyId());
        comment.setContent(replyCommentForm.getReplycontent());
        comment.setPid(replyCommentForm.getpId());
        comment.setLevel(replyCommentForm.getLevel());
        comment.setNickname(replyCommentForm.getNickname());
        /*获取被回复人姓名*/
        User replyUser=userRepo.findById(replyCommentForm.getReplyId()).get();
        comment.setReplyNickname(replyUser.getUsername());
        commentRepo.save(comment);
    }
}
