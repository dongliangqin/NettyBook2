package com.phei.netty.msgpack;

import org.msgpack.annotation.Message;
/**
 * Copyright(C),2019-2022,Code For ONE PIECE
 * FileName: User
 * Author: dongliangqin
 * Date: 2022/2/13 16:33
 * Description :
 * History:
 * <author>          <time>           <version>          <desc>
 * 作者姓名           修改时间           版本号               描述
 */

@Message
public class User {
    private String name;
    private int age;
    private String id;
    private String sex;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id='" + id + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
