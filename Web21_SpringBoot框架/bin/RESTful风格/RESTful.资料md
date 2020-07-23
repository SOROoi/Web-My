# **5.** **通用Mapper**

## **5.1.** **假设：使用Mybatis只需要定义Mapper接口，无需编写Mapper****.xml文件**

如果要实现无需编写Mapper.xml文件，我们必须要实现动态拼接SQL。

 

如何实现动态拼接SQL语句？

 

思路：编写Mybatis的插件，在执行的过程中动态生成SQL语句。

 

## **5.2.** **简介**

![img](file:///C:\Temp\ksohtml\wps2EAE.tmp.jpg) 

 

<http://www.oschina.net/p/mybatis-mapper>

 

作者：

![img](file:///C:\Temp\ksohtml\wps2EAF.tmp.jpg) 

 

## **5.3.** **使用通用Mapper**

### **5.3.1.** **文档**

![img](file:///C:\Temp\ksohtml\wps2EB0.tmp.jpg) 

### **5.3.2.** **如何集成通用Mapper**

![img](file:///C:\Temp\ksohtml\wps2EB1.tmp.jpg) 

### **5.3.3.** **配置通用Mapper拦截器**

![img](file:///C:\Temp\ksohtml\wps2EC1.tmp.jpg) 

 

注意：配置插件的顺序，必须是分页助手在前面，通用Mapper插件在后面。

### **5.3.4.** **定义Mapper接口**

![img](file:///C:\Temp\ksohtml\wps2EC2.tmp.jpg) 

### **5.3.5.** **实体添加JPA注解**

![img](file:///C:\Temp\ksohtml\wps2EC3.tmp.jpg) 

## **5.4.** **使用通用Mapper实现用户数据查询**

### **5.4.1.** **NewUser****Mapper**

![img](file:///C:\Temp\ksohtml\wps2EC4.tmp.jpg) 

### **5.4.2.** **NewUserServie**

![img](file:///C:\Temp\ksohtml\wps2EC5.tmp.jpg) 

### **5.4.3.** **UserController**

![img](file:///C:\Temp\ksohtml\wps2EC6.tmp.jpg) 

### **5.4.4.** **执行的SQL**

![img](file:///C:\Temp\ksohtml\wps2EC7.tmp.jpg) 

 

## **5.5.** **测试**

### **5.5.1.** **创建单元测试用例**

![img](file:///C:\Temp\ksohtml\wps2EC8.tmp.jpg) 

 

![img](file:///C:\Temp\ksohtml\wps2EC9.tmp.jpg) 

 

![img](file:///C:\Temp\ksohtml\wps2EDA.tmp.jpg) 

### **5.5.2.** **初始化NewUserMapper**

![img](file:///C:\Temp\ksohtml\wps2EDB.tmp.jpg) 

### **5.5.3.** **通用Mapper动态拼接SQL**

![img](file:///C:\Temp\ksohtml\wps2EDC.tmp.jpg) 

 

### **5.5.4.** **完整测试用例**

**public** **class** NewUserMapperTest {

 

​    **private** NewUserMapper newUserMapper;

 

​    @Before

​    **public** **void** setUp() **throws** Exception {

​        ApplicationContext applicationContext = **new** ClassPathXmlApplicationContext(

​                "classpath:spring/applicationContext*.xml");

​        **this**.newUserMapper = applicationContext.getBean(NewUserMapper.**class**);

​    }

 

​    @Test

​    **public** **void** testSelectOne() {

​        User record = **new** User();

​        record.setuserName("zhangsan");

​        User user = **this**.newUserMapper.selectOne(record);

​        System.**out**.println(user);

​    }

 

​    @Test

​    **public** **void** testSelect() {

​        User record = **new** User();

​        // 多个条件之间是AND关系

​        // record.setuserName("zhangsan");

​        // record.setPassword("123456");

​        record.setSex(1);

​        List<User> list = **this**.newUserMapper.select(record);

​        **for** (User user : list) {

​            System.**out**.println(user);

​        }

​    }

 

​    @Test

​    **public** **void** testSelectCount() {

​        System.**out**.println(**this**.newUserMapper.selectCount(**null**));

​    }

 

​    @Test

​    **public** **void** testSelectByPrimaryKey() {

​        User user = **this**.newUserMapper.selectByPrimaryKey(1L);

​        System.**out**.println(user);

​    }

 

​    @Test

​    **public** **void** testInsert() {

​        User record = **new** User();

​        // record.setAge(20);

​        record.setBirthday(**new** Date());

​        record.setCreated(**new** Date());

​        // record.setUpdated(new Date());

​        record.setName("name_4");

​        record.setPassword("20");

​        // record.setSex(1);

​        record.setuserName("user_name_4");

​        // SQL:INSERT INTO tb_user (CREATED,BIRTHDAY,ID,SEX,NAME,AGE,UPDATED,USER_NAME,PASSWORD)

​        // VALUES ( ?,?,?,?,?,?,?,?,? )

​        // 将对象中的所有属性都当做是SQL语句中字段执行

​        **this**.newUserMapper.insert(record);

 

​        System.**out**.println(record.getId());

​    }

 

​    @Test

​    **public** **void** testInsertSelective() {

​        User record = **new** User();

​        // record.setAge(20);

​        record.setBirthday(**new** Date());

​        record.setCreated(**new** Date());

​        // record.setUpdated(new Date());

​        record.setName("name_3");

​        record.setPassword("20");

​        // record.setSex(1);

​        record.setuserName("user_name_3");

 

​        // INSERT INTO tb_user ( NAME,BIRTHDAY,ID,CREATED,USER_NAME,PASSWORD ) VALUES ( ?,?,?,?,?,?

​        // )

​        // Selective：将不为null的属性作为SQL语句中字段

​        **this**.newUserMapper.insertSelective(record);

​        System.**out**.println(record.getId());

​    }

 

​    @Test

​    **public** **void** testDelete() {

​        *fail*("Not yet implemented");

​    }

 

​    @Test

​    **public** **void** testDeleteByPrimaryKey() {

​        *fail*("Not yet implemented");

​    }

 

​    @Test

​    **public** **void** testUpdateByPrimaryKey() {

​        // 更新所有的字段，更新条件是:主键

​        *fail*("Not yet implemented");

​    }

 

​    @Test

​    **public** **void** testUpdateByPrimaryKeySelective() {

​        // 更新不为null的字段，更新条件是:主键

​        *fail*("Not yet implemented");

​    }

 

​    @Test

​    **public** **void** testSelectCountByExample() {

​        *fail*("Not yet implemented");

​    }

 

​    @Test

​    **public** **void** testDeleteByExample() {

​        *fail*("Not yet implemented");

​    }

 

​    @Test

​    **public** **void** testSelectByExample() {

 

​        Example example = **new** Example(User.**class**);

​        List<Object> values = **new** ArrayList<Object>();

​        values.add(1L);

​        values.add(2L);

​        values.add(3L);

​        //设置查询条件

​        example.createCriteria().andIn("id", values);

​        

​        //设置排序条件

​        example.setOrderByClause("created DESC");

​        List<User> list = **this**.newUserMapper.selectByExample(example);

​        **for** (User user : list) {

​            System.**out**.println(user);

​        }

​    }

 

​    @Test

​    **public** **void** testUpdateByExampleSelective() {

​        *fail*("Not yet implemented");

​    }

 

​    @Test

​    **public** **void** testUpdateByExample() {

​        *fail*("Not yet implemented");

​    }

 

}

 

## **5.6.** **集成到项目中**

![img](file:///C:\Temp\ksohtml\wps2EDD.tmp.jpg) 

 

# **6.** **RESTful Web Service**

## **6.1.** **大纲**

![img](file:///C:\Temp\ksohtml\wps2EDE.tmp.jpg) 

## **6.2.** **REST**ful是什么？

![img](file:///C:\Temp\ksohtml\wps2EEF.tmp.jpg) 

论文：Roy Thomas Fielding博士论文REST(中文版).pdf

![img](file:///C:\Temp\ksohtml\wps2EF0.tmp.jpg) 

## **6.3.** **REST**ful到底是什么？

![img](file:///C:\Temp\ksohtml\wps2EF1.tmp.jpg) 

## **6.4.** **RESTful**是什么？

![img](file:///C:\Temp\ksohtml\wps2EF2.tmp.jpg) 

 

Web service：

JAX-WS

JAX-RS

 

![img](file:///C:\Temp\ksohtml\wps2EF3.tmp.jpg) 

 

## **6.5.** **REST** **架构的主要原则**

![img](file:///C:\Temp\ksohtml\wps2EF4.tmp.jpg) 

## **6.6.** **URI和URL**

![img](file:///C:\Temp\ksohtml\wps2EF5.tmp.jpg) 

## **6.7.** **无状态性**

![img](file:///C:\Temp\ksohtml\wps2EF6.tmp.jpg) 

## **6.8.** **资源操作**

![img](file:///C:\Temp\ksohtml\wps2EF7.tmp.jpg) 

 

 

之前的操作：

<http://127.0.0.1/user/query/1> GET  根据用户id查询用户数据

http://127.0.0.1/user/save POST 新增用户

http://127.0.0.1/user/update POST 修改用户信息

http://127.0.0.1/user/delete GET/POST 删除用户信息

 

RESTful用法：

<http://127.0.0.1/user/1> GET  根据用户id查询用户数据

<http://127.0.0.1/user>  POST 新增用户

<http://127.0.0.1/user>  PUT 修改用户信息

<http://127.0.0.1/user>  DELETE 删除用户信息

## **6.9.** **REST**ful接口定义

![img](file:///C:\Temp\ksohtml\wps2F07.tmp.jpg) 

![img](file:///C:\Temp\ksohtml\wps2F08.tmp.jpg) 

## **6.10.** **最佳实践**

### **6.10.1.** **REST**ful接口设计

![img](file:///C:\Temp\ksohtml\wps2F09.tmp.jpg) 

### **6.10.2.** **响应设计**

![img](file:///C:\Temp\ksohtml\wps2F0A.tmp.jpg) 

 

### **6.10.3.** **响应示例**

![img](file:///C:\Temp\ksohtml\wps2F0B.tmp.jpg) 

 

### **6.10.4.** **指定响应的属性字段**

![img](file:///C:\Temp\ksohtml\wps2F0C.tmp.jpg) 

# **7.** **http**响应状态码

![img](file:///C:\Temp\ksohtml\wps2F0D.tmp.jpg) 

# **8.** **SpringMVC**实现RESTful服务

![img](file:///C:\Temp\ksohtml\wps2F0E.tmp.jpg) 

## **8.1.** **查询资源**

![img](file:///C:\Temp\ksohtml\wps2F0F.tmp.jpg) 

## **8.2.** **新增资源**

![img](file:///C:\Temp\ksohtml\wps2F20.tmp.jpg) 

 

测试：

![img](file:///C:\Temp\ksohtml\wps2F21.tmp.jpg) 

 

 

## **8.3.** **更新资源**

![img](file:///C:\Temp\ksohtml\wps2F22.tmp.jpg) 

 

测试：

![img](file:///C:\Temp\ksohtml\wps2F23.tmp.jpg) 

 

默认情况下，PUT请求是无法提交表单数据的，需要在web.xml中添加过滤器解决：

 

​	<!-- 解决PUT请求无法提交表单数据的问题 -->

​	<filter>

​		<filter-name>HttpMethodFilter</filter-name>

​		<filter-class>org.springframework.web.filter.HttpPutFormContentFilter</filter-class>

​	</filter>

​	<filter-mapping>

​		<filter-name>HttpMethodFilter</filter-name>

​		<url-pattern>/*</url-pattern>

​	</filter-mapping>

 

## **8.4.** **删除资源**

![img](file:///C:\Temp\ksohtml\wps2F24.tmp.jpg) 

 

测试：

![img](file:///C:\Temp\ksohtml\wps2F25.tmp.jpg) 

 

 

需要在web.xml中添加过滤器解决DELETE请求无法提交表单数据的问题：

​	<!-- 

​		将POST请求转化为DELETE或者是PUT

​		要用_method指定真正的请求参数

​	 -->

​	<filter>

​		<filter-name>HiddenHttpMethodFilter</filter-name>

​		<filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>

​	</filter>

​	<filter-mapping>

​		<filter-name>HiddenHttpMethodFilter</filter-name>

​		<url-pattern>/*</url-pattern>

​	</filter-mapping>

 

# **9.** **小结**

![img](file:///C:\Temp\ksohtml\wps2F26.tmp.jpg) 

 

 

 

 

 