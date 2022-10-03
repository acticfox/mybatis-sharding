package com.github.acticfox.mybatis.sharding.example.model;

import java.io.Serializable;

public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    private int               id;

    private int               a;

    private int               b;

    private int               c;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "Test{" + "id=" + id + ", a=" + a + ", b=" + b + ", c=" + c + '}';
    }

}
