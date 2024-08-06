package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CheckItemService {
    public void add(CheckItem checkItem);
    public PageResult findPage(int curPage,int pageSize,String queryString);
    public void delete(int id);
    //查询是否关联
   public  void edit(CheckItem checkItem);
   public List<CheckItem>findAll();

}
