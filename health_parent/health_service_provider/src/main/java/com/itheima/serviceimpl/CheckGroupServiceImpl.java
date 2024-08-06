package com.itheima.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.entity.PageResult;
import com.itheima.mapper.CheckGroupDao;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckGroupService;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass=CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Reference
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {

        checkGroupDao.add(checkGroup);
        //设置检查组合和检查项的关联关系
        setCheckGroupAndItem(checkGroup.getId(),checkitemIds);

    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page = checkGroupDao.selectBycondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    public void  setCheckGroupAndItem(Integer checkGroupId,Integer[]checkitemIds){
       if(checkitemIds!=null&&checkitemIds.length>0) {
           for(Integer checkitemId:checkitemIds){
            Map<String,Integer> map=new HashMap<>();
            map.put("checkgroup_id",checkGroupId);
            map.put("checkitem_id",checkitemId);
            checkGroupDao.setCheckGroupAndItem(map);
           }
       }

    }

    @Override
    public void delete(Integer id) {
        checkGroupDao.delete(id);
        checkGroupDao.deleteAssociation(id);

    }

    @Override
    public void edit(CheckGroup checkGroup,Integer[]checkItemIds) {


        //操作t_checkgroup_checkitem
        checkGroupDao.deleteAssociation(checkGroup.getId());
        setCheckGroupAndItem(checkGroup.getId(), checkItemIds);

        //操作t_checkgroup
        checkGroupDao.edit(checkGroup);
    }

    @Override
    public List<CheckGroup> findAll() {
        List<CheckGroup> all = checkGroupDao.findAll();
        return all;
    }

    @Override
    public CheckGroup findById(Integer id) {
        CheckGroup checkGroup = checkGroupDao.findById(id);
        return  checkGroup;
    }



    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        List<Integer> checkItemIds = checkGroupDao.findCheckItemIdsByCheckGroupId(id);
        return  checkItemIds;

    }
}
