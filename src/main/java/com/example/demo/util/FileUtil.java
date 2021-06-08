package com.example.demo.util;

import com.example.demo.entity.User;
import org.springframework.util.ResourceUtils;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author cqx
 * @Description: 文件读取工具类
 * @date 2021/6/810:55
 */
public class FileUtil {

    //读取员工信息得到当天生日的信息
    public static List<User> getUser(){
        List<User> users = new ArrayList<>();
        try {
            /* 读入TXT文件 ，写上文件地址*/
            File filename = ResourceUtils.getFile("classpath:dataFile/information.txt");
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader);
            String data = null;
            while ((data = br.readLine()) != null) {
                User user = new User();
                String[] sizeArr = data.split(",");
                user.setCode(sizeArr[0]);
                user.setName(sizeArr[1]);
                user.setBirthday(sizeArr[2]);
                user.setEmail(sizeArr[3]);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * @Description:发送邮件
     * @author:cqx
     * @Date: 2021/6/8
    */
    public static  int sendEmail(User user){
        try {
            System.out.println("进来发送邮件");
            //创建属性对象
            Properties pro = new Properties();
            pro.put("mail.smtp.auth", "true");//身份验证
            pro.put("mail.host", "smtp.qq.com");//设置邮件服务器
            pro.put("mail.transport.protocol", "smtp");//发送邮件协议
            pro.put("mail.smtp.port", "465");//端口号 465

            pro.put("mail.smtp.socketFactory.port", "465");
            pro.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            Session session = Session.getInstance(pro);
            MimeMessage message = new MimeMessage(session);
            //设置邮件的标题
            message.setSubject("生日祝福");
            //设置邮件内容
            message.setText("亲爱的"+user.getName()+"：今天是您的生日，祝您生日快乐！天天开心！");
            //设置发送人
            message.setFrom(new InternetAddress("1501766508@qq.com"));
            //设置收件人
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(user.getEmail()));
            Transport tran = session.getTransport();
            tran.connect("1501766508", "mxighnnvlzaehhaa");
            //发送消息
            tran.sendMessage(message, message.getAllRecipients());
            tran.close();
            System.out.println("发送成功");
            return 1;
        } catch (Exception e) {
            e.getMessage();
            return 0;
        }
    }
}
