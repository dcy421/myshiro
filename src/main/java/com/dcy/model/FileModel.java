package com.dcy.model;

/**
 * Created by Administrator on 2017/5/13.
 */
public class FileModel {
    private String info;//序号guid
    private String name;//名字
    private String path;//地址
    private String newPath;//新文件地址
    private long size;//大小
    private String contentType;//获取文件MIME类型


    public String getNewPath() {
        return newPath;
    }

    public void setNewPath(String newPath) {
        this.newPath = newPath;
    }
    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    private String suffix;//获取文件MIME类型

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }



    public FileModel() {
    }

    public FileModel(String info, String name, String path, long size, String contentType, String suffix) {
        this.info = info;
        this.name = name;
        this.path = path;
        this.size = size;
        this.contentType = contentType;
        this.suffix = suffix;
    }

    @Override
    public String toString() {
        return "FileModel{" +
                "info='" + info + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", newPath='" + newPath + '\'' +
                ", size=" + size +
                ", contentType='" + contentType + '\'' +
                ", suffix='" + suffix + '\'' +
                '}';
    }
}
