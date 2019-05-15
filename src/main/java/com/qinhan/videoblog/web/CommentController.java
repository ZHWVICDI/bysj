package com.qinhan.videoblog.web;

import com.qinhan.videoblog.dal.model.Comment;
import com.qinhan.videoblog.dal.model.User;
import com.qinhan.videoblog.service.CommentService;
import com.qinhan.videoblog.service.UserService;
import com.qinhan.videoblog.web.modelvo.CommentForm;
import com.qinhan.videoblog.web.modelvo.ReplyCommentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    @RequestMapping("/comment/send")
    @ResponseBody
    public List<Comment> sendComment(HttpSession session, CommentForm commentForm, ModelMap modelMap){
        //得到videoid-用户id--用户名
        // TODO: 2019/5/11 判断user是否为空，如果为空返回主页，提示用户应该登陆，最后加一个过滤】
        String username= (String) session.getAttribute("username");
        User user=userService.findUserByUsername(username);
        commentForm.setLevel(1);
        commentForm.setPosterId(user.getUserId());
        commentForm.setNickname(username);
        commentForm.setPid(0);
        boolean result=commentService.sendComment(commentForm);
        List<Comment> commentList=commentService.getAllCommentBy(commentForm.getVideoId());
        return commentList;
    }

    @RequestMapping("/comment/reply")
    @ResponseBody
    public List<Comment> sendReply(HttpSession session, ReplyCommentForm replyCommentForm){
        String username= (String) session.getAttribute("username");
        User user=userService.findUserByUsername(username);
        replyCommentForm.setLevel(2);
        replyCommentForm.setPosterId(user.getUserId());
        replyCommentForm.setNickname(username);
        commentService.sendReply(replyCommentForm);
        List<Comment> commentList=commentService.getAllCommentBy(replyCommentForm.getVideoId());
        return commentList;
    }

    /*加载评论*/
    @RequestMapping("/comment/load")
    @ResponseBody
    public List<Comment> loadComments(@NumberFormat Integer videoId){
        List<Comment> allCommentBy = commentService.getAllCommentBy(videoId);
        System.out.println(allCommentBy);
        return allCommentBy;
    }
}
