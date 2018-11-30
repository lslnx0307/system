package com.jsjf.model.member;

public class JsMemberTask {
	private Integer id;//Id自增长
	private String name;//任务名称
	private Integer type;//任务类型1-新手任务2-每月任务 3-每日任务
	private Integer wealth;//财富值
	private Integer valid;//是否禁用 1-可用 0-不可用
	public JsMemberTask() {
		super();
	}
	public JsMemberTask(String name, Integer type, Integer wealth, Integer valid) {
		super();
		this.name = name;
		this.type = type;
		this.wealth = wealth;
		this.valid = valid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getWealth() {
		return wealth;
	}
	public void setWealth(Integer wealth) {
		this.wealth = wealth;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}

}
