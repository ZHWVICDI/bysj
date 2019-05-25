package com.qinhan.videoblog.service.impl;

import com.qinhan.videoblog.dal.UserRepo;
import com.qinhan.videoblog.dal.model.User;
import com.qinhan.videoblog.service.MediaService;
import com.qinhan.videoblog.service.UserService;
import com.qinhan.videoblog.web.common.DateUtil;
import com.qinhan.videoblog.web.common.VideoConstants;
import com.qinhan.videoblog.web.modelvo.UserModifyForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class UseServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    MediaService mediaService;
    /**
     * 判断用户名是否存在
     *
     * @param username
     * @return
     */
    @Override
    public boolean isUserNameExist(String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            return false;
        }
        return true;
    }

    /**
     * 注册用户，将用户信息存入数据库
     *
     * @param user
     */
    @Override
    public void registerUser(User user) {
        //设置默认头像
        user.setHeadingUrl("default.jpg");
        //注册用户
        userRepo.save(user);
    }

    @Override
    public String getPicturePath(String username) {
        return null;
    }

    /**
     * 判断用户是否存在
     *
     * @param username 用户名
     * @param password 用户密码
     * @return
     */
    @Override
    public boolean checkUserInfo(String username, String password) {
        User user = userRepo.findByUsernameAndPassword(username, password);
        if (user != null) {
            if(user.getState().equals("FROZEN")){
                throw new RuntimeException("USERACCOUNT_FROZEN");
            }
            return true;
        }
        return false;
    }

    /**
     * 管理员登陆
     * @param username
     * @param password
     * @return
     */
    public boolean checkSuperUserInfo(String username, String password) {
        User user = userRepo.findByUsernameAndPassword(username, password);
        if (user != null) {
            return true;
        }
        return false;
    }

    /**
     * 通过用户名找到用户并返回
     *
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    /**
     * 修改用户信息
     *
     * @param userModifyForm
     * @param username
     */
    @Override
    public void modifyUserInfo(UserModifyForm userModifyForm, String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("USER_NOTFOUND");
        }
        try {
            user.setHeadingUrl(userModifyForm.getHeadingUrl());
            user.setEmail(userModifyForm.getEmail());
            user.setQq(userModifyForm.getQq());
            user.setTelephone(userModifyForm.getTelephone());
            user.setAge(userModifyForm.getAge());
            user.setUserAbout(userModifyForm.getUserAbout());
            user.setBirthday(userModifyForm.getBirthday());
        } catch (Exception e) {
            throw new RuntimeException("MODIFY_ERROR");
        }
        userRepo.save(user);
    }

    /**
     * 通过id获取用户，并返回
     *
     * @param userId
     * @return
     */
    @Override
    public User getUserById(Integer userId) {
        User user = userRepo.findById(userId).get();
        if (user == null) {
            throw new RuntimeException("BLOG_USER_NOT_FOUND");
        }
        return user;
    }

    /**
     * 更改用户头像
     * //将headingUrl的文件进行转换处理，然后将其存入指定文件目录中，然后持久化到上传用户表中。
     */
    @Override
    public User updateHeadingUrl(MultipartFile headingImage, String savepath, String username) {
        //如果文件不为空，写入上传路径
        if (!headingImage.isEmpty()) {
            //获得上传文件的保存路径
            String path = savepath;
            //获取上传文件名并加入时间前缀作为头像url
            String filename = DateUtil.formatLong(new Date()) + headingImage.getOriginalFilename();
            String headingurl = path + filename;
            File filepath = new File(path, filename);

            //判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();//创建一个目录树
            }
            //将上传文件保存到目标目录当中
            try {
                headingImage.transferTo(new File(path + File.separator + filename));
            } catch (IOException e) {
                throw new RuntimeException("FILE_UPLOAD_ERROR");
            }
            String headingOriImagePath=  path+File.separator+filename;//上传原始文件存放的路径
            String headingImagePath= VideoConstants.HEADING_PIC_PATH+File.separator+filename;
            try{
                mediaService.processHeadingImage(headingOriImagePath,headingImagePath);
            }catch (RuntimeException e){
                if(e.getMessage()!=null&&e.getMessage().equals("CUT_IMAGE_FAILED")){
                    throw new RuntimeException("CUT_IMAGE_FAILED");
                }
            }

            /*持久化*/
            User user = userRepo.findByUsername(username);
            user.setHeadingUrl(filename);
            userRepo.save(user);
            return user;
        }else {
            return null;
        }

    }

    /**
     * 获取到所有用户信息
     * @param page
     * @param size
     * @return
     */
    @Override
        public Page<User> getAllUsers(Integer page, Integer size) {
        Sort sort=new Sort(Sort.Direction.ASC,"userId");
        Pageable pageable= PageRequest.of(page-1,size,sort);
        Page<User> users=userRepo.findAll(pageable);
        return users;
    }

    /**
     * 通过id删除用户
     * @param userId
     */
    @Override
    public void deleteUserById(Integer userId) {
        userRepo.deleteById(userId);
    }

    @Override
    public void changeUserState(Integer userId, String state) {
        User user=userRepo.findById(userId).get();
        user.setState(state);
        userRepo.save(user);
    }
}
