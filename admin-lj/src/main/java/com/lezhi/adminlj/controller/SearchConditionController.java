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
        usrScale.add(new Common("a", "100户以下", "a1", "1"));
        usrScale.add(new Common("a", "100-200户", "a2", "2"));
        usrScale.add(new Common("a", "200-500户", "a3", "3"));
        usrScale.add(new Common("a", "500户以上", "a4", "4"));
        condition.setUserScale(usrScale);

        List<Common> residenceScale = new ArrayList<>();
        residenceScale.add(new Common("l", "500户以下", "l500", "1"));
        residenceScale.add(new Common("l", "500-1000户", "l1000", "2"));
        residenceScale.add(new Common("l", "1000-2000户", "l2000", "3"));
        residenceScale.add(new Common("l", "2000户以上", "l3000", "4"));
        condition.setResidenceScale(residenceScale);

        List<Common> residenceAvg = new ArrayList<>();
        residenceAvg.add(new Common("p", "3万以下", "p2", "1"));
        residenceAvg.add(new Common("p", "3-5万", "p3", "2"));
        residenceAvg.add(new Common("p", "5-8万", "p4", "3"));
        residenceAvg.add(new Common("p", "8-10万", "p5", "4"));
        residenceAvg.add(new Common("p", "10万以上", "p8", "45"));
        condition.setResidenceAvg(residenceAvg);

        List<Common> residenceKind = new ArrayList<>();
        residenceKind.add(new Common("t","高级小区","t1","1"));
        residenceKind.add(new Common("t","中高级小区","t2","2"));
        residenceKind.add(new Common("t","普通小区","t3","3"));
        condition.setResidenceKind(residenceKind);

        List<Common> residenceUserProportion = new ArrayList<>();
        residenceUserProportion.add(new Common("c","10%以下","c1","1"));
        residenceUserProportion.add(new Common("c","10%-20%","c2","2"));
        residenceUserProportion.add(new Common("c","20%-30%","c3","3"));
        residenceUserProportion.add(new Common("c","30%-50%","c4","4"));
        residenceUserProportion.add(new Common("c","50%以上","c5","5"));
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
        billActDegree.add(new Common("o", "3个月内有缴费记录", "o3", "1"));
        billActDegree.add(new Common("o", "6个月内有缴费记录", "o6", "2"));
        billActDegree.add(new Common("o", "12个月内有缴费记录", "o12", "3"));
        billActDegree.add(new Common("o", "24个月内有缴费记录", "o24", "4"));
        condition.setBillActDegree(billActDegree);

        List<Common> ifSubIncrement = new ArrayList<>();
        ifSubIncrement.add(new Common("f", "是", "f1", "1"));
        ifSubIncrement.add(new Common("f", "否", "f2", "2"));
        condition.setIfSubIncrement(ifSubIncrement);
        return condition;
    }
}
