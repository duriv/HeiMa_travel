package cn.itcast.web.servlet;

import cn.itcast.bean.PageBean;
import cn.itcast.bean.Route;
import cn.itcast.bean.User;
import cn.itcast.service.FavoriteService;
import cn.itcast.service.RouteService;
import cn.itcast.service.impl.FavoriteServiceImpl;
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
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    /**
     * 查询对象
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受参数
        //接收当前页
        String currentPageStr = request.getParameter("currentPage");
        //接收页面数据大小
        String pageSizeStr = request.getParameter("pageSize");
        //接收类型编号
        String cidStr = request.getParameter("cid");
        //接收rname线路名称
        String rname = request.getParameter("rname");
        //设置编码。
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        //2.处理参数
        //类别id
        int cid = 0;
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }
        //当前页码，如果不传递，则默认为第一页
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }
        //每页显示条数，如果不传递，则每页显示5条记录
        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }
        //3.调用servic查询pagebean对象
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize,rname);
        //4.将pageBean对象序列化为json，返回
        writeValue(pb,response);
    }

    /**
     * 根据id查询一个旅游线路的详细信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1接收id
        String rid = request.getParameter("rid");
        //2调用service查询route对象
        Route route = routeService.findOne(rid);
        //3转为json写回客户端
        writeValue(route,response);
    }

    /**
     * 判断当前登陆用户是否手收藏过该线路
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取线路id
        String rid = request.getParameter("rid");
        //2.获取当前登陆的用户user
        User user = (User) request.getSession().getAttribute("user");
        //用户id
        int uid;
        if (user == null) {
            //用户尚未登陆
            uid = 0;
        } else {
            //用户已经登录
            uid = user.getUid();
        }
        //调用FavoriteService查询是否收藏
        boolean flag = favoriteService.isFavorite(rid, uid);
        //写回客户端
        writeValue(flag, response);
    }

    /**
     * 增加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取线路rid
        String rid = request.getParameter("rid");
        //2.获取当前登陆用户
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null){
            return;
        }else {
            uid = user.getUid();
        }
        //3.调用service添加
        favoriteService.add(rid,uid);
    }
}