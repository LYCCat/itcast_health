package com.itheima.mapper;

import com.github.pagehelper.Page;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CheckItemDao  {


    public Result add(CheckItem checkItem);
    public Page<CheckItem> findPage(String queryString);
    public void delete(int id);
    public long findCountByCheckItemId(Integer checkItemId);
    public  void edit(CheckItem checkItem);
    public List<CheckItem> findAll();

}
