package com.example.anapp3d.model;


import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.example.anapp3d.model.entity.AwardNo3DPo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class AwardNo3DModel {

    public static String TAG= AwardNo3DModel.class.getName();
    public static String EXPORT_FILE_NAME = "AnApp3d_data.txt";
    public static String IMPORT_AWARD_ERROR = "import award error";
    public static String EXPORT_AWARD_ERROR = "export award error";
    public static String DIVIDER_OF_ISSUENO_AWARD = "===";
    private Realm realm;

    public AwardNo3DModel(){
        realm = Realm.getDefaultInstance();
    }

    /**
     * 新增数据
     * @param awardNo3D
     * @return
     */
    public boolean insertAwardNo3d(AwardNo3DPo awardNo3D,Long id){

        realm.beginTransaction();
        AwardNo3DPo awardNo3DPo;
        if(id == null){
            awardNo3DPo = realm.createObject(AwardNo3DPo.class, System.currentTimeMillis());
        }else{
            awardNo3DPo = realm.createObject(AwardNo3DPo.class, id);
        }
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

    public AwardNo3DPo getLastAwardNo(){
        List<AwardNo3DPo> result = getAwardNoAll();
        return result.get(result.size()-1);
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

    /**
     * 导出奖号数据
     * @return
     */
    public String exportAwardNo(){

        List<AwardNo3DPo> listAwardNo = getAwardNoAll();
//        File mPath = Environment.getExternalStorageDirectory();
        // 小米手机不允许在外部存储的根目录下写文件，只能在Download, Documents目录下写
        File mPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        String fileName = mPath + File.separator+EXPORT_FILE_NAME;
        try {
            FileWriter fw = new FileWriter(fileName, false);
            BufferedWriter bw = new BufferedWriter(fw);
            for(AwardNo3DPo awardNo3DPo:listAwardNo){
                String rawData = awardNo3DPo.getIssueNo()+DIVIDER_OF_ISSUENO_AWARD+awardNo3DPo.getHundredth()+
                                 awardNo3DPo.getTen()+awardNo3DPo.getTheUnit()+"\r\n ";
                bw.write(rawData);
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            Log.e(TAG,"导出奖号数据出错",e);
            return EXPORT_AWARD_ERROR+"="+e.getMessage();
        }
        return fileName;
    }

    /**
     * 导入奖号数据
     * @return
     */
    public String importAwardNo(){

        File mPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        String fileName = mPath + File.separator+EXPORT_FILE_NAME;
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);

            String rawData;
            int i=0;
            while ((rawData = br.readLine().trim()) != null && rawData.length() > 0){

                String[] rawDataArr = rawData.split(DIVIDER_OF_ISSUENO_AWARD);
                Long issueNo = Long.valueOf(rawDataArr[0].trim());
                char[] awardNo = rawDataArr[1].trim().toCharArray();

                int hundredth = awardNo[0]-'0';
                int ten = awardNo[1]-'0';
                int theUnit = awardNo[2]-'0';
                AwardNo3DPo awardNo3D = new AwardNo3DPo();
                awardNo3D.setIssueNo(issueNo);
                awardNo3D.setHundredth(hundredth);
                awardNo3D.setTen(ten);
                awardNo3D.setTheUnit(theUnit);

                insertAwardNo3d(awardNo3D,System.currentTimeMillis()+i);
                i++;
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            Log.e(TAG,"导出奖号数据出错",e);
            return IMPORT_AWARD_ERROR+"="+e.getMessage();
        }
        return "success";
    }
}
