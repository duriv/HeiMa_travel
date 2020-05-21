package cn.itcast.web.servlet;

import cn.itcast.bean.PageBean;
import cn.itcast.bean.Route;
import cn.itcast.service.RouteService;
import cn.itcast.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RouteServlet",urlPatterns = "/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("PageSize");
        String cidStr = request.getParameter("cid");
        //2.处理参数
        //类别id
        int cid = 0;
        if (cidStr != null && cidStr.length()>0){
            cid = Integer.parseInt(cidStr);
        }
        //当前页码，如果不传递，则默认为第一页
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;
        }
        //每页显示条数，如果不传递，则每页显示5条记录
        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize = 5;
        }
        //3.调用servic查询pagebean对象
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize);
        //4.将pageBean对象序列化为json，返回
        writeValue(pb,response);
    }
}
