package cn.itcast.dao.impl;

import cn.itcast.bean.Category;
import cn.itcast.dao.CategoryDao;
import cn.itcast.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    /**
     * 获取JdbcTemplate,拿到数据库连接
     * JdbcTemplate是Spring的一部分,是对数据库的操作在jdbc的封装,处理了资源的建立和释放(不需要我们管理连接了),
     * 我们只需要提供SQL语句(不需要我们设置参数了)和提取结果(查询时候可以直接返回对应的实体类),使JDBC更加易于使用。
     * JdbcTemplate使用spring的注入功能，把DataSource注册到JdbcTemplate之中。
     * 一般使用语句：
     *  execute：没有返回值，可以执行所有SQL语句，一般用于执行DDL语句。
     *  update：返回的是一个int值，影响的行数， 用于执行INSERT、UPDATE、DELETE等DML语句。增删改都只是使用到了一个方法： update(sql,Object…args)
     *  queryXxx：用于DQL数据查询语句
     *  queryXxx:
     * 方法名	作用
     * queryForObject(sql,数据类型.class)	查询单个对象
     * queryForMap(sql,参数)	                查询单个对象，返回一个Map对象
     * queryForObject(sql,new BeanPropertyRowMapper(类型),参数)	查询单个对象,返回单个实体类对象
     * queryForList(sql,参数)	            查询多个对象，返回一个List对象，List对象存储是Map对象
     * query(sql,new BeanPropertyRowMapper(),参数 )	    查询多个对象，返回的是一个List对象，List对象存储是实体类
     *
     */
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询tab_category(类型)表所有数据
     * @return
     */
    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category ";
        //查询数据库表返回list实体类对象
        return template.query(sql,new BeanPropertyRowMapper<Category>(Category.class));
    }

}
