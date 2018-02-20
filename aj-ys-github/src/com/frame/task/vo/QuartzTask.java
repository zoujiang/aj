package com.frame.task.vo;

import java.util.Map;

/**
 * 调度任务实体
 * 
 */
public class QuartzTask {

	private String id;
	private String jobName;// 任务名称
	private String jobGroup;// 任务组
	private String triggerName;// 触发器名称
	private String triggerGroup;// 触发器组
	private Class className;// 类名称
	private String express;// 类名称
	private Map<String, ? extends Object> data;// 数据
	private String[] expresses;// 触发器 Corn表达式
	
	private String jobNameStartWith;
	private String triggerNameStartWith;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	public Class getClassName() {
		return className;
	}

	public void setClassName(Class className) {
		this.className = className;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public String[] getExpresses() {
		return expresses;
	}

	public void setExpresses(String[] expresses) {
		this.expresses = expresses;
	}

	public Map<String, ? extends Object> getData() {
		return data;
	}

	public void setData(Map<String, ? extends Object> data) {
		this.data = data;
	}

	public String getJobNameStartWith() {
		return jobNameStartWith;
	}

	public void setJobNameStartWith(String jobNameStartWith) {
		this.jobNameStartWith = jobNameStartWith;
	}

	public String getTriggerNameStartWith() {
		return triggerNameStartWith;
	}

	public void setTriggerNameStartWith(String triggerNameStartWith) {
		this.triggerNameStartWith = triggerNameStartWith;
	}

}