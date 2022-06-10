package com.example.anapp3d.model;


import com.example.anapp3d.enums.OddOREven;
import com.example.anapp3d.model.entity.AwardNo3DPo;
import com.example.anapp3d.model.entity.AwardNo3DVo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

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
    public boolean addAwardNo3d(AwardNo3DPo awardNo3D){

        realm.beginTransaction();
        AwardNo3DPo awardNo3DPo = realm.createObject(AwardNo3DPo.class);
        awardNo3DPo.setIssueNo(awardNo3D.getIssueNo());
        awardNo3DPo.setHundredth(awardNo3D.getHundredth());
        awardNo3DPo.setTen(awardNo3D.getTen());
        awardNo3DPo.setTheUnit(awardNo3D.getTheUnit());

        realm.copyToRealm(awardNo3DPo);
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
    public List<AwardNo3DVo> getAwardNoByPage(int startPage, int pageSize){

        RealmResults<AwardNo3DPo> results = realm.where(AwardNo3DPo.class).findAllAsync();
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

        List<AwardNo3DVo> resultList = new ArrayList<>();
        if(startPage < dataList.size()){
            for(int i=startPage;i<dataList.size();i++){
                if(resultList.size() >= pageSize){
                    break;
                }
                AwardNo3DVo awardNo3DVo = new AwardNo3DVo();
                AwardNo3DPo awardNo3DPo = dataList.get(i);
                awardNo3DVo.setIssueNo(awardNo3DPo.getIssueNo());

                int hundredth = awardNo3DPo.getHundredth();
                int ten = awardNo3DPo.getTen();
                int theUnit = awardNo3DPo.getTheUnit();
                awardNo3DVo.setAwardNo(hundredth +String.valueOf(ten)+ theUnit);

                if(OddOREven.ODD_NUMBER.equals(judgeOddOrEven(hundredth))){
                    awardNo3DVo.setFirstOdd("奇");
                    awardNo3DVo.setFirstEven("");
                }else{
                    awardNo3DVo.setFirstOdd("");
                    awardNo3DVo.setFirstEven("偶");
                }

                if(OddOREven.ODD_NUMBER.equals(judgeOddOrEven(ten))){
                    awardNo3DVo.setSecondOdd("奇");
                    awardNo3DVo.setSecondEven("");
                }else{
                    awardNo3DVo.setSecondOdd("");
                    awardNo3DVo.setSecondEven("偶");
                }

                if(OddOREven.ODD_NUMBER.equals(judgeOddOrEven(theUnit))){
                    awardNo3DVo.setThirdOdd("奇");
                    awardNo3DVo.setThirdEven("");
                }else{
                    awardNo3DVo.setThirdOdd("");
                    awardNo3DVo.setThirdEven("偶");
                }

                resultList.add(awardNo3DVo);
            }
        }

        return resultList;
    }

    /**
     * 判断数据奇偶性
     * @param awardNo3DPo
     * @return
     */
    public OddOREven[] judgeOddOrEvenOfAwardNo(AwardNo3DPo awardNo3DPo){

        OddOREven[] result =new OddOREven[3];

        if(awardNo3DPo !=null){

            int hundredth = awardNo3DPo.getHundredth();
            int ten = awardNo3DPo.getTen();
            int theUnit = awardNo3DPo.getTheUnit();

            result[0] = judgeOddOrEven(hundredth);
            result[1] = judgeOddOrEven(ten);
            result[2] = judgeOddOrEven(theUnit);
        }
        return result;
    }

    /**
     * 判断奇偶
     * @param integerNum
     * @return
     */
    public OddOREven judgeOddOrEven(int integerNum){

        if(integerNum % 2 ==0){
            return OddOREven.EVEN_NUMBER;
        }
        return OddOREven.ODD_NUMBER;
    }

    /**
     * 从小到大排序成组合模式
     * @param awardNo3DPo
     * @return
     */
    public List<Integer> getAwardZuheNo(AwardNo3DPo awardNo3DPo){

        List<Integer> zuheNo = new ArrayList<>();
        zuheNo.add(awardNo3DPo.getHundredth());
        zuheNo.add(awardNo3DPo.getTen());
        zuheNo.add(awardNo3DPo.getTheUnit());
        Collections.sort(zuheNo);

        return zuheNo;
    }

}
