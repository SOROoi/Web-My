package p3_开发需要创建哪些模块;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/*
 * 程序开发时，经常会遇到异常
 * 	unchecked exception(运行时异常)
 * 	checked exception(编译时异常)
 * 
 * 若想要把编译时异常导致的问题报告到程序上一级，又不影响程序，则可将编译时异常变化为运行时异常
 * 	如下：
 * 		try{
 * 		
 * 		}catch(Exception e){
 * 			throw new RuntimeException(e);
 * 		}
 * 
 * 
 */
public class 编译异常处理 {

	public static void main(String[] args) {
		try {
			FileInputStream fis = new FileInputStream("");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
}
