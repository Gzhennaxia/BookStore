package com.cskaoyan.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserFormBean {

    private String username;
    private String nickname;
    private String password;
    private String email;
    private String birthday;

    private Map<String, String> error = new HashMap<String, String>();

    public UserFormBean() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Map<String, String> getError() {
        return error;
    }

    public void setError(Map<String, String> error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "UserFormBean{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }

    public boolean validate() {

        if (username == null || "".equals(username)){
            error.put("username", "用户名不能为空");
        }else if (username.length() < 2 || username.length() > 16){
            error.put("username", "用户名长度介于2-16个字符之间");
        }

        if (nickname == null || "".equals(nickname)){
            error.put("nickname", "昵称不能为空");
        }else if (nickname.length() < 2 || nickname.length() > 16){
            error.put("nickname", "昵称长度介于2-16个字符之间");
        }

        if (password == null || "".equals(password)){
            error.put("password", "密码不能为空");
        }else if (password.length() < 2 || password.length() > 16){
            error.put("password", "密码长度介于2-16个字符之间");
        }

        if (email == null || "".equals(email)){
            error.put("email", "邮箱不能为空");
        }else if(!email.matches("\\b^['_a-z0-9-\\+]+(\\.['_a-z0-9-\\+]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*\\.([a-z]{2}|aero|arpa|asia|biz|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|nato|net|org|pro|tel|travel|xxx)$\\b")){
            error.put("email", "邮箱格式不正确");
        }

        if (birthday == null || "".equals(birthday)){
            error.put("password", "出生日期不能为空");
        }else {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat.parse(birthday);
            } catch (ParseException e) {
                //e.printStackTrace();
                error.put("birthday", "日期格式为年-月-日");
            }
        }

        return error.isEmpty();
    }

    public String varify() {
        String errorMsg = null;
        if (nickname == null || "".equals(nickname)){
            errorMsg = "昵称不能为空";
        }else if (nickname.length() < 2 || nickname.length() > 16){
            errorMsg = "昵称长度介于2-16个字符之间";
        }

        if (password == null || "".equals(password)){
            errorMsg = "密码不能为空";
        }else if (password.length() < 2 || password.length() > 16){
            errorMsg = "密码长度介于2-16个字符之间";
        }

        if (email == null || "".equals(email)){
            errorMsg = "邮箱不能为空";
        }else if(!email.matches("\\b^['_a-z0-9-\\+]+(\\.['_a-z0-9-\\+]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*\\.([a-z]{2}|aero|arpa|asia|biz|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|nato|net|org|pro|tel|travel|xxx)$\\b")){
            errorMsg = "邮箱格式不正确";
        }

        if (birthday == null || "".equals(birthday)){
            errorMsg = "出生日期不能为空";
        }else {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat.parse(birthday);
            } catch (ParseException e) {
                //e.printStackTrace();
                errorMsg = "日期格式为年-月-日";
            }
        }

        return errorMsg;

    }
}
