package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.OrderService;
import com.itheima.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

import static com.itheima.pojo.Order.ORDERTYPE_WEIXIN;

@RestController
@RequestMapping("/order")
public class OrderController {
  @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;
    @RequestMapping("/submit")
    public Result submitOrder(@RequestBody Map map){

        String validateCode=(String) map.get("validateCode");
        String telephone=(String)map.get("telephone");
        String redisCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);

        //校验验证码
        if(redisCode==null||!redisCode.equals(validateCode)){
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);

        }
        Result result=null;
        //验证通过进行预约
        try {
            map.put("orderType", ORDERTYPE_WEIXIN);
            result = orderService.order(map);

        }
        catch (Exception e){
        return  null;

        }
        //预约成功之后，向用户发送短信
        if(!result.isFlag()){
            return  result;
        }
        try{
            SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,map.get("orderDate").toString());

        }catch(ClientException e){
            e.printStackTrace();
            return  result;
        }
        return  new Result(true,MessageConstant.ORDER_SUCCESS);









    }
}
