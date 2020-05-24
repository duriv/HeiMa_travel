package cn.itcast.service.impl;

import cn.itcast.bean.PageBean;
import cn.itcast.bean.Route;
import cn.itcast.bean.RouteImg;
import cn.itcast.bean.Seller;
import cn.itcast.dao.FavoriteDao;
import cn.itcast.dao.RouteDao;
import cn.itcast.dao.RouteImgDao;
import cn.itcast.dao.SellerDao;
import cn.itcast.dao.impl.FavoriteDaoImpl;
import cn.itcast.dao.impl.RouteDaoImpl;
import cn.itcast.dao.impl.RouteImgDaoImpl;
import cn.itcast.dao.impl.SellerDaoImpl;

import cn.itcast.service.FavoriteService;
import cn.itcast.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 分页处理
     * @param cid 分类编号
     * @param currentPage 当前页面
     * @param pageSize  页面显示数量
     * @param rname 名称
     * @return
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        //封装PageBean
        PageBean<Route> pb = new PageBean<Route>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);
        //设置总记录数，调用dao层查询页面的总条数
        int totalCount = routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);
        //设置当前页显示的数据集合
        //当前页开始的记录数
        int start = (currentPage - 1) * pageSize;
        //调用dao层，查询当前页需展示内容
        List<Route> list = routeDao.findByPage(cid, start, pageSize,rname);
        pb.setList(list);
        //设置总页数 = 总记录数/每页显示条数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize :(totalCount/pageSize)+1;
        pb.setTotalPage(totalPage);

        return pb;
    }

    /**
     * 查询摸一个值去查询其他参数
     * @param rid
     * @return
     */
    @Override
    public Route findOne(String rid) {
        //1。调用dao层,根据id去route表中查询route对象
        Route route = routeDao.findOne(Integer.parseInt(rid));
        //2.根据route的id查询图片集合信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
        route.setRouteImgList(routeImgList);
        //3.根据route的sid（商家id）查询商家对象
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);
        //4.查询收藏次数
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }

    @Override
    public List<Route> findAl() {
        return routeDao.findAl();
    }
}
