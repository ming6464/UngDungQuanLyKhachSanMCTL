package com.ming6464.ungdungquanlykhachsanmctl.Hienthiphong;


public class user {
    private int resourceId;
    private String name;

    public user(int resourceId, String name) {
        this.resourceId = resourceId;
        this.name = name;
    }


    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

