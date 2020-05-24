package cn.itcast.service;

import cn.itcast.bean.Favorite;

public interface FavoriteService {
    /**
     * 判断是否收藏
     * @param rid
     * @param uid
     * @return
     */
    public boolean isFavorite(String rid,int uid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    public void add(String rid, int uid);

    public Favorite findFavirite();
}
