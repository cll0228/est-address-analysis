package com.lezhi.adminlj.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lezhi.adminlj.pojo.Common;
import com.lezhi.adminlj.pojo.SearchCondition;

/**
 * Created by Cuill on 2017/2/15.
 */
@Controller
public class SearchConditionController {
    @RequestMapping(value = "getSearchCond", method = RequestMethod.GET)
    @ResponseBody
    public SearchCondition getSearchCond() {
        SearchCondition condition = new SearchCondition();
        List<Common> usrScale = new ArrayList<>();// 用户规模
        usrScale.add(new Common("u", "100户以下", "a1", "1"));
        usrScale.add(new Common("u", "100-200户", "a2", "2"));
        usrScale.add(new Common("u", "200-500户", "a3", "3"));
        usrScale.add(new Common("u", "500户以上", "a4", "4"));
        condition.setUserScale(usrScale);

        List<Common> residenceScale = new ArrayList<>();
        residenceScale.add(new Common("r", "500户以下", "r500", "1"));
        residenceScale.add(new Common("r", "500-1000户", "r1000", "2"));
        residenceScale.add(new Common("r", "1000-2000户", "r2000", "3"));
        residenceScale.add(new Common("r", "2000户以上", "r3000", "4"));
        condition.setResidenceScale(residenceScale);

        List<Common> residenceAvg = new ArrayList<>();
        residenceAvg.add(new Common("p", "2万以下", "r2", "1"));
        residenceAvg.add(new Common("p", "2-3万", "r3", "2"));
        residenceAvg.add(new Common("p", "3-5万", "r4", "3"));
        residenceAvg.add(new Common("p", "5-8万", "r5", "4"));
        residenceAvg.add(new Common("p", "8万以上", "r8", "45"));
        condition.setResidenceAvg(residenceAvg);

        List<Common> residenceKind = new ArrayList<>();
        residenceKind.add(new Common("t","高级小区","t1","1"));
        residenceKind.add(new Common("t","中高级小区","t2","2"));
        residenceKind.add(new Common("t","普通小区","t3","3"));
        condition.setResidenceKind(residenceKind);

        List<Common> residenceUserProportion = new ArrayList<>();
        residenceUserProportion.add(new Common("b","10%以下","b1","1"));
        residenceUserProportion.add(new Common("b","10%-20%","b2","2"));
        residenceUserProportion.add(new Common("b","20%-30%","b3","3"));
        residenceUserProportion.add(new Common("b","30%-50%","b4","4"));
        residenceUserProportion.add(new Common("b","50%以上","b5","5"));
        condition.setResidenceUserProportion(residenceUserProportion);

        List<Common> estateTotalValue = new ArrayList<>();
        estateTotalValue.add(new Common("g", "300万以下", "g300", "1"));
        estateTotalValue.add(new Common("g", "300万-500万", "g500", "2"));
        estateTotalValue.add(new Common("g", "500万-800万", "g800", "3"));
        estateTotalValue.add(new Common("g", "800万-1000万", "g1000", "4"));
        estateTotalValue.add(new Common("g", "1000万-1500万", "g1500", "5"));
        estateTotalValue.add(new Common("g", "1500万-3000万", "g3000", "6"));
        estateTotalValue.add(new Common("g", "3000万以上", "g3100", "7"));
        condition.setEstateTotalValue(estateTotalValue);

        List<Common> billActDegree = new ArrayList<>();
        billActDegree.add(new Common("d", "3个月内有缴费记录", "d3", "1"));
        billActDegree.add(new Common("d", "6个月内有缴费记录", "d6", "2"));
        billActDegree.add(new Common("d", "12个月内有缴费记录", "d12", "3"));
        billActDegree.add(new Common("d", "24个月内有缴费记录", "d24", "4"));
        condition.setBillActDegree(billActDegree);

        List<Common> ifSubIncrement = new ArrayList<>();
        ifSubIncrement.add(new Common("j", "是", "j1", "1"));
        ifSubIncrement.add(new Common("j", "否", "j2", "2"));
        condition.setIfSubIncrement(ifSubIncrement);
        return condition;
    }
}
