package com.example.anapp3d.model.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AwardNo3DPo extends RealmObject {

    /*期号*/
    private String issueNo;

    /*百位*/
    private int hundredth;

    /*十位*/
    private int ten;

    /*个位*/
    private int theUnit;

    public String getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo;
    }

    public int getHundredth() {
        return hundredth;
    }

    public void setHundredth(int hundredth) {
        this.hundredth = hundredth;
    }

    public int getTen() {
        return ten;
    }

    public void setTen(int ten) {
        this.ten = ten;
    }

    public int getTheUnit() {
        return theUnit;
    }

    public void setTheUnit(int theUnit) {
        this.theUnit = theUnit;
    }

    @Override
    public String toString() {
        return "AwardNo3DPo{" +
                "issueNo='" + issueNo + '\'' +
                ", hundredth=" + hundredth +
                ", ten=" + ten +
                ", theUnit=" + theUnit +
                '}';
    }
}
