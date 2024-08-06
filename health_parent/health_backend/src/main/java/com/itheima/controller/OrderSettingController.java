package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
   private OrderSettingService  orderSettingService;
    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile){
        try{
            //读取Excel文件数据
            List<String[]> list = POIUtils.readExcel(excelFile);
            List<OrderSetting>orderSettingList=new ArrayList<>();
            if(list!=null&&list.size()>0) {
                for (String[] strings : list) {
                    //一个数组就是一行excel表的数据
                orderSettingList.add(new OrderSetting(new Date(strings[0]),Integer.parseInt(strings[1])));
                }
            }
            orderSettingService.add(orderSettingList);
            return  new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);

        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);

        }

    }
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrdersettingByMonth(String data){//参数格式为：2019-03
        try{
            List<Map> list = orderSettingService.getOrdersettingByMonth(data);
            return  new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        }catch(Exception e){
            e.printStackTrace();
            return  new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try{
            orderSettingService.editNumberByDate(orderSetting);
            return  new Result(true,MessageConstant.ORDERSETTING_SUCCESS);

        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }


}
