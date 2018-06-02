package com.cskaoyan.bean;

import java.util.Date;

public class Order {

    private String oid;
    private double money;
    private String recipients;
    private String tel;
    private String address;
    private int state;
    private Date ordertime;
    private int uid;

    public Order() {
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oid='" + oid + '\'' +
                ", money=" + money +
                ", recipients='" + recipients + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", state=" + state +
                ", ordertime=" + ordertime +
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
        }else if (tel.matches("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\\\d{8}$")){
            errorMsg = "电话格式不正确";
        }

        if (address == null || "".equals(address)){
            errorMsg = "地址不能为空";
        }

        return errorMsg;


    }
}
