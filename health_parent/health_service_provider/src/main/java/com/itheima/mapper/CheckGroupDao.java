package com.itheima.mapper;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    public void add(CheckGroup checkGroup);
    public void setCheckGroupAndItem(Map map);
    public Page<CheckGroup> selectBycondition(String queryString);
    public List<Integer>findCheckItemIdsByCheckGroupId(Integer id);
    public  void  edit(CheckGroup checkGroup);
    public  void deleteAssociation(Integer id);
    public CheckGroup findById(Integer id);
    public void delete(Integer id);

    public List<CheckGroup>findAll();



}
