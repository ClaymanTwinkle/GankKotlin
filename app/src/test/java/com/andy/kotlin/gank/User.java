package com.andy.kotlin.gank;

/**
 * todo 类名
 *
 * @author andyqtchen <br/>
 *         todo 实现的主要功能。
 *         创建日期：2017/6/20 11:43
 */
public class User {
    private String name;
    private String age;
    private boolean sex;

    public User(String name, String age, boolean sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
