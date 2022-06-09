package com.example.anapp3d.model;

import android.util.Log;
import android.widget.Toast;

import com.example.anapp3d.enums.OddOREven;
import com.example.anapp3d.model.entity.AwardNo3D;
import java.util.ArrayList;
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
    public boolean addAwardNo3d(AwardNo3D awardNo3D){

        realm.beginTransaction();
        AwardNo3D awardNo3DPo = realm.createObject(AwardNo3D.class);
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
    public  List<AwardNo3D> getAwardNoAll(){
        List<AwardNo3D> result = new ArrayList<>();
        RealmResults<AwardNo3D> results = realm.where(AwardNo3D.class).findAllAsync();
        for (AwardNo3D awardNo3D : results) {
            result.add(awardNo3D);
        }
        return result;
    }

    /**
     * 分页查询
     * @return
     */
    public List<AwardNo3D> getAwardNoByPage(int startPage,int pageSize){

        RealmResults<AwardNo3D> results = realm.where(AwardNo3D.class).findAllAsync();
        List<AwardNo3D> dataList = new ArrayList<>();
        for (AwardNo3D awardNo3D : results) {
            dataList.add(awardNo3D);
        }

        int topIndex = dataList.size() - pageSize;
        if(topIndex<=0){
            topIndex = dataList.size()-1;
        }
        if(startPage>=topIndex){
            startPage = topIndex;
        }
        List<AwardNo3D> resultList = new ArrayList<>();
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

    /**
     * 判断数据奇偶性
     * @param awardNo3D
     * @return
     */
    public OddOREven[] judgeOddOrEvenOfAwardNo(AwardNo3D awardNo3D){

        OddOREven[] result =new OddOREven[3];

        if(awardNo3D !=null){

            int hundredth = awardNo3D.getHundredth();
            int ten = awardNo3D.getTen();
            int theUnit = awardNo3D.getTheUnit();

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

}
