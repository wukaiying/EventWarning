package com.eventwarning.servlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eventwarning.bean.Event;
import com.eventwarning.dbImpl.DBOperation;
import com.github.abel533.echarts.RoamController;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.code.Y;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.MarkPoint;
import com.github.abel533.echarts.style.ItemStyle;
public class HotMapServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public HotMapServlet() {
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
		//List<Map> locationdata = DBOperation.GetLocationEvent();		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;character=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		String keyword = "";
		if(request.getParameter("skey")!=null)
			keyword = request.getParameter("skey");
		GsonOption hotmapoption = HotMapOption(LocationChina, keyword);
		
		response.getWriter().write(hotmapoption.toString());
		//request.setAttribute("hotmapoption", hotmapoption.toString());
		
		//ServletContext servletContext = getServletContext();
		//RequestDispatcher dispather = servletContext.getRequestDispatcher("/hotmap.jsp");
		//dispather.forward(request, response);
		
	}
	public static String LocationChina[]={"北京","天津","河北","山西","内蒙古","辽宁","吉林","黑龙江",
			"上海","江苏","浙江","安徽","福建","江西","山东","河南","湖北","湖南","广东","广西","海南省",
			"重庆","四川","贵州","云南","西藏","陕西","甘肃","青海","宁夏","新疆","香港","澳门","台湾",//};
	//public static String CitiesChina[] = {
		"北京市","天津市","石家庄市","唐山市","秦皇岛市","邯郸市","邢台市","保定市","张家口市",
		"承德市","沧州市","廊坊市","衡水市","太原市","大同市","阳泉市","长治市","晋城市","朔州市","晋中市","运城市",
		"忻州市","临汾市","吕梁市","呼和浩特市","包头市","乌海市","赤峰市","通辽市","鄂尔多斯市","呼伦贝尔市",
		"巴彦淖尔市","乌兰察布市","兴安盟","锡林郭勒盟","阿拉善盟","沈阳市","大连市","鞍山市","抚顺市","本溪市",
		"丹东市","锦州市","营口市","阜新市","辽阳市","盘锦市","铁岭市","朝阳市","葫芦岛市","长春市",
		"吉林市","四平市","辽源市","通化市","白山市","松原市","白城市","延边朝鲜族自治州","哈尔滨市",
		"齐齐哈尔市","鸡西市","鹤岗市","双鸭山市","大庆市","伊春市","佳木斯市","七台河市","牡丹江市","黑河市",
		"绥化市","大兴安岭地区","上海市","南京市","无锡市","徐州市","常州市","苏州市","南通市","连云港市","淮安市",
		"盐城市","扬州市","镇江市","泰州市","宿迁市","杭州市","宁波市","温州市","嘉兴市","湖州市","绍兴市",
		"金华市","衢州市","舟山市","台州市","丽水市","合肥市","芜湖市","蚌埠市","淮南市","马鞍山市",
		"淮北市","铜陵市","安庆市","黄山市","滁州市","阜阳市","宿州市","巢湖市","六安市","亳州市",
		"池州市","宣城市","福州市","厦门市","莆田市","三明市","泉州市","漳州市","南平市","龙岩市",
		"宁德市","南昌市","景德镇市","萍乡市","九江市","新余市","鹰潭市","赣州市","吉安市","宜春市",
		"抚州市","上饶市","济南市","青岛市","淄博市","枣庄市","东营市","烟台市","潍坊市","济宁市",
		"泰安市","威海市","日照市","莱芜市","临沂市","德州市","聊城市","滨州市","荷泽市","郑州市",
		"开封市","洛阳市","平顶山市","安阳市","鹤壁市","新乡市","焦作市","濮阳市","许昌市","漯河市",
		"三门峡市","南阳市","商丘市","信阳市","周口市","驻马店市","武汉市","黄石市","十堰市","宜昌市",
		"襄樊市","鄂州市","荆门市","孝感市","荆州市","黄冈市","咸宁市","随州市","恩施土家族苗族自治州",
		"神农架","长沙市","株洲市","湘潭市","衡阳市","邵阳市","岳阳市","常德市","张家界市","益阳市",
		"郴州市","永州市","怀化市","娄底市","湘西土家族苗族自治州","广州市","韶关市","深圳市","珠海市",
		"汕头市","佛山市","江门市","湛江市","茂名市","肇庆市","惠州市","梅州市","汕尾市","河源市",
		"阳江市","清远市","东莞市","中山市","潮州市","揭阳市","云浮市","南宁市","柳州市","桂林市",
		"梧州市","北海市","防城港市","钦州市","贵港市","玉林市","百色市","贺州市","河池市","来宾市",
		"崇左市","海口市","三亚市","重庆市","成都市","自贡市","攀枝花市","泸州市","德阳市","绵阳市",
		"广元市","遂宁市","内江市","乐山市","南充市","眉山市","宜宾市","广安市","达州市","雅安市",
		"巴中市","资阳市","阿坝藏族羌族自治州","甘孜藏族自治州","凉山彝族自治州","贵阳市","六盘水市","遵义市"
		,"安顺市","铜仁地区","黔西南布依族苗族自治州","毕节地区","黔东南苗族侗族自治州","黔南布依族苗族自治州",
		"昆明市","曲靖市","玉溪市","保山市","昭通市","丽江市","思茅市","临沧市","楚雄彝族自治州",
		"红河哈尼族彝族自治州","文山壮族苗族自治州","西双版纳傣族自治州","大理白族自治州","德宏傣族景颇族自治州",
		"怒江傈僳族自治州","迪庆藏族自治州","拉萨市","昌都地区","山南地区","日喀则地区","那曲地区","阿里地区",
		"林芝地区","西安市","铜川市","宝鸡市","咸阳市","渭南市","延安市","汉中市","榆林市","安康市","商洛市",
		"兰州市","嘉峪关市","金昌市","白银市","天水市","武威市","张掖市","平凉市","酒泉市","庆阳市","定西市",
		"陇南市","临夏回族自治州","甘南藏族自治州","西宁市","海东地区","海北藏族自治州","黄南藏族自治州",
		"海南藏族自治州","果洛藏族自治州","玉树藏族自治州","海西蒙古族藏族自治州","银川市","石嘴山市","吴忠市",
		"固原市","中卫市","乌鲁木齐市","克拉玛依市","吐鲁番地区","哈密地区","昌吉回族自治州","博尔塔拉蒙古自治州",
		"巴音郭楞蒙古自治州","阿克苏地区","克孜勒苏柯尔克孜自治州","喀什地区","和田地区","伊犁哈萨克自治州","塔城地区",
		"阿勒泰地区","石河子市","阿拉尔市","图木舒克市","五家渠市","香港特别行政区","澳门特别行政区"
	};
	public GsonOption HotMapOption(String provinces[], String keyword){
		GsonOption option = new GsonOption();
		List<Integer> counts = new ArrayList();
		int maxCount=0, minCount=Integer.MAX_VALUE;
		for(int i=0; i<provinces.length; i++){
			int count = DBOperation.GetProvinceCount(provinces[i], keyword);
			if(count>maxCount)
				maxCount = count;
			if(count<minCount)
				minCount = count;
			counts.add(count);
		}
		
		option.title().text("热点事件报道数量统计展示").subtext("全国地区").x(X.center);
		option.tooltip().trigger(Trigger.item);
		option.legend().orient(Orient.vertical).x(X.left).data().add("事件数");
		
		option.dataRange().min(minCount);
		option.dataRange().max(maxCount);
		option.dataRange().x(X.left).y(Y.bottom).text("高","低").calculable(true);
		
		RoamController rcontroller = new RoamController();
		rcontroller.show(true).x(X.right).mapTypeControl("china",true);
		
		com.github.abel533.echarts.series.Map series 
			= new com.github.abel533.echarts.series.Map();
		series.name("事件数").type(SeriesType.map).mapType("china").roam(false);
		series.selectedMode("single");
		ItemStyle istyle = new ItemStyle();
		istyle.normal().label().show(true);
		istyle.emphasis().label().show(true);
		series.itemStyle(istyle);
		for(int i=0; i<provinces.length; i++)
			series.data().add(this.MapSeriesData(provinces[i],counts.get(i)));
		
//		com.github.abel533.echarts.series.Map top5 
//				= new com.github.abel533.echarts.series.Map();
//		top5.name("Top5").type(SeriesType.map).mapType("china");
		MarkPoint mp = new MarkPoint();
		mp.symbol("emptyCircle");
		mp.effect().show(true).shadowBlur(0);
		ItemStyle tstyle = new ItemStyle();
		tstyle.normal().label().show(false);
		mp.itemStyle(tstyle);
//		for(int i=high; i<5; i++){
//			Event e = (Event)locationdata.get(i).get("event");
//			String location = e.location.split(" ")[0];
//			int count = (Integer)locationdata.get(i).get("count");
//			mp.data().add(MapSeriesData(location, count));
//		}
//		top5.markPoint(mp);
//		series.markPoint(mp);
		
		option.series(series);
		return option;
	}
	
	public Map MapSeriesData(String name, int value){
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