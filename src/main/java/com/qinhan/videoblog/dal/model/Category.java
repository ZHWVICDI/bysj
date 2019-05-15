package com.qinhan.videoblog.dal.model;

import javax.persistence.*;

@Entity
@Table(name = "videoblog_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    //策略名
    @Column(name = "category_name", nullable = false)
    private String categoryName;

    //父策略---这个针对二级策略
    @Column(name = "parent_id",columnDefinition = "int(11) default 0 comment '父级分类id'")
    private int parent;
    //深度--表示是几级的分类
    @Column(name = "depth" ,columnDefinition = "int(11) default 1 comment '分类，默认为1级分类'")
    private int depth;

    //分类的状态，NORMAL可用|FROZEN不可用
    private String status;
    //分类的优先级
    private String priority;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Category{" +
                "Id=" + Id +
                ", categoryName='" + categoryName + '\'' +
                ", parent=" + parent +
                ", depth=" + depth +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
