package cn.itcast.web.filter;

import cn.itcast.bean.User;
import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;

@WebFilter(filterName = "AutoLoginFilter",urlPatterns = "/user/login")
public class AutoLoginFilter implements Filter {
    @Override
    public void destroy() {
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse)resp;
        HttpServletRequest request = (HttpServletRequest)req;
        //流程
        //1如果已登录，不进行自动登录
        User loginuser = (User) request.getAttribute("user");
        if (loginuser!=null){
            chain.doFilter(req, resp);
            return;
        }
        //2如果浏览器没有自动登录cookie，不进行自动登录

        //3如果cookie不正确，不自动登录。
        Cookie[] cookies = request.getCookies();
        String cusername = null;
        String cpassword = null;
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if("password".equals(cookie.getName())) {
                    cpassword = cookie.getValue();
                }else if("username".equals(cookie.getName())) {
                    cusername = cookie.getValue();
                }
            }
        }

        //判断cookie是否为空
        if(cusername==null || cpassword==null) {
            chain.doFilter(request, response);
            return;//结束程序
        }
        //封装用户
        User user = new User();
        //这里处理一下servlet中URLEncode用户名的中文
        user.setUsername(URLDecoder.decode(cusername, "UTF-8"));
        user.setPassword(cpassword);
        //调用service登录获得用户实体
        UserService us = new UserServiceImpl();
        User login = null;
            login = us.login(user);

        if(login!=null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", login);

        }

        //4如果没有自动登录且cookie信息正确，进行自动登录，将得到的用户存放到session中。

        chain.doFilter(req, resp);
    }
    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
