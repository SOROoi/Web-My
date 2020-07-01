package ajax;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*							AJAX	JQuery写法	
	客户端
	 	1.AJAX----Asynchronous Javascript And XML（异步JavaScript和XML）
	 		用于前端，向服务器发送数据、获取数据。
	 		
	 	2.AJAX代码写在script中
	 	
	 	3.发送请求(get、post),在其中 处理servlet返回的 json数据
	 		
	 		例：
	 			$.post("CityServlet02",{pid:pid} ,function(data){
					
				},"json" );
					
					
				$.get(url, [data], [callback], [type])
					url：待载入页面的URL地址.
 					data：待发送 Key/value 参数。
 					callback：载入成功时回调函数。
 					type：返回内容的格式，xml, html, script, json, text, _default。
 
 
 	服务器端
 		1.json数据格式：[
					    {
					        "cname": "深圳",
					        "id": 1,
					        "pid": 1
					    },
					    {
					        "cname": "东莞",
					        "id": 2,
					        "pid": 1
					    }
					   ]
			
			
		2.将 Bean 转为 json数据：
			0.导入json的依赖包
				json-lib-2.4-jdk15.jar
				
			1.前提：
				必须为JavaBean才可以被转为json数据，即含有成员变量，get方法,set方法
			
			2.先转为JSONObject/JSONArray对象，再调用toString()方法。
								
				例：	JSONObject jsonObject = JSONObject.fromObject(new Bean());
					String json = jsonObject.toString();			
				
				例：	ArrayList<Object> list = new ArrayList<Object>();
					list.add(new Bean());
					list.add(new Bean());
					JSONArray jsonArray = JSONArray.fromObject(list);
					String json = jsonArray.toString();
												
												
		3.将 json数据 转为 Bean:
			1.springmvc框架提供，需满足：	1. 导入jackson的jar包。
															jackson-databind
															jackson-core
															jackson-annotations
										2. json数据key名 与 bean中属性名 相同。
												
			2.先转为 JSONObject对象，再调用 toBean(JSONObject o,Class c)方法转为 Bean：
					
				例:	String str = "{'name':'张三','age':'15'}";
					JSONObject jObj = JSONObject.fromObject(str);
					Bean b = (Bean) JSONObject.toBean(jObj, Bean.class);	//指定转换的Class对象,并强转
 
 				例：	String str = "[{'name':'李四','age':'16'},{'name':'王五','age':'17'}]";
					JSONArray jArray = JSONArray.fromObject(str); 	//将json 字符串转为 JSONArray对象
					System.out.println(jArray);
					Object[] oArray = jArray.toArray(); 	//JSONArray 对象转为 Object数组
					for (Object o : oArray) {
						JSONObject jObj = JSONObject.fromObject(o); 	//将每个Object 转为 JSONObject对象
						Bean b = (Bean) JSONObject.toBean(jObj, Bean.class); //将每个JSONObject 对象转为 Bean对象
						System.out.println(b.getName() + "=" + b.getAge());
					}
 
 */

/*							AJAX	JS写法	
 * 	
	 1.发送 get请求，并发送数据、获取数据：
			
			//1. ajax前置方法，创建 ajax对象
			function  ajaxFunction(){
			   var xmlHttp;
			   try{ // Firefox, Opera 8.0+, Safari
			        xmlHttp=new XMLHttpRequest();
			    }
			    catch (e){
				   try{// Internet Explorer
				         xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
				      }
				    catch (e){
				      try{
				         xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
				      }
				      catch (e){}
				      }
			    }
			
				return xmlHttp;
			 }
			
			//2. ajax方法	(html元素的事件绑定该send()方法)
			function send(){		
				var request = ajaxFunction();											//1. 创建xmlhttprequest 对象
				request.open("GET" ,"/day16/DemoServlet01?name=aa&age=18" ,true );		//2. 发送请求
				
								参数一： 请求类型  GET or  POST
								参数二： 请求的路径
								参数三： 是否异步， true  or false
																				
				request.onreadystatechange = function(){								//3. 获取响应数据 注册监听的意思。
				  																			一会准备的状态发生了改变，那么
				  																			就执行 = 号右边的方法
				  																						
					if(request.readyState == 4 && request.status == 200){	//前半段表示 已经能够正常处理。
					  														    再判断状态码是否是200	
						alert(request.responseText);	//弹出响应的信息
					}
				}
				request.send();
			}
			

	2.发送 post请求，并发送数据、获取数据：(html元素的事件绑定该send()方法)
	
			//1. ajax前置方法，创建 ajax对象
			function  ajaxFunction(){
			   var xmlHttp;
			   try{ // Firefox, Opera 8.0+, Safari
			        xmlHttp=new XMLHttpRequest();
			    }
			    catch (e){
				   try{// Internet Explorer
				         xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
				      }
				    catch (e){
				      try{
				         xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
				      }
				      catch (e){}
				      }
			    }
			
				return xmlHttp;
			 }
			 
			 //2. ajax方法
			function post() {
				var request = ajaxFunction();								//1.创建对象
				request.open( "POST", "/day16/DemoServlet01", true );		//2.发送请求
				
				 
				request.onreadystatechange=function(){						//3.获取服务器数据， 加一个状态的监听。
					if(request.readyState==4 && request.status == 200){
						
						alert("post："+request.responseText);
					}
				}
																			//4.1 发送数据-请求头
				request.setRequestHeader("Content-type","application/x-www-form-urlencoded");	
																			
								   post方式带数据，那么这里要添加头， 
								     说明提交的数据类型是一个经过url编码的form表单数据
				 
				request.send("name=aobama&age=19");							//4.2 发送数据-在send方法里面写表单数据。
			}
 */

/*							后端 servlet代码
 
 	1. servlet获取 request中数据，response发送数据给前端：
 	
			protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				try {
					
					request.setCharacterEncoding("UTF-8");
					
					//1. 检测是否存在
					String name = request.getParameter("name");
					System.out.println("name="+name);
					
					UserDao dao = new UserDaomImpl();
					boolean isExist = dao.checkUserName(name);
					
					//2. 通知页面，数据库中是否存在 该用户名。
					if(isExist){
						response.getWriter().println(1);  //存在用户名
					}else{
						response.getWriter().println(2); //不存在该用户名
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
 */

public class AJAX_前端技术 {

	public static void main(String[] args) {
		// method1();
		method2();
	}

	// 将json 字符串转为一个Bean 对象
	private static void method1() {
		String str = "{'name':'张三','age':'15'}";
		JSONObject jObj = JSONObject.fromObject(str); // 将json 字符串转为 JSONObject对象
		System.out.println(jObj);
		Bean b = (Bean) JSONObject.toBean(jObj, Bean.class); // 将JSONObject对象转为 Bean对象
		System.out.println(b.getAge());
	}

	// 将json 字符串转为N个Bean 对象
	private static void method2() {
		String str = "[{'name':'李四','age':'16'},{'name':'王五','age':'17'}]";
		JSONArray jArray = JSONArray.fromObject(str); // 将json 字符串转为 JSONArray对象
		System.out.println(jArray);
		Object[] oArray = jArray.toArray(); // JSONArray 对象转为 Object数组
		for (Object o : oArray) {
			JSONObject jObj = JSONObject.fromObject(o); // 将每个Object 转为 JSONObject对象
			Bean b = (Bean) JSONObject.toBean(jObj, Bean.class); // 将每个JSONObject 对象转为 Bean对象
			System.out.println(b.getName() + "=" + b.getAge());
		}
	}
}
