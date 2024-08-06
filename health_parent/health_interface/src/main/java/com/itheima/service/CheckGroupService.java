package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckGroupService {
    public void add(CheckGroup checkGroup, Integer[] checkitemIds);

    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    public CheckGroup findById(Integer id);

    public List<Integer>findCheckItemIdsByCheckGroupId(Integer id);

    public void delete(Integer id);

    public void edit(CheckGroup checkGroup,Integer[]checkItemIds);
    public List<CheckGroup>findAll();
    }




