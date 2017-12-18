package com.piar.demo;

/**
 * Created by xingke on 2017/3/23.
 */
public class TestRemoteServiceImpl implements TestLocalService {

    @Override
    public Student getAStudent(String name) {
        Student student = new Student();
        student.setName(name);
        student.setAge((int)(Math.random()*100));
        return student;
    }
}
