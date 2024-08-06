package com.itheima.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.mapper.CheckItemDao;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass=CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
   private CheckItemDao checkItemDao;
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult findPage(int curPage,int pageSize,String queryString) {
        PageHelper.startPage(curPage,pageSize);
        Page<CheckItem>page = checkItemDao.findPage(queryString);
        PageResult pageResult=new PageResult(page.getTotal(),page.getResult());

        return pageResult;
    }

    @Override
    public void delete(int id) {
        //注意：不能直接删除，需要判断当前检查项是否和检查组关联，如果已经和检查组进行了关联则不允许
        //删除
        if(checkItemDao.findCountByCheckItemId(id)>0)
            throw new RuntimeException("当前检查项被引用，不能删除");

        checkItemDao.delete(id);

    }

    @Override
    public void edit(CheckItem checkItem) {
    checkItemDao.edit(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        List<CheckItem> list = checkItemDao.findAll();
        return list;
    }


}
