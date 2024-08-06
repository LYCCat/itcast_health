package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
     private SetmealService setmealService;
    @Autowired
   private JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile")MultipartFile imgFile){
    try{
        //获取原始文件名
        String originalFilename=imgFile.getOriginalFilename();
        //获取文件后缀名
        int lastIndexOf= originalFilename.lastIndexOf(".");
        String suffix=originalFilename.substring(lastIndexOf-1);
        //使用UUID随机产生文件名称，防止同名文件覆盖
        String fileName= UUID.randomUUID().toString()+suffix;

        QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);
        //图片上传成功，将上传图片名称存入Redis，基于Redis的Set集合存储
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);

        return  new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS);
    }catch (Exception e){
    return  new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
    }
    }
    @RequestMapping("/add")
      public  Result add(@RequestBody Setmeal setmeal,Integer[]checkgroupIds ){
        try{
            setmealService.add(setmeal,checkgroupIds);
            return  new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);

        }catch (Exception e){
            return  new Result(true, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        try{
            PageResult pageResult = setmealService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
            return pageResult;

        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }



}
