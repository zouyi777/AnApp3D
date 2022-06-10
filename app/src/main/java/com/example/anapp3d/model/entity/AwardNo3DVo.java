package com.example.anapp3d.model.entity;

public class AwardNo3DVo{

    private String issueNo;
    private String awardNo;

    private String firstOdd;
    private String firstEven;

    private String secondOdd;
    private String secondEven;

    private String thirdOdd;
    private String thirdEven;

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

    public String getFirstOdd() {
        return firstOdd;
    }

    public void setFirstOdd(String firstOdd) {
        this.firstOdd = firstOdd;
    }

    public String getFirstEven() {
        return firstEven;
    }

    public void setFirstEven(String firstEven) {
        this.firstEven = firstEven;
    }

    public String getSecondOdd() {
        return secondOdd;
    }

    public void setSecondOdd(String secondOdd) {
        this.secondOdd = secondOdd;
    }

    public String getSecondEven() {
        return secondEven;
    }

    public void setSecondEven(String secondEven) {
        this.secondEven = secondEven;
    }

    public String getThirdOdd() {
        return thirdOdd;
    }

    public void setThirdOdd(String thirdOdd) {
        this.thirdOdd = thirdOdd;
    }

    public String getThirdEven() {
        return thirdEven;
    }

    public void setThirdEven(String thirdEven) {
        this.thirdEven = thirdEven;
    }

    @Override
    public String toString() {
        return "AwardNo3DVo{" +
                "issueNo='" + issueNo + '\'' +
                ", awardNo='" + awardNo + '\'' +
                ", firstOdd='" + firstOdd + '\'' +
                ", firstEven='" + firstEven + '\'' +
                ", secondOdd='" + secondOdd + '\'' +
                ", secondEven='" + secondEven + '\'' +
                ", thirdOdd='" + thirdOdd + '\'' +
                ", thirdEven='" + thirdEven + '\'' +
                '}';
    }
}
