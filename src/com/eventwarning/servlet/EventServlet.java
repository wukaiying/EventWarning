package com.eventwarning.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eventwarning.bean.DynamicPoint;
import com.eventwarning.bean.Event;
import com.eventwarning.bean.EventDynamic;
import com.eventwarning.bean.KeyWord;
import com.eventwarning.bean.WarningData;
import com.eventwarning.dbImpl.DBOperation;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Position;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.style.ItemStyle;

public class EventServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public EventServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int eventid = Integer.parseInt(request.getParameter("eid"));
		Event e = DBOperation.GetEvent(eventid);
		WarningData wd = new WarningData();
	    List<KeyWord> keywords = DBOperation.GetKeyWordList(20, eventid);
	    List<EventDynamic> edlist = DBOperation.GetEventDyanmicList(eventid, 100);
		
		GsonOption keywordbar = KeyWordOption(e.getKeyWordList());
		//String keywordbarstr = keywordbar.toString().replaceFirst("bar", "treemap");

		GsonOption hotline = HotLineOption(Event.GetHotPoints(edlist));
		GsonOption sentimentpie = SentimentPieOption(edlist);
		GsonOption warningbar = WarningOption(wd, edlist.get(edlist.size()-1));
		
		request.setAttribute("eid", eventid);
		request.setAttribute("event", e);
		
		request.setAttribute("keywordbar", keywordbar.toString());
		request.setAttribute("hotline", hotline.toString());
		request.setAttribute("sentimentpie", sentimentpie.toString());
		request.setAttribute("warningbar", warningbar.toString());
		request.setAttribute("warningd", wd.getWarnD(edlist.get(edlist.size()-1)));
		
		ServletContext servletContext = getServletContext();
		RequestDispatcher dispather = servletContext.getRequestDispatcher("/eventdetail.jsp");
		dispather.forward(request, response);
	}
	
	public GsonOption WarningOption(WarningData wd, EventDynamic ed){
		GsonOption option = new GsonOption();
		option.title().text("舆情指数").subtext("事件舆情指标体系数据");
		option.tooltip().trigger(Trigger.axis);
		option.legend().data().add("指标强度");
		option.calculable(true);
		ValueAxis xAxis = new ValueAxis();
		xAxis.type(AxisType.value).boundaryGap(0.00, 0.01);
		xAxis.axisLabel().formatter("{value} %");
		option.xAxis(xAxis);
		ValueAxis yAxis = new ValueAxis();
		yAxis.type(AxisType.category);
		yAxis.data("事件热度","情感指数","舆情强度","讨论活度");
		option.yAxis(yAxis);
		
		Bar bar = new Bar();
		bar.data(wd.getHotD(ed), wd.getSentimentD(ed), wd.getStrongD(ed), wd.getActiveD(ed));
		ItemStyle istyle = new ItemStyle();
		istyle.normal().lineStyle().width(1);
		bar.itemStyle(istyle);
		option.series(bar);
		return option;
	}
	public GsonOption KeyWordListOption(List<KeyWord> words){
		GsonOption option = new GsonOption();
		option.title().text("关键字").subtext("事件相关关键字提及次数");
		option.tooltip().trigger(Trigger.item).formatter("{b}: {c}");
		option.calculable(false);
		
		Bar bar = new Bar();
		bar.name("矩形图").type(SeriesType.bar);
		ItemStyle istyle = new ItemStyle();
		istyle.normal().label().show(true).formatter("{b}");
		istyle.normal().borderWidth(1);
		istyle.emphasis().label().show(true);
		bar.itemStyle(istyle);
		for(int i=0;i<words.size(); i++)
			bar.data().add(MapSeriesData(words.get(i).keyword, words.get(i).sumcount));
		option.series(bar);
		return option;
	}
	public GsonOption KeyWordOption(List<KeyWord> words){
		GsonOption option = new GsonOption();
		option.title().text("关键词组成").subtext("事件相关关键词词频比较");
		
		option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
		option.legend().data().add("词频");
		
	    ValueAxis xAxis = new ValueAxis();
	    xAxis.type(AxisType.category).splitLine().show(false);
	    for(int i=0;i<words.size(); i++)
	    	xAxis.data().add(words.get(i).keyword);
	    option.xAxis(xAxis);

	    ValueAxis yAxis = new ValueAxis();
	    yAxis.type(AxisType.value);
	    option.yAxis(yAxis);

	    Bar bar = new Bar();
	    bar.name("提及次数").type(SeriesType.bar);
	    for(int i=0;i<words.size(); i++)
	    	bar.data().add(words.get(i).count);
	    ItemStyle istyle = new ItemStyle();
	    istyle.normal().label().show(true).position(Position.inside);
	    bar.itemStyle(istyle);
	    option.series(bar);
	    return option;
	}
	public GsonOption HotLineOption(List<DynamicPoint> pts){
		GsonOption option = new GsonOption();
		option.title().text("事件热度变化趋势").subtext("热度值动态曲线");
		option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
		option.legend().data().add("热度值");
		
		option.calculable(true);
		
	    ValueAxis xAxis = new ValueAxis();
	    xAxis.type(AxisType.category);
	    for(int i=0;i<pts.size(); i++)
	    	xAxis.data().add(pts.get(i).time);
	    option.xAxis(xAxis);

	    ValueAxis yAxis = new ValueAxis();
	    yAxis.type(AxisType.value);
	    option.yAxis(yAxis);

	    Line bar = new Line();
	    bar.name("热度").type(SeriesType.line);
	    for(int i=0;i<pts.size(); i++)
	    	bar.data().add(pts.get(i).value);
	    option.series(bar);
	    return option;
	}
	public GsonOption SentimentPieOption(List<EventDynamic> eds){
		GsonOption option = new GsonOption(), option1 = new GsonOption();
		int num = eds.size();
		EventDynamic ed1 = eds.get(num/4), ed2=eds.get(num/3), ed3=eds.get(num/2), ed4=eds.get(num*2/3), ed5=eds.get(num-1);
		
		option.timeline().data().add(ed1.updateTime.substring(5,16));
		option.timeline().data().add(ed2.updateTime.substring(5,16));
		option.timeline().data().add(ed3.updateTime.substring(5,16));
		option.timeline().data().add(ed4.updateTime.substring(5,16));
		option.timeline().data().add(ed5.updateTime.substring(5,16));
		option.timeline().autoPlay(true);
		option.timeline().playInterval(1000);
		
		option1.title().text("舆情态势变化趋势").subtext("事件参与用户情感分布变化趋势");
		option1.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");
		//option1.toolbox().show(true);
		//option1.toolbox().feature(Tool.mark, new MagicType(Magic.pie, Magic.funnel), Tool.restore);
		option1.legend().data("积极情感","中立态度","负面情绪");

		Pie p = new Pie();
		p.name("态势分布").type(SeriesType.pie).center("50%","45%").radius("50%");
		p.data().add(ed1.postiveNum);
		p.data().add(ed1.neutralNum);
		p.data().add(ed1.negativeNum);
		option1.series(p);
		
		option.options().add(option1);
		option.options().add(GetOptionSentiment(ed2));
		option.options().add(GetOptionSentiment(ed3));
		option.options().add(GetOptionSentiment(ed4));
		option.options().add(GetOptionSentiment(ed5));

	    return option;
	}
	public GsonOption GetOptionSentiment(EventDynamic ed){
		GsonOption option = new GsonOption();
		option.series(DynamicPieData(ed));
		return option;
	}
	public Pie DynamicPieData(EventDynamic ed){
		Pie p = new Pie();
		p.name("态势分布").type(SeriesType.pie);
		p.data().add(MapSeriesData("正面",ed.postiveNum));
		p.data().add(MapSeriesData("中立",ed.neutralNum));
		p.data().add(MapSeriesData("负面",ed.negativeNum));
		return p;
	}
	
	public Map MapSeriesData(String name, double value){
		Map map = new HashMap();
		map.put("name", name);
		map.put("value", value);
		return map;
	}
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
