package com.itheima.mapper;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    public  void add(OrderSetting orderSetting);
    //查看是否存在当天日期的编辑
    public int findCountByOrderDate(Date orderDate);
    //进行更新订单设置的操作
    public void editNumberByOrderDate(OrderSetting orderSetting);
    public List<OrderSetting> getOrdersettingByMonth(Map map);



}
