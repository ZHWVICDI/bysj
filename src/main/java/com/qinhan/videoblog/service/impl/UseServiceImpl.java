package com.qinhan.videoblog.service.impl;

import com.qinhan.videoblog.dal.UserRepo;
import com.qinhan.videoblog.dal.model.User;
import com.qinhan.videoblog.service.UserService;
import com.qinhan.videoblog.web.modelvo.UserModifyForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UseServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;
    @Override
    public boolean isUserNameExist(String username) {
      User user=  userRepo.findByUsername(username);
      if(user==null){
          return false;
      }
      return true;
    }

    @Override
    public void registerUser(User user) {
        //注册用户
        userRepo.save(user);
    }

    @Override
    public String getPicturePath(String username) {
        return null;
    }

    @Override
    public boolean checkUserInfo(String username, String password) {
        User user=userRepo.findByUsernameAndPassword(username,password);
        if(user!=null){
            return true;
        }
        return false;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    /**
     * 修改用户信息
     * @param userModifyForm
     * @param username
     */
    @Override
    public void modifyUserInfo(UserModifyForm userModifyForm, String username) {
        User user=userRepo.findByUsername(username);
        if(user==null){
            throw new RuntimeException("USER_NOTFOUND");
        }
        try{
            user.setHeadingUrl(userModifyForm.getHeadingUrl());
            user.setEmail(userModifyForm.getEmail());
            user.setQq(userModifyForm.getQq());
            user.setTelephone(userModifyForm.getTelephone());
            user.setAge(userModifyForm.getAge());
            user.setUserAbout(userModifyForm.getUserAbout());
            user.setBirthday(userModifyForm.getBirthday());
        }catch(Exception e){
            throw new RuntimeException("MODIFY_ERROR");
        }
        userRepo.save(user);
    }

    @Override
    public User getUserById(Integer userId) {
        User user=userRepo.findById(userId).get();
        if(user==null){
            throw new RuntimeException("BLOG_USER_NOT_FOUND");
        }
        return user;
    }
}
