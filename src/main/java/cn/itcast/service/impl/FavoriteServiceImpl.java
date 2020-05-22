package cn.itcast.service.impl;

import cn.itcast.bean.Favorite;
import cn.itcast.dao.FavoriteDao;
import cn.itcast.dao.RouteImgDao;
import cn.itcast.dao.impl.FavoriteDaoImpl;
import cn.itcast.dao.impl.RouteImgDaoImpl;
import cn.itcast.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 调用dao层，查询是否收藏
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        //如果对象有值则为true
        return favorite != null;
    }

    /**
     * 调用dao层增加收藏
     * @param rid
     * @param uid
     */
    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }


}
