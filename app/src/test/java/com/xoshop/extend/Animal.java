package com.xoshop.extend;

/**
 * Created by mac on 2018/11/16.
 */

public class Animal {
    private String name ;
    private String classify;
    public Animal(String name,String classify){
        this.name = name;
        this.classify = classify;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }
    public void eat(){
        System.out.print("动物吃东西");
    }
}
