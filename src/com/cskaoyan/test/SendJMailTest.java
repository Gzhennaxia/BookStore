package com.cskaoyan.test;

import com.cskaoyan.utils.SendJMail;
import org.junit.Test;

import static org.junit.Assert.*;

public class SendJMailTest {

    @Test
    public void sendMail() {
        SendJMail.sendMail("931077056@qq.com", "爱你么么哒");
    }
}