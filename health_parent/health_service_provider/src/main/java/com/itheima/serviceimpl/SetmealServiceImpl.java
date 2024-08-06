package com.itheima.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.mapper.SetmealDao;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Service(interfaceClass=SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
   private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;
    @Value("${out_put_path}")
    private  String outputpath;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public List<Setmeal> findAll() {
        List<Setmeal> list = setmealDao.findAll();
        return  list;
    }
    @Override
    public List<Setmeal> findById(int id) {
        List<Setmeal> setmealList = setmealDao.findById(id);
        return  setmealList;

    }

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        if(checkgroupIds!=null&&checkgroupIds.length>0) {
            //绑定套餐和检查组的多对多关系
            setSetmealAndCheckGroup(setmeal.getId(), checkgroupIds);
        }
        //将图片名称保存到Redis
        savePic2Redis(setmeal.getImg());

        //新增套餐后需要重新生成静态页面
        generateMobileStaticHtml();

    }

    private void generateMobileStaticHtml() {
        //准备模板文件中所需的数据
        List<Setmeal> setmealList = setmealDao.findAll();
        //生成套餐列表静态页面
        generateMobileSetmealListHtml(setmealList);
        //生成套餐详情静态页面(多个)
        generateMobileSetmealDetailHtml(setmealList);
    }

    private void generateMobileSetmealDetailHtml(List<Setmeal> setmealList) {
        for (Setmeal setmeal : setmealList) {
            Map<String,Object> dataMap=new HashMap<String,Object>();
            dataMap.put("setmeal",setmeal.getId());
            generateHtml("mobile_setmeal_detail.ftl","setmeal_detail"+setmeal.getId()+".html",dataMap);

        }

    }

    private void generateMobileSetmealListHtml(List<Setmeal> setmealList) {
        Map<String,Object>dataMap=new HashMap<String,Object>();
        dataMap.put("setmealList",setmealList);
        generateHtml("mobile_setmeal.ftl","m_setmeal.html",dataMap);
    }

    private void generateHtml(String templateName, String htmlPageName, Map<String, Object> dataMap){
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Writer out =null;
        try{
            //加载模板文件
         Template  template= configuration.getTemplate(templateName);
          //生成数据
            File docFile=new File(outputpath+"\\"+htmlPageName);
            out =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            //输出文件
            template.process(dataMap,out);


        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(null!=out){
                    out.flush();
                }

            }catch (Exception e2){
                e2.printStackTrace();
            }
        }


    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.findPage(queryString);
        PageResult pageResult=new PageResult(page.getTotal(),page.getResult());
        return pageResult;

    }



    private void setSetmealAndCheckGroup(Integer id, Integer[] checkgroupIds) {
        for (Integer checkgroupID:checkgroupIds) {
            Map<String,Integer>map=new HashMap<>();
            map.put("setmeal_id",id);
            map.put("checkgroup_id",checkgroupID);
            setmealDao.setSetmealAndCheckGroup(map);
        }
    }
    private void savePic2Redis(String pic){
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pic);

    }
}
