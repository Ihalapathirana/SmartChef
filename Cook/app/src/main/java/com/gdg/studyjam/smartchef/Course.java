package com.gdg.studyjam.smartchef;

/**
 * Created by Anu on 4/21/2015.
 */
public class Course {

    public String name;
    public String recipe_count=null;
    public String img=null;



    public Course(String n,String d){
        name=n;
        recipe_count=d;
    }
    public Course(String n,String d,String i){
        name=n;
        recipe_count=d;
        this.img=i;
    }



}
