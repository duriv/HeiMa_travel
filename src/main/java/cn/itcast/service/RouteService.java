package cn.itcast.service;

import cn.itcast.bean.PageBean;
import cn.itcast.bean.Route;

public interface RouteService {
    public PageBean<Route> pageQuery(int cid,int currentPage,int pageSize);
}
