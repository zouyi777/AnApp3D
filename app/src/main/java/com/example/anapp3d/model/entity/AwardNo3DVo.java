package com.example.anapp3d.model.entity;

import com.example.anapp3d.enums.DataType;

public class AwardNo3DVo{

    private Long id;
    private DataType dataType;
    private String issueNo;
    private String awardNo;
    private String firstLeft;
    private String firstRight;
    private String secondLeft;
    private String secondRight;
    private String thirdLeft;
    private String thirdRight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo;
    }

    public String getAwardNo() {
        return awardNo;
    }

    public void setAwardNo(String awardNo) {
        this.awardNo = awardNo;
    }

    public String getFirstLeft() {
        return firstLeft;
    }

    public void setFirstLeft(String firstLeft) {
        this.firstLeft = firstLeft;
    }

    public String getFirstRight() {
        return firstRight;
    }

    public void setFirstRight(String firstRight) {
        this.firstRight = firstRight;
    }

    public String getSecondLeft() {
        return secondLeft;
    }

    public void setSecondLeft(String secondLeft) {
        this.secondLeft = secondLeft;
    }

    public String getSecondRight() {
        return secondRight;
    }

    public void setSecondRight(String secondRight) {
        this.secondRight = secondRight;
    }

    public String getThirdLeft() {
        return thirdLeft;
    }

    public void setThirdLeft(String thirdLeft) {
        this.thirdLeft = thirdLeft;
    }

    public String getThirdRight() {
        return thirdRight;
    }

    public void setThirdRight(String thirdRight) {
        this.thirdRight = thirdRight;
    }

    @Override
    public String toString() {
        return "AwardNo3DVo{" +
                "dataType=" + dataType +
                ", issueNo='" + issueNo + '\'' +
                ", awardNo='" + awardNo + '\'' +
                ", firstLeft='" + firstLeft + '\'' +
                ", firstRight='" + firstRight + '\'' +
                ", secondLeft='" + secondLeft + '\'' +
                ", secondRight='" + secondRight + '\'' +
                ", thirdLeft='" + thirdLeft + '\'' +
                ", thirdRight='" + thirdRight + '\'' +
                '}';
    }
}
