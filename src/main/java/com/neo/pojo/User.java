package com.neo.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
//    private static final long serialVersionUID = 1L; //实现序列化
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false, unique = true)
    private String poemName;  //名字
    @Column(nullable = false)
    private String poemAuthor;  //作者

    @Column(nullable = false)
    private String poemDynasty;//朝代 int  1 2 3

    @Column(nullable = false)
    private String poemType; //类型 int  1 2 3

    @Column(nullable = false)
    private String poemDescription;

   // @Column(nullable = false)
    private String url;

    /**
     * 详细的描述信息，包含所有诗词信息
     * */
    // @Column(nullable = false)
    private String detailContent;

    public String getDetailContent(){
        return detailContent;
    }

    public User setDetailContent(String detailContent) {
        this.detailContent = detailContent;
        return this;
    }

    public String getUrl(){
        return url;
    }

    public User setUrl(String url) {
        this.url = url;
        return this;
    }

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public String getPoemName() {
        return poemName;
    }

    public User setPoemName(String poemName) {
        this.poemName = poemName;
        return this;
    }

    public String getPoemAuthor() {
        return poemAuthor;
    }

    public User setPoemAuthor(String poemAuthor) {
        this.poemAuthor = poemAuthor;
        return this;
    }

    public String getPoemDescription() {
        return poemDescription;
    }

    public User setPoemDescription(String poemDescription) {
        this.poemDescription = poemDescription;
        return this;
    }

    public String getPoemDynasty() {
        return poemDynasty;
    }

    public User setPoemDynasty(String poemDynasty) {
        this.poemDynasty = poemDynasty;
        return this;
    }

    public String getPoemType() {
        return poemType;
    }

    public User setPoemType(String poemType) {
        this.poemType = poemType;
        return this;
    }

    //重写tostring的方法
    @Override
    public String toString() {
        return "User{" +
                "poemName='" + poemName + '\'' +
                ", poemAuthor='" + poemAuthor + '\'' +
                ", poemDynasty='" + poemDynasty + '\'' +
                ", poemType='" + poemType + '\'' +
                ", poemDescription='" + poemDescription + '\'' +
                '}';
    }
}
