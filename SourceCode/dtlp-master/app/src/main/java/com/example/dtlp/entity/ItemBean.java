package com.example.dtlp.entity;


public class ItemBean {
    private int img;
    private String title;
    private boolean isUpdate=false;

    public ItemBean() {
    }

    public ItemBean(int img, String title, boolean isUpdate) {
        this.img = img;
        this.title = title;
        this.isUpdate = isUpdate;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    @Override
    public String toString() {
        return "ItemBean{" +
                "img=" + img +
                ", title='" + title + '\'' +
                ", isUpdate=" + isUpdate +
                '}';
    }
}
