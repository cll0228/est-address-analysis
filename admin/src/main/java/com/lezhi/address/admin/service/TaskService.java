package com.lezhi.address.admin.service;

import com.lezhi.address.admin.pojo.Address;

/**
 * 解析和匹配任务流程管理
 * Created by Colin Yan on 2017/2/7.
 */
public interface TaskService {

    /**
     * 匹配单条地址，适用于手动执行匹配
     *
     * @param analysisTaskId 对应 t_analysis_task表id
     * @param addressId      对应具体地址表id，如abc_address
     * @param operatorUserId 操作人员，从session中获取
     * @return 返回执行完匹配操作以后的地址值，主要是写入 match_status和match_time两个字段
     */
    public Address match(int analysisTaskId, int addressId, int operatorUserId);

    /**
     * 新建解析任务，此方法中除了要写入解析任务表数据以外，还要【异步】调用地址解析接口，开始地址解析任务
     * 每解析完100条，更新t_analysis_task中的success_count和failed_count
     *
     * @param datasourceId     数据源id，对应t_datasource表id
     * @param targetTableName  目前地址表id，如abc_address，表示农行复评地址表
     * @param targetColumnName 目前地址表的中表示地址的字段，如address
     * @param taskName         一个好识别的名字
     * @param autoMatch        是否在解析完成以后自动开启匹配任务，如果为true，则在解析线程执行完以后，调用createMatchTask方法
     * @param operatorUserId   操作人员，从session中获取
     * @return 返回新建的task id
     */
    public int createAnalysisTask(int datasourceId, String targetTableName, String targetColumnName, String taskName, boolean autoMatch, int operatorUserId);

    /**
     * 新建匹配任务，此方法中除了要写入匹配任务表数据以外，还要【异步】调用匹配接口，开始地址匹配任务
     * 每匹配完100条，更新t_analysis_task中的success_count和failed_count
     *
     * @param analysisTaskId 对应的地址解析任务id
     * @param unMatchedOnly  仅去匹配没匹配过的地址，即abc_address表中 match_status不等于10的记录
     * @return 返回新建的匹配任务id
     */
    public int createMatchTask(int analysisTaskId, boolean unMatchedOnly);
}
