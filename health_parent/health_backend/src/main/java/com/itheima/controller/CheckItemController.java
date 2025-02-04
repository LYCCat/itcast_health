package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    CheckItemService checkItemService;
    //新增,click "确定"
    @RequestMapping("/add")
    public  Result add(@RequestBody  CheckItem checkItem){
        try{
        checkItemService.add(checkItem);
      return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        }catch (Exception e){
            return  new Result(false,MessageConstant.ADD_CHECKITEM_FAIL);
        }


    }
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){


            PageResult  pageResult = checkItemService.findPage
                    (queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
            return pageResult;


    }
    @RequestMapping("/delete")
    public Result delete(int id){
        try {
            checkItemService.delete(id);
            return  new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            return  new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }

    }
    @RequestMapping("/edit")
    public Result edit(CheckItem checkItem){
        try{
            checkItemService.edit(checkItem);
            return  new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        }catch (Exception e){
            return  new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }


    }
    @RequestMapping("/findAll")
    public  Result findAll(){
        try {
            List<CheckItem> list = checkItemService.findAll();
            return  new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        }catch (Exception e){
            return  new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

}
