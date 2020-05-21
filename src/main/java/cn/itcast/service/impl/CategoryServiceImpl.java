package cn.itcast.service.impl;

import cn.itcast.bean.Category;
import cn.itcast.dao.CategoryDao;
import cn.itcast.dao.impl.CategoryDaoImpl;
import cn.itcast.service.CategoryService;
import cn.itcast.utils.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao dao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
        //1.从redis中查询
        //1,1获取客户端
        Jedis jedis = JedisUtil.getJedis();
        //1.2 可使用sortedset查询
//        Set<String> categorys = jedis.zrange("category", 0, -1);
        //1.3查询sortedset中的分数(cid)和值(cname)
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        //2.判断查询的字符串是否为空
        List<Category> all = null;
        if (categorys == null || categorys.size()==0){
            System.out.println("从数据库中查询");
            //3.如果为空，需要从数据库查询，再将数据存入redis
            //3.1 从数据库查询
            all = dao.findAll();
            //将数据存储到redis中category中的key中
            for (int i = 0; i < all.size(); i++) {
                jedis.zadd("category",all.get(i).getCid(),all.get(i).getCname());
            }
        }else {
            System.out.println("从redis中查询");
            //如果不为空，将set的数据存入list
            all = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                all.add(category);
            }
        }

        //如果不为空直接返回
        return all;
    }
}
