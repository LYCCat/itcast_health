package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.Setmeal;

import java.util.List;

public interface SetmealService {

    public List<Setmeal> findAll();
    public List<Setmeal> findById(int id);
    public  void add(Setmeal setmeal,Integer[]checkgroupIds);
    public PageResult findPage(Integer currentPage,Integer pageSize,String queryString);


}
