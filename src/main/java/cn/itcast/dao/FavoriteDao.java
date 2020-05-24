package cn.itcast.dao;

import cn.itcast.bean.Favorite;

public interface FavoriteDao {
    /**
     * 根据rid和uid查询收藏信息
     * @param rid
     * @param uid
     * @return
     */
    public Favorite findByRidAndUid(int rid,int uid);
    /**
     * 根据rid查询收藏次数
     * @param rid
     * @return
     */
    public int findCountByRid(int rid);

    /**
     * 添加收藏
     * @param parseInt
     * @param uid
     */
    public void add(int parseInt, int uid);

    public Favorite findFavorite();
}
