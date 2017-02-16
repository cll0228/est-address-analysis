package com.lezhi.adminlj.pojo;

import java.util.List;

/**
 * Created by Cuill on 2017/2/15.
 */
public class SearchCondition {
    private List userScale;//用户规模
    private List residenceScale;//小區規模;
    private List residenceAvg;//小区均价；

    private List residenceKind;//小区分类
    private List residenceUserProportion;//小区用户占比
    private List estateTotalValue;//不動產估值

    private List billActDegree;//账单活跃度
    private List ifSubIncrement;//是否订阅增值节目

    public List getResidenceKind() {
        return residenceKind;
    }

    public void setResidenceKind(List residenceKind) {
        this.residenceKind = residenceKind;
    }

    public List getResidenceUserProportion() {
        return residenceUserProportion;
    }

    public void setResidenceUserProportion(List residenceUserProportion) {
        this.residenceUserProportion = residenceUserProportion;
    }

    public List getBillActDegree() {
        return billActDegree;
    }

    public void setBillActDegree(List billActDegree) {
        this.billActDegree = billActDegree;
    }

    public List getIfSubIncrement() {
        return ifSubIncrement;
    }

    public void setIfSubIncrement(List ifSubIncrement) {
        this.ifSubIncrement = ifSubIncrement;
    }

    public List getEstateTotalValue() {
        return estateTotalValue;
    }

    public void setEstateTotalValue(List estateTotalValue) {
        this.estateTotalValue = estateTotalValue;
    }

    public List getUserScale() {
        return userScale;
    }

    public void setUserScale(List userScale) {
        this.userScale = userScale;
    }

    public List getResidenceScale() {
        return residenceScale;
    }

    public void setResidenceScale(List residenceScale) {
        this.residenceScale = residenceScale;
    }

    public List getResidenceAvg() {
        return residenceAvg;
    }

    public void setResidenceAvg(List residenceAvg) {
        this.residenceAvg = residenceAvg;
    }
}
