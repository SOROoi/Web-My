package p2_xml编程;

/* XML编程(CRUD)
 * 		CRUD--creat read update deletes --增查改删
 * 
 * XML约束:
 * 		DTD约束
 * 		Schema约束： Schema定义约束的能力非常强，可对XML实例文档作出细致的语义限制。
 * 					Schema 文件自身就是一个XML文件，但它的扩展名通常为.xsd。
 * 
 * XML解析方式分为两种：Dom和Sax
 *		Dom：(Document Object Model, 即文档对象模型) 是 W3C 组织推荐的处理 XML 的一种方式。
 * 		Sax： (Simple API for XML) 不是官方标准，但它是 XML 社区事实上的标准，几乎所有的 XML 解析器都支持它。
 *
 * XML解析器
 *		Crimson、Xerces 、Aelfred2
 *
 * XML解析开发包
 *		Jaxp(标准)、Jdom、dom4j(Xpath)(灵活，需导包，使用方法见ppt)
 * 		
 *(面试题)Dom 和 Sax 解析方式的区别？
 * 		1.dom 解析优点是对xml文档 crud 比较方便，缺点是如果文件比较大，对内存消耗就特别大，极容易导致内存溢出(加载整个文件结构)
 * 		2.sax 解析优点是占用内存少，解析速度快，缺点是只适合做文档的读取，不适合文档的 crud (一行一行的读)
 * 
 * 调整JVM内存大小(虚拟机默认内存为64M)
 * 		Eclipse中 右键Run As ——> Run Configurations ——> (x)=Arguments ——> VM arguments框	
 * 															 	      写入：-Xmx80m (设置虚拟机内存为80MB)
 * 																		-Xmx81920k (设置虚拟机内存为81920KB)
 * 																		-Xmx83886080 (设置虚拟机内存为83886080字节)
 */

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/*	一：JAXP开发包解析XML
 *			1.JAXP 开发包是J2SE的一部分，它由javax.xml、org.w3c.dom 、org.xml.sax 包及其子包组成
 *		
 *			2.在 javax.xml.parsers 包中，定义了几个工厂类，程序员调用这些工厂类，
 *			   可以得到对xml文档进行解析的 DOM 或 SAX 的解析器对象。
 *
 *	二：获取JAXP中的DOM解析器 解析XML
 *			//1.得到创建 DOM 解析器的工厂
 *			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
 *
 *			//2.工厂对象的方法得到 DOM 解析器对象
		 	DocumentBuilder builder = factory.newDocumentBuilder();
		 	
		 	//3.DOM 解析器对象的方法解析 XML文档，得到XML文档的 Document 对象
		 	Document document = builder.parse("src/book.xml");
	
	三：DOM	编程	操作XML
			1.Document类及其体系
			
			----NodeList
					//方法： getLength() 				--int 		返回列表中的节点数。
							item(int index) 		--Node 		返回集合中的第 index 个节点。
				父类			   	
			----Node(节点)			
					//方法： appendChild(Node newChild)					--Node	将节点 newChild 添加到此节点的子节点列表的末尾。
				 			insertBefore(Node newChild, Node refChild) --Node	在现有子节点 refChild 之前插入节点 newChild。
				 			getTextContent()  						--String 	返回此节点文本内容 及其后代的文本内容。
				 			setTextContent(String textContent) 		--void 		设置此节点的文本内容
				 			getParentNode()  						--Node 		返回此节点的父节点。
				 			removeChild(Node oldChild)  			--Node 		移除 oldChild 所指示的子节点，并将其返回。
				 			
				 			getChildNodes() 		 				--NodeList	包含此节点的所有子节点的 NodeList。
				 			getAttributes()  						--NamedNodeMap 包含此节点的属性的 NamedNodeMap（如果它是 Element）；否则为 null。
				 			getNodeName()							--String 	返回此节点的名称，取决于其类型。（如属性节点 name="aa"）
				 			getNodeValue() 							--String 	返回此节点的值，取决于其类型。
				 			
				 				子类
						 ----Document (文档)			
						 	//方法： getElementsByTagName(String tagname) --NodeList 按文档顺序返回此标签名的所有 Element 的 NodeList。
						 			getElementById(String elementId)  --Element 	返回具有带给定值的 ID 属性的 Element。
						 	 		createElement(String tagName)	--Element 	创建指定类型的元素。
						 			createAttribute(String name)	--Attr 		创建给定名称的 Attr。
						 
						 ----Element (元素/标签)
						 	//方法： getElementsByTagName(String name)	--NodeList  以文档顺序返回此标签名的所有后代 Elements 的 NodeList
						 	 		getTagName()  						--String 	返回元素的名称。
						 	 		getAttribute(String name)  			--String 	通过名称获得属性值字符串。
									setAttribute(String name, String value)	--void  添加一个新属性。
			
			2.//把更新后的内容写回文档
				 Transformer transformer=TransformerFactory.newInstance().newTransformer();
				 	DOMSource source=new DOMSource(document);
				 		FileOutputStream outstream =new FileOutputStream(new File("src/outbook.xml"));
				 	StreamResult reslut=new StreamResult(outstream);
				 transformer.transform(source, reslut);
				 outstream.close();		
 * 
 */
