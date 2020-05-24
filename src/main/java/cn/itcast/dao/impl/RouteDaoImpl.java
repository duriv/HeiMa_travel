package cn.itcast.dao.impl;

import cn.itcast.bean.Route;
import cn.itcast.dao.RouteDao;
import cn.itcast.dao.UserDao;
import cn.itcast.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    /**
     * spring对jdbc的封装
     */
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 通过cid和name查询路线表中的数量
     * @param cid
     * @param rname
     * @return
     */
    @Override
    public int findTotalCount(int cid,String rname) {
//        String sql = "select count(*) from tab_route where cid = ?";
        //1.定义sql模板
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        //条件们
        List params = new ArrayList();
        //2.判断参数是否有值
        //返回的就一个值
        if (cid != 0 ){
            sb.append(" and cid = ? ");
            //添加？对应的值
            params.add(cid);
        }
        if (rname != null && rname.length() > 0){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        sql = sb.toString();
        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    /**
     *
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String rname) {
//        String sql = "select * from tab_route where cid = ? and rname like ? limit ?,?";
        //1.定义sql模板
        String sql = "select * from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        //条件们
        List params = new ArrayList();
        //2.判断参数是否有值
        //返回的就一个值
        if (cid != 0 ){
            sb.append(" and cid = ? ");
            //添加？对应的值
            params.add(cid);
        }
        if (rname != null && rname.length() > 0){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        //分页条件
        sb.append(" limit ? , ?");
        sql = sb.toString();

        params.add(start);
        params.add(pageSize);
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    /**
     * 通过rid查询tab_route表中的所有参数
     * @param rid
     * @return
     */
    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        //返回一个list实体类对象
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }

    @Override
    public List<Route> findAl() {
        String sql = "select * from tab_route order by count desc ";
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class));
    }
}
