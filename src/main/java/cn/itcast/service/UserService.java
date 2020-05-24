package cn.itcast.service;

import cn.itcast.bean.User;

public interface UserService {
    /**
     * 注册用户
     * @param user
     * @return
     */
    boolean regist(User user);


    boolean active(String code);

    User login(User user);

    boolean findA(User user);

    boolean upss(User user);
}
