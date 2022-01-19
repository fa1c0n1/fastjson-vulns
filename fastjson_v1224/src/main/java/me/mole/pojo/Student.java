package me.mole.pojo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class Student {
    private String name;
    private int age;
    private HashMap<String,String> _properties;

    public Student() {
        System.out.println("无参构造函数");
    }

    public String getName() {
        System.out.println("getName");
        return name;
    }

    public void setName(String name) {
        System.out.println("setName");
        this.name = name;
    }

    public int getAge() {
        System.out.println("getAge");
        return age;
    }

    public void setAge(int age) {
        System.out.println("setAge");
        this.age = age;
    }

    public HashMap<String, String> getProperties() {
        System.out.println("getProperties");
        return _properties;
    }

    public void setProperties(HashMap<String,String> properties) {
        System.out.println("setProperties");
        this._properties = properties;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", _properties=" + _properties +
                '}';
    }
}