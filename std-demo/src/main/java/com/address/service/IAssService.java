package com.address.service;


import com.address.model.AssessParam;
import com.address.model.AssessResult;

import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2016/3/30.
 */
public interface IAssService {

    /**
     * 1,待股价房屋估价，通过传递的estimation对象构建待股价房屋的参数信息inMap：包括小区id、房屋类型、房屋总层数、房屋面积估价时间段、 总房间数。<br/>
     * inMap2：小区id，经纬度 ，小区竣工日期；imMap3：是否有地下室（别墅包含地下室），包括小区id、房屋类型、房屋总层数、房屋面积估价时间段、总房间数<br/>
     * 待股价房屋的小区id获取该小区为空，返回估价模型对象TechnicalDto(null, null, estimation);<br/>
     * 2,通过getFinalDealList方法，传入参数inMap和待估对象estimation获取参与估价的交易可比案例;<br/>
     * 3,通过getFinalHangList方法，传入参数inMap3和待估对象estimation获取参与估价的挂牌可比案例;<br/>
     * 4,估价方法：caculate，传入参数inMap，挂牌案例集合
     * ，交易案例集合，和待股价对象estimation，根据挂牌案例，交易案例进行估价，返回待估房屋价格price，如果price为null
     * ，直接返回TechnicalDto，包含交易和挂牌可比案例，待估对象;<br/>
     * 5,assessPrice，如果一阶段的估出的价格比所有的交易案例和挂牌案例的价格都大，那将一二手房的价格指数再次进行估价，并最终返回price；<br/>
     * 6,设置待股价的对象的最终返回属性:<br/>
     * 时间单价：assTimeUnitPrice，时点总价：assTimeTotalPrice，最终单价(一个月内单价均价)：assUnitPrice，<br/>
     * 最终总价 (一个月内总价均价)：assTotalPrice；返回TechnicalDto(hangList, dealList, estimation)<br/>
     * 。
     *
     * @param assessParam 待估对象
     * @param assDate 估价时间点
     * @return TechnicalDto对象，包含交易可比案例集合，挂牌案例集合以及待估对象的所有信息
     */
    AssessResult assHouse(AssessParam assessParam, Date assDate);

    /**
     * @see com.lezhi.sh.ass.house.service.IAssService#assHouse(com.lezhi.sh.ass.house.model.AssessParam, java.util.Date)
     */
    AssessResult assHouse(AssessParam assessParam);

    /**
     * 某一时间段内跟踪评估
     *
     * @param assessParam 待估对象
     * @param frequency 频率 month/week
     * @return 指定周期内的价格
     *//*
    List<PeriodAssessResult> assFollowHouse(AssessParam assessParam, Date begin, Date end, String frequency);

    *//**
     * 通过小区参数获取业主端建议报价
     *
     * @param caseQueryCondition 小区参数
     * @return List<ComparableCase> 小区业主端建议报价集合
     *//*
    List<ComparableCase> querySimilarHouse(CaseQueryCondition caseQueryCondition);*/

    /**
     * 通过小区ID获取二手房交易均价
     *
     * @param residenceId 小区ID
     * @return List<String> 小区一年内二手房交易均价
     */
    List<String> getResidenceAvgPriceList(Integer residenceId);

    /**
     * 根据时间段和价格指数对评估单价进行二次评估
     *
     * @param assessParam 待估对象
     * @param date 时间日期
     * @return 最终返回估价实例对象TechnicalDto
     */
    AssessResult assessDatePrice(AssessParam assessParam, Date date);

    /**
     * 获取可比案例详情
     * @param hangIds 挂牌可比案例ID集合
     * @param dealIds 交易可比案例ID集合
     * @return 可比案例详情
     *//*
    ComparableCaseWrapperVO queryAssComparableCases(List<Integer> hangIds, List<Integer> dealIds);*/
}
