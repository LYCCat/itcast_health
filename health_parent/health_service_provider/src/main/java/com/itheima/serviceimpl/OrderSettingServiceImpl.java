package com.itheima.serviceimpl;

import com.itheima.mapper.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    //批量添加
    @Override
    public void add(List<OrderSetting> orderSettingList) {
        if(orderSettingList!=null&&orderSettingList.size()>0)
        for (OrderSetting orderSetting:orderSettingList )
        {
            if(orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate())>0){
                //已经存在执行更新操作
                orderSettingDao.editNumberByOrderDate(orderSetting);
            }else{
                //不存在执行添加操作
            orderSettingDao.add(orderSetting);}
        }


    }

    @Override
    public List<Map> getOrdersettingByMonth(String date) {//日期格式2019-2
        String dateBegin=date+"-1";
        String dateEnd=date+"-31";
        Map map=new HashMap<>();
        map.put("dateBegin",dateBegin);
        map.put("dateEnd",dateEnd);
        List<OrderSetting> list = orderSettingDao.getOrdersettingByMonth(map);
        List<Map> data=new ArrayList<>();
        for (OrderSetting orderSetting : list) {
        Map orderSettingMap=new HashMap<>();
        orderSettingMap.put("date",orderSetting.getOrderDate().getDate());//获取日期
        orderSettingMap.put("number",orderSetting.getNumber());
        orderSettingMap.put("reservations",orderSetting.getReservations());
        data.add(orderSettingMap);
        }
        return data;



    }
//根据日期修改预约设置
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        int count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if (count > 0) {
            orderSettingDao.add(orderSetting);

        } else {
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }
    }


}
