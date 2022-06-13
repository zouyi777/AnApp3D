package com.example.anapp3d.presenter;

import com.example.anapp3d.enums.AwardAttribute;
import com.example.anapp3d.enums.DataType;
import com.example.anapp3d.enums.DirectOrGroup;
import com.example.anapp3d.enums.LargeOrSmall;
import com.example.anapp3d.enums.OddOREven;
import com.example.anapp3d.model.AwardNo3DModel;
import com.example.anapp3d.model.entity.AwardNo3DPo;
import com.example.anapp3d.model.entity.AwardNo3DVo;
import com.example.anapp3d.model.entity.QueryAwardParam;
import com.example.anapp3d.utils.NumberUtil;
import com.example.anapp3d.view.ViewCallBack;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AwardPresenter implements Presenter{

    private AwardNo3DModel awardNo3DModel;
    private ViewCallBack viewCallBack;

    public AwardPresenter(){
        this.awardNo3DModel = new AwardNo3DModel();
    }

    /**
     * 新增奖号
     * @param awardNo3D
     */
    public void addAwardNo3d(AwardNo3DPo awardNo3D){
        awardNo3DModel.insertAwardNo3d(awardNo3D,System.currentTimeMillis());
    }

    /**
     * 修改奖号
     * @param awardNo3D
     */
    public void editAwardNo3d(AwardNo3DPo awardNo3D){
        awardNo3DModel.updateAwardNo3d(awardNo3D);
    }

    public AwardNo3DPo getLastAwardNo(){
        return awardNo3DModel.getLastAwardNo();
    }

    /**
     * 获取奇偶数据
     * @return
     */
    public List<AwardNo3DVo> getAwardNoVoList(QueryAwardParam queryAwardParam){

        List<AwardNo3DPo> dataList = awardNo3DModel.getAwardNoByPage(queryAwardParam.getStartPage(),queryAwardParam.getPageSize());

        List<AwardNo3DVo> resultList = new ArrayList<>();
        exportAwardNo(dataList,resultList,queryAwardParam);
        return resultList;
    }

    /**
     * 整理组装数据
     * @param resultList
     * @param resultList
     */
    private void exportAwardNo(List<AwardNo3DPo> dataList,List<AwardNo3DVo> resultList, QueryAwardParam queryAwardParam){

        int firstLeftTimes = 0;
        int firstRightTimes = 0;

        int secondLeftTimes = 0;
        int secondRightTimes = 0;

        int thirdLeftTims = 0;
        int thirdRightTimes = 0;

        for(int i=dataList.size()-1;i>=0;i--){

            AwardNo3DPo awardNo3DPo = dataList.get(i);

            if(DirectOrGroup.GROUP.equals(queryAwardParam.getSelectDirectOrGroup())){
                awardNo3DPo = NumberUtil.getAwardZuheNo(awardNo3DPo);
            }

            AwardNo3DVo awardNo3DVo = new AwardNo3DVo();
            awardNo3DVo.setId(awardNo3DPo.getId());
            awardNo3DVo.setDataType(DataType.AWARD_NO);
            awardNo3DVo.setIssueNo(awardNo3DPo.getIssueNo()+"");

            int hundredth = awardNo3DPo.getHundredth();
            int ten = awardNo3DPo.getTen();
            int theUnit = awardNo3DPo.getTheUnit();
            awardNo3DVo.setAwardNo(hundredth +String.valueOf(ten)+ theUnit);

            if(AwardAttribute.ODD_EVEN.equals(queryAwardParam.getSelectAwardAttr())){
                if(OddOREven.ODD_NUMBER.equals(NumberUtil.judgeOddOrEven(hundredth))){
                    awardNo3DVo.setFirstLeft("奇");
                    awardNo3DVo.setFirstRight("");
                    firstLeftTimes++;
                }else{
                    awardNo3DVo.setFirstLeft("");
                    awardNo3DVo.setFirstRight("偶");
                    firstRightTimes++;
                }

                if(OddOREven.ODD_NUMBER.equals(NumberUtil.judgeOddOrEven(ten))){
                    awardNo3DVo.setSecondLeft("奇");
                    awardNo3DVo.setSecondRight("");
                    secondLeftTimes++;
                }else{
                    awardNo3DVo.setSecondLeft("");
                    awardNo3DVo.setSecondRight("偶");
                    secondRightTimes++;
                }

                if(OddOREven.ODD_NUMBER.equals(NumberUtil.judgeOddOrEven(theUnit))){
                    awardNo3DVo.setThirdLeft("奇");
                    thirdLeftTims++;
                }else{
                    awardNo3DVo.setThirdRight("偶");
                    thirdRightTimes++;
                }
            }else if(AwardAttribute.LARGE_SMALL.equals(queryAwardParam.getSelectAwardAttr())){
                if(LargeOrSmall.LARGE.equals(NumberUtil.judegeLargeSmall(hundredth))){
                    awardNo3DVo.setFirstLeft("大");
                    firstLeftTimes++;
                }else{
                    awardNo3DVo.setFirstRight("小");
                    firstRightTimes++;
                }

                if(LargeOrSmall.LARGE.equals(NumberUtil.judegeLargeSmall(ten))){
                    awardNo3DVo.setSecondLeft("大");
                    secondLeftTimes++;
                }else{
                    awardNo3DVo.setSecondRight("小");
                    secondRightTimes++;
                }

                if(LargeOrSmall.LARGE.equals(NumberUtil.judegeLargeSmall(theUnit))){
                    awardNo3DVo.setThirdLeft("大");
                    thirdLeftTims++;
                }else{
                    awardNo3DVo.setThirdRight("小");
                    thirdRightTimes++;
                }
            }

            resultList.add(awardNo3DVo);
        }

        AwardNo3DVo awardNo3DVoTims = new AwardNo3DVo();
        awardNo3DVoTims.setDataType(DataType.APPEAR_TIMES);
        awardNo3DVoTims.setIssueNo("出现次数");
        awardNo3DVoTims.setAwardNo("-");
        awardNo3DVoTims.setFirstLeft(firstLeftTimes+"");
        awardNo3DVoTims.setFirstRight(firstRightTimes+"");
        awardNo3DVoTims.setSecondLeft(secondLeftTimes+"");
        awardNo3DVoTims.setSecondRight(secondRightTimes+"");
        awardNo3DVoTims.setThirdLeft(thirdLeftTims+"");
        awardNo3DVoTims.setThirdRight(thirdRightTimes+"");
        resultList.add(awardNo3DVoTims);
    }

    public String exportAwardNo(){
        String result = awardNo3DModel.exportAwardNo();
        if(!result.contains(AwardNo3DModel.EXPORT_AWARD_ERROR)){
            result = "数据导出成功，文件目录="+ result;
        }
        return result;
    }

    public String importAwardNo(InputStream in){
        String result = awardNo3DModel.importAwardNo(in);
        if(!result.contains(AwardNo3DModel.IMPORT_AWARD_ERROR)){
            result = "数据导入成功";
        }
        return result;
    }

    @Override
    public void onDestroy() {
        this.viewCallBack = null;
    }
}
