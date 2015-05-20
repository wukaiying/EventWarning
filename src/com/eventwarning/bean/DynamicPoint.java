package com.eventwarning.bean;

public class DynamicPoint {
	public String time;
	public String getTime(){
		return this.time;
	}
	public double value;
	public double getValue(){
		return this.getValue();
	}
	public DynamicPoint(String time, double value){
		this.time = time.substring(5,16);
		this.value = value;
	}
}
