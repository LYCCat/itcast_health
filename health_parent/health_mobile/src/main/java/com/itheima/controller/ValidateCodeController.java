package com.itheima.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * 短信验证
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        //生成四位验证码
        String code = ValidateCodeUtils.generateValidateCode4String(4);
        try{
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code);

        }catch (ClientException e){
            e.printStackTrace();
            return  new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);

        }
        //将发送成功的验证码保存到redis
        jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER,5*60,code);
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);


    }

}
