package com.itheima.mapper;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SetmealDao {
    public List<Setmeal> findAll();
    public  List<Setmeal> findById(int id);
    public void add(Setmeal setmeal);
    public  void setSetmealAndCheckGroup(Map<String,Integer>map);
    public Page<Setmeal>findPage(String queryString);
}
