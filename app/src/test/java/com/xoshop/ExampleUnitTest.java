package com.xoshop;

import com.xoshop.extend.Cat;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void getCat() throws Exception {
        Cat cat =new Cat("tom","cat");
        System.out.print("获取类别为---》"+cat.getClassify());
        cat.eat();
    }
}