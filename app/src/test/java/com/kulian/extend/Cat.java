package com.kulian.extend;

/**
 * Created by mac on 2018/11/16.
 */

public class Cat extends Animal {
    /**
     *
     * @param name
     * @param classify
     */
    public Cat(String name, String classify) {
        super(name, classify);
    }

    @Override
    public void eat() {
        System.out.print("maochidongxi");
    }
}
