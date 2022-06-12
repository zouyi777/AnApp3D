package com.example.anapp3d.utils;

import com.example.anapp3d.enums.LargeOrSmall;
import com.example.anapp3d.enums.OddOREven;
import com.example.anapp3d.model.entity.AwardNo3DPo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NumberUtil {

    /**
     * 判断奇偶
     * @param integerNum
     * @return
     */
    public static OddOREven judgeOddOrEven(int integerNum){

        if(integerNum % 2 ==0){
            return OddOREven.EVEN_NUMBER;
        }
        return OddOREven.ODD_NUMBER;
    }

    /**
     * 判断大小
     * @param integerNum
     * @return
     */
    public static LargeOrSmall judegeLargeSmall(int integerNum){
        if (integerNum >=5){
            return LargeOrSmall.LARGE;
        }
        return LargeOrSmall.SMALL;
    }

    /**
     * 从小到大排序成组合模式
     * @param awardNo3DPo
     * @return
     */
    public static AwardNo3DPo getAwardZuheNo(AwardNo3DPo awardNo3DPo){

        List<Integer> zuheNo = new ArrayList<>();
        zuheNo.add(awardNo3DPo.getHundredth());
        zuheNo.add(awardNo3DPo.getTen());
        zuheNo.add(awardNo3DPo.getTheUnit());
        Collections.sort(zuheNo);

        AwardNo3DPo awardNo3Res = new AwardNo3DPo();
        awardNo3Res.setIssueNo(awardNo3DPo.getIssueNo());
        awardNo3Res.setHundredth(zuheNo.get(0));
        awardNo3Res.setTen(zuheNo.get(1));
        awardNo3Res.setTheUnit(zuheNo.get(2));

        return awardNo3Res;
    }
}
