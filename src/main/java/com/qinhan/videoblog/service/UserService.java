package com.qinhan.videoblog.service;
import com.qinhan.videoblog.dal.model.User;
import com.qinhan.videoblog.web.modelvo.UserModifyForm;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    boolean isUserNameExist(String username);

    void registerUser(User user);

    String getPicturePath(String username);

    boolean checkUserInfo(String username, String password);

    User findUserByUsername(String username);

    /**
     * 修改用户信息
     * @param userModifyForm
     * @param username
     */
    void modifyUserInfo(UserModifyForm userModifyForm, String username);

    User getUserById(Integer userId);

    User updateHeadingUrl(MultipartFile headingUrl,String savePath,String username);
}
