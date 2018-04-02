package com.robin.bos.domain.take_delivery;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 宣传任务
 */
@Entity
@Table(name = "T_PROMOTION")
public class Promotion {

    // Fields

    private Long id;
    private String title; // 标题
    private String titleImg;// 封面
    private String activeScope; // 活动范围
    private Date startDate; // 促销开始时间
    private Date endDate; // 促销结束时间
    private String status; // 状态 1：有效 2: 过期
    private String description; // 内容

    public Promotion() {}

    public Promotion(String title, String titleImg, String activeScope,
            Date startDate, Date endDate, String status, String description) {
        this.title = title;
        this.titleImg = titleImg;
        this.activeScope = activeScope;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.description = description;
    }

    @Id
    @GeneratedValue()
    @Column(name = "ID")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TITLE", length = 510)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "TITLE_IMG", length = 510)
    public String getTitleImg() {
        return this.titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    @Column(name = "ACTIVE_SCOPE", length = 510)
    public String getActiveScope() {
        return this.activeScope;
    }

    public void setActiveScope(String activeScope) {
        this.activeScope = activeScope;
    }

    @Column(name = "START_DATE")
    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "END_DATE")
    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "DESCRIPTION", length = 50000)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
