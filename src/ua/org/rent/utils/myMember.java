package ua.org.rent.utils;

import java.lang.reflect.Member;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 20.09.13
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
public class myMember implements Member {
    public int ID;
    private String name;
    private Object[] tags;


    @Override
    public Class<?> getDeclaringClass() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getModifiers() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getName() {
        return name;
    }

    public void setTags(Object[] obj)
    {
        tags=obj;
    }

    public void setName(String s)
    {
        name=s;
    }

    @Override
    public boolean isSynthetic() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

