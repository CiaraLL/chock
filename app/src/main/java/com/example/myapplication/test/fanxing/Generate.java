package com.example.myapplication.test.fanxing;

/**
 * @Author lili
 * @Date 2024/5/30-15:24
 * 继承泛型类
 */
public class Generate<T> extends NormalGenerate<T> {
    public Generate(T data) {
        super(data);
    }
}
