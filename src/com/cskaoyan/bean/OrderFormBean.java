package com.cskaoyan.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderFormBean {

    private double money;
    private String recipients;
    private String tel;
    private String address;
    private int uid;

    public OrderFormBean() {
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "OrderFormBean{" +
                "money=" + money +
                ", recipients='" + recipients + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", uid=" + uid +
                '}';
    }

    public String validate() {

        String errorMsg = null;

        if (recipients == null || "".equals(recipients)){
            errorMsg = "收件人不能为空";
        }else if (recipients.length() < 2 || recipients.length() > 16){
            errorMsg = "收件人长度介于2-16个字符之间";
        }

        if (tel == null || "".equals(tel)){
            errorMsg = "电话不能为空";
        }else if (tel.matches("(\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$")){
            errorMsg = "电话格式不正确";
        }

        if (address == null || "".equals(address)){
            errorMsg = "地址不能为空";
        }

        return errorMsg;


    }
}
