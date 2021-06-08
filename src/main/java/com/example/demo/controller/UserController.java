package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author cqx
 * @Description: 控制类
 * @date 2021/6/811:08
 */
@Component
@EnableScheduling
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    //定时执行读取信息发送邮件
    @Scheduled(cron = "0 0 0 * * ?")//每天凌晨执行一次
    public void getInformation(){
        log.info(dateFormat.format(new Date()) +"处理开始");
        List<User> users = FileUtil.getUser();
        try {
            Iterator<User> it = users.iterator();
            int i = 0;
            while(it.hasNext()) {  //判断集合当中是否还有元素
                // 比较当天与生日的时间（截取方式需在txt中的时间格式一致且按照规定的情况下，也可选择直接用日期格式进行对比）
                User user = it.next();
                int month1= Integer.parseInt(user.getBirthday().substring(5,7));//获取生日月份
                int day1 = Integer.parseInt(user.getBirthday().substring(8,10));//获取生日天

                int month2= Integer.parseInt(dateFormat.format(new Date()).substring(5,7));//获取当前月份
                int day2 = Integer.parseInt(dateFormat.format(new Date()).substring(8,10));//获取当前天

                if (month1 == month2 && day1 == day2){
                    FileUtil.sendEmail(user);
//                    System.err.println("进来了");
                    i = 1;
                }
            }
            if (i == 0){
                System.err.println(dateFormat.format(new Date())+"没有人过生日");
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        log.info(dateFormat.format(new Date()) +"处理结束");
    }


}