public class Jaxp解析XML文档 {

	@Test
	public void find() throws Exception {				//输出根目录"书架"标签的文本内容
		//获得DOM解析器
		DocumentBuilderFactory documentBuilderFactory =DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		//通过解析器获得XML的Document对象
		Document document = documentBuilder.parse("src/Book.xml");
		//输出第一个"书架"标签的文本内容
		System.out.println(document.getElementsByTagName("书架").item(0).getTextContent());
	}
	
	@Test
	public void add2() throws Exception{				//向文档中指定位置上添加节点  <售价>59.0元</售价>	
		 DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		 DocumentBuilder builder=factory.newDocumentBuilder();
		 Document document=builder.parse("src/Book.xml");
		 //创建节点
		 Element price=document.createElement("售价");
		 price.setTextContent("59.0元");
		 //得到参考节点
		 Element refNode=(Element)document.getElementsByTagName("售价").item(0);
		 //得到挂崽的节点
		 Element book=(Element)document.getElementsByTagName("书").item(0);
		 // 往book节点指定位置前插值
		 book.insertBefore(price, refNode);
		 
		 //把更新后的内容写回文档
		 Transformer transformer=TransformerFactory.newInstance().newTransformer();
		 DOMSource source=new DOMSource(document);
		 FileOutputStream outstream =new FileOutputStream(new File("src/outbook2.xml"));
		 StreamResult reslut=new StreamResult(outstream);
		 transformer.transform(source, reslut);
		 outstream.close();
	}
	
	@Test
	public void addAtt() throws Exception{				//向文档节点 添加属性
		 DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		 DocumentBuilder builder=factory.newDocumentBuilder();
		 Document document=builder.parse("src/Book.xml");
		 
		 //得到文档节点
		 Element refNode=(Element)document.getElementsByTagName("售价").item(0);
		 refNode.setAttribute("addAtrr","new cha ru value");
		 
		
		
		 Transformer transformer=TransformerFactory.newInstance().newTransformer();
		 DOMSource source=new DOMSource(document);
		 FileOutputStream outstream =new FileOutputStream(new File("src/outbook3.xml"));
		 StreamResult reslut=new StreamResult(outstream);
		 transformer.transform(source, reslut);
		 outstream.close();		
	}
		
	@Test
	public void delete() throws Exception{				//删除节点
		 DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		 DocumentBuilder builder=factory.newDocumentBuilder();
		 Document document=builder.parse("src/book.xml");
		 
		 //得到要删除的节点
		 Element refNode=(Element)document.getElementsByTagName("售价").item(0);
		 refNode.getParentNode().removeChild(refNode);
		
		
		 Transformer transformer=TransformerFactory.newInstance().newTransformer();
		 DOMSource source=new DOMSource(document);
		 FileOutputStream outstream =new FileOutputStream(new File("src/outbook3.xml"));
		 StreamResult reslut=new StreamResult(outstream);
		 transformer.transform(source, reslut);
		 outstream.close();		
	}

	@Test
	public void update() throws Exception{				//更新 售价
		 DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		 DocumentBuilder builder=factory.newDocumentBuilder();
		 Document document=builder.parse("src/book.xml");
		 
		 //得到要更新的节点
		 Element refNode=(Element)document.getElementsByTagName("售价").item(0);
		 refNode.setTextContent("1000");
		 
		 Transformer transformer=TransformerFactory.newInstance().newTransformer();
		 DOMSource source=new DOMSource(document);
		 FileOutputStream outstream =new FileOutputStream(new File("src/outbook3.xml"));
		 StreamResult reslut=new StreamResult(outstream);
		 transformer.transform(source, reslut);
		 outstream.close();	
		 
	}
}
