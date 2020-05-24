package cn.itcast.service.impl;

import cn.itcast.bean.User;
import cn.itcast.dao.UserDao;
import cn.itcast.dao.impl.UserDaoImpl;
import cn.itcast.service.UserService;
import cn.itcast.utils.MailUtils;
import cn.itcast.utils.Md5Util;
import cn.itcast.utils.UuidUtil;

import java.math.BigInteger;
import java.security.MessageDigest;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();
    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        //1.根据用户名查询用户信息
        User u = dao.findByUsername(user.getUsername());
        //2.保存用户信息
        //判断用户名是否为空
        if (u != null){
            //用户名存在，注册失败
            return false;
        }
        //2.保存用户信息
        //2.1设置激活码，唯一字符串
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N");
        dao.save(user);
        //3.激活邮件发送，邮件正文
        String content = "<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活【 黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }


    /**
     * 激活用户
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = dao.findByCode(code);
        if (user != null){
            //2.调用dao的修改激活状态的方法
            dao.updateStatus(user);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 登陆方法
     * 调用dao层，传入对象的用户名和密码
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        String password = null;
        try {
            password = Md5Util.encodeByMd5(user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dao.findByUsernameAndPassword(user.getUsername(),password);
    }

    @Override
    public boolean findA(User user) {
        String password=null;
        try {
            password = Md5Util.encodeByMd5(user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        User byUsernameAndPassword = dao.findByUsernameAndPassword(user.getUsername(),password);
        if (byUsernameAndPassword!=null){
            return true;
        }
        return false;
    }

    @Override
    public boolean upss(User user) {
        dao.upss(user);
        return true;
    }


}
