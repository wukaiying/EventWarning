package com.eventwarning.bean;

public class CloudWord {
	public String text;
	public int weight;
	public String link;
	public CloudWord(KeyWord w){
		this.text = w.keyword;
		this.weight = w.count;
		this.link = "EventServlet?id="+w.eventID;
	}
	public CloudWord(String text, int weight){
		this.text = text;
		this.weight = weight;
	}
}
