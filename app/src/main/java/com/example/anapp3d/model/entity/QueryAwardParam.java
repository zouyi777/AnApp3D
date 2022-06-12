package com.example.anapp3d.model.entity;

import com.example.anapp3d.enums.AwardAttribute;
import com.example.anapp3d.enums.DirectOrGroup;

public class QueryAwardParam {

    private int startPage;
    private int pageSize;
    private AwardAttribute selectAwardAttr;
    private DirectOrGroup selectDirectOrGroup;

    public QueryAwardParam(){
        this.startPage = 0;
        this.pageSize = 30;
        this.selectAwardAttr = AwardAttribute.ODD_EVEN;
        this.selectDirectOrGroup = DirectOrGroup.DIRECT;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public AwardAttribute getSelectAwardAttr() {
        return selectAwardAttr;
    }

    public void setSelectAwardAttr(AwardAttribute selectAwardAttr) {
        this.selectAwardAttr = selectAwardAttr;
    }

    public DirectOrGroup getSelectDirectOrGroup() {
        return selectDirectOrGroup;
    }

    public void setSelectDirectOrGroup(DirectOrGroup selectDirectOrGroup) {
        this.selectDirectOrGroup = selectDirectOrGroup;
    }


    /**
     * startPage自增
     */
    public void startPageSelfAdd(){
        startPage++;
    }

    /**
     * startPage自减
     */
    public void startPageSelfSub(){
        if(startPage<0){
            startPage = 0;
            return;
        }
        startPage--;
    }

}
