package com.example.anapp3d.model;


import com.example.anapp3d.model.entity.AwardNo3DPo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class AwardNo3DModel {

    private Realm realm;

    public AwardNo3DModel(){
        realm = Realm.getDefaultInstance();
    }

    /**
     * 新增数据
     * @param awardNo3D
     * @return
     */
    public boolean insertAwardNo3d(AwardNo3DPo awardNo3D){

        realm.beginTransaction();
        AwardNo3DPo awardNo3DPo = realm.createObject(AwardNo3DPo.class, System.currentTimeMillis());
        awardNo3DPo.setIssueNo(awardNo3D.getIssueNo());
        awardNo3DPo.setHundredth(awardNo3D.getHundredth());
        awardNo3DPo.setTen(awardNo3D.getTen());
        awardNo3DPo.setTheUnit(awardNo3D.getTheUnit());

        realm.copyToRealm(awardNo3DPo);
        realm.commitTransaction();

        return true;
    }

    /**
     * 更新奖号
     * @param awardNo3D
     * @return
     */
    public boolean updateAwardNo3d(AwardNo3DPo awardNo3D){
        realm.beginTransaction();
        // 若是有相同的主键，将更新对象
        // 若是主键不一致，将建立新的对象
        realm.copyToRealmOrUpdate(awardNo3D);
        realm.commitTransaction();
        return true;
    }

    /**
     * 查询所有
     * @return
     */
    public  List<AwardNo3DPo> getAwardNoAll(){
        List<AwardNo3DPo> result = new ArrayList<>();
        RealmResults<AwardNo3DPo> results = realm.where(AwardNo3DPo.class).findAllAsync();
        for (AwardNo3DPo awardNo3DPo : results) {
            result.add(awardNo3DPo);
        }
        return result;
    }

    /**
     * 分页查询
     * @return
     */
    public List<AwardNo3DPo> getAwardNoByPage(int startPage, int pageSize){

        RealmResults<AwardNo3DPo> results = realm.where(AwardNo3DPo.class).findAllAsync();
        results = results.sort("issueNo",Sort.DESCENDING);//根据期号倒序
        List<AwardNo3DPo> dataList = new ArrayList<>();
        for (AwardNo3DPo awardNo3DPo : results) {
            dataList.add(awardNo3DPo);
        }

        int topIndex = dataList.size() - pageSize;
        if(topIndex<=0){
            topIndex = dataList.size()-1;
        }
        if(startPage>=topIndex){
            startPage = topIndex;
        }

        if(startPage <0){
            startPage = 0;
        }

        List<AwardNo3DPo> resultList = new ArrayList<>();
        if(startPage < dataList.size()){
            for(int i=startPage;i<dataList.size();i++){
                if(resultList.size() >= pageSize){
                    break;
                }
                resultList.add(dataList.get(i));
            }
        }
        return resultList;
    }



}
