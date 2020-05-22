package cn.itcast.dao.impl;

import cn.itcast.bean.Favorite;
import cn.itcast.dao.FavoriteDao;
import cn.itcast.utils.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {
    /**
     * JdbcTemplate是spring中的一部分，是对数据库jdbc的封装，处理了资源的建立和释放(不用手动操作)
     * JdbcTemplate使用了spring的注入功能
     * 只需要提供sql语句和提取结果（对应的实体类）
     */
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     *通过rid和uid查询tab_favorite(收藏)表的所有参数
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        Favorite favorite = null;
        try {
            String sql = " select * from tab_favorite where rid = ? and uid = ?";
            //
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {

        }
        return favorite;
    }

    /**
     * 通过rid查询tab_favorite(收藏)表中内容的数量
     * @param rid
     * @return
     */
    @Override
    public int findCountByRid(int rid) {
        String sql = "SELECT COUNT(*) FROM tab_favorite WHERE rid = ?";

        return template.queryForObject(sql,Integer.class,rid);
    }

    /**
     * 通过rid,时间和uid对tab_favorite（收藏）表进行增加数据
     * @param rid
     * @param uid
     */
    @Override
    public void add(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";

        template.update(sql,rid,new Date(),uid);
    }
}
