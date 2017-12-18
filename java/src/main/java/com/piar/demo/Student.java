package com.piar.demo;

import javax.naming.Name;
import java.io.Serializable;

/**
 * Created by xingke on 2017/3/24.
 */
public class Student implements Serializable {

    private int age;
    private String name;

    @Override
    public String toString() {
        return "name: " + name + "\nage: " + age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
