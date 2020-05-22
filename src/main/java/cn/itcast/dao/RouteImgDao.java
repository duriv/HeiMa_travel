package cn.itcast.dao;

import cn.itcast.bean.RouteImg;

import java.util.List;

public interface RouteImgDao {
    /**
     * 根据线路id查询图片
     * @param rid
     * @return
     */
    public List<RouteImg> findByRid(int rid);
}
