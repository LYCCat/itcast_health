package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;
    //新增
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[]checkitemIds){
        try {
            checkGroupService.add(checkGroup,checkitemIds);
            return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }

   }
   //分页查询所有
    @RequestMapping("/findPage")
   public PageResult findPage(@RequestBody QueryPageBean queryPageBean){

            PageResult pageResult = checkGroupService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
            return  pageResult;

   }
   //根据id查询
   @RequestMapping("/findById")
   public  Result findById(Integer id){
        try {
            CheckGroup checkGroup = checkGroupService.findById(id);
            return  new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        }catch (Exception e){
        e.printStackTrace();
        return  new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

   }
   //根据id查询其包含的所有检查项id
   @RequestMapping ("/findCheckItemIdsByCheckGroupId")
    public Result  findCheckItemIdsByCheckGroupId(Integer id){
        try{
       List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(id);
       return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemIds);
   }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }

}
//编辑
    public Result edit(@RequestBody CheckGroup checkGroup ,Integer[]checkItemIds){
    try{
        checkGroupService.add(checkGroup,checkItemIds);
    }catch (Exception e){
    return  new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
    }
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
    @RequestMapping("/findAll")
    public Result findAll(){
        try{
            List<CheckGroup>list = checkGroupService.findAll();
            return  new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);

        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);


        }
    }
}
