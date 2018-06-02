package com.cskaoyan.test;

import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;

public class UserServletTest {

    @Test
    public void test01(){

        Scanner scanner = new Scanner(System.in);
        String tel = scanner.nextLine();

        System.out.println(tel);
    }

}