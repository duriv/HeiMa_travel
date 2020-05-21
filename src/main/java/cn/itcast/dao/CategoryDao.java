package cn.itcast.dao;

import cn.itcast.bean.Category;

import java.util.List;

public interface CategoryDao {
    public List<Category> findAll();
}
