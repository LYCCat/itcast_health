package com.itheima.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service(interfaceClass=OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService, InitializingBean {

    @Override
    public Result order(Map map) {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("'++======'===");
    }
}
