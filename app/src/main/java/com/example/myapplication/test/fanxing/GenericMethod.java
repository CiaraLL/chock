package com.example.myapplication.test.fanxing;

/**
 * @Author lili
 * @Date 2024/5/30-15:29
 */
public class GenericMethod {

    public static <T> T genericMethod(T... a) {
        return a[a.length];
    }

    public static void main(String[] args) {
        System.out.println(GenericMethod.genericMethod(12,34,"String"));
    }

    public class Genius<T> {}

    //泛型类
    public <T,K> K show(Genius<T> container){

    }
}
