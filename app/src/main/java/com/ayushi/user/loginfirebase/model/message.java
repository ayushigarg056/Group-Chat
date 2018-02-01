package com.ayushi.user.loginfirebase.model;

/**
 * Created by user on 10-Jan-18.
 */

public class message {
    String messagel;
    String name;
    String id;

    public message() {}

    public message(String id, String name,String messagel) {
        this.messagel = messagel;
        this.id=id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setMessagel(String messagel) {
        this.messagel = messagel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessagel() {
        return messagel;
    }

    public String getName() {
        return name;
    }

}
