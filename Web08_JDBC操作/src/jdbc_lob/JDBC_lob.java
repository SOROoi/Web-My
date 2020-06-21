package jdbc_lob;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



/**						JDBC存取大数据lob
 * 
  	1.MySQL中创建数据库lob,创建表textclob(id--varchar(10),	textdata--text),
  							 表bytelob(id--varchar(10),	blobdata--blob)
  	
  		create database lob character set utf8 collate utf8_general_ci;
  		use lob;
  		create table textclob(
  			id varchar(10) primary key,
  			textdata text
  		);
  		create table bytelob(
  			id varchar(10) primary key,
  			blobdata blob
  		);
  		

 */
public class JDBC_lob {

	// 存text数据
	public void insertText() throws SQLException, FileNotFoundException {

		Connection con = null;
		PreparedStatement pState = null;
		ResultSet re = null;

		try { // 使用try..finally 确保一定释放Connection资源
			JdbcUtil.setDriver();
			con = JdbcUtil.getConnection();
			String sql = "insert into textclob(id,textdata) values(?,?);";
			pState = con.prepareStatement(sql);
			pState.setString(1, "1");

			File file = new File("src/1.txt");	//默认项目目录下
			Reader reader = null;
			try {		// 使用try..finally 确保一定释放输入流资源
				reader = new FileReader(file);
				pState.setCharacterStream(2, reader, file.length());
				int i = pState.executeUpdate();
				if (i > 0) {
					System.out.println("插入数据成功");
					return;
				}
				System.out.println("插入数据失败");
			} finally {
				if(reader!=null) {
					try {
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} finally {
			JdbcUtil.close(re, pState, con); // 释放资源
		}

	}

	// 取text数据
	public void readText() throws SQLException, IOException {

		Connection con = null;
		PreparedStatement pState = null;
		ResultSet re = null;
		Reader reader = null;
		Writer writer = null;

		try { // 使用try..finally 确保一定释放Connection.PreparedStatement.ResultSet.Reader.Writer资源
			JdbcUtil.setDriver();
			con = JdbcUtil.getConnection();
			String sql = "select id,textdata from textclob where id ='1';"; // 此处id 为varchar类型，sql语句中值写作'1'
			pState = con.prepareStatement(sql);

			re = pState.executeQuery();
			if (re.next()) {
				reader = re.getCharacterStream("textdata");
				File file = new File("copy.txt");	//默认项目目录下
				writer = new FileWriter(file);

				int len = 0;
				char[] c = new char[1024];
				while ((len = reader.read(c)) != -1) {
					writer.write(c, 0, len);
				}
				System.out.println("读取成功，并复制！");
			}

		} finally {
			JdbcUtil.close(re, pState, con); // 释放Connection.PreparedStatement.ResultSet.Reader.Writer资源
			if(reader!=null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(writer!=null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	// 存blob数据
	public void insertBlob() throws SQLException, FileNotFoundException {

		Connection con = null;
		PreparedStatement pState = null;
		ResultSet re = null;

		try { // 使用try..finally 确保一定释放Connection资源
			JdbcUtil.setDriver();
			con = JdbcUtil.getConnection();
			
			String sql = "insert into bytelob(id,blobdata) values(?,?);";				//此处代码不同 String sql
			pState = con.prepareStatement(sql);
			pState.setString(1, "1");

			File file = new File("src/1.jpg");	//默认项目目录下
			InputStream in = null;														//此处代码不同 InputStream
			try {		// 使用try..finally 确保一定释放输入流资源
				in = new FileInputStream(file);
				pState.setBinaryStream(2, in, file.length());							//此处代码不同 setBinaryStream()
				int i = pState.executeUpdate();
				if (i > 0) {
					System.out.println("插入数据成功");
					return;
				}
				System.out.println("插入数据失败");
			} finally {
				if(in!=null) {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} finally {
			JdbcUtil.close(re, pState, con); // 释放资源
		}

	}
	
	// 取blob数据
	public void readBlob() throws SQLException, IOException {

		Connection con = null;
		PreparedStatement pState = null;
		ResultSet re = null;
		InputStream in = null;																				//此处代码不同
		OutputStream out = null;																			//此处代码不同

		try { // 使用try..finally 确保一定释放Connection.PreparedStatement.ResultSet.Reader.Writer资源
			JdbcUtil.setDriver();
			con = JdbcUtil.getConnection();
			
			String sql = "select id,blobdata from bytelob where id ='1';"; // 此处id 为varchar类型，sql语句中值写作'1' //此处代码不同
			pState = con.prepareStatement(sql);

			re = pState.executeQuery();
			if (re.next()) {
				in = re.getBinaryStream("blobdata");														//此处代码不同
				File file = new File("copy.jpg");	//默认项目目录下											//此处代码不同
				out = new FileOutputStream(file);															//此处代码不同

				int len = 0;
				byte[] c = new byte[1024];																	//此处代码不同
				while ((len = in.read(c)) != -1) {
					out.write(c, 0, len);
				}
				System.out.println("读取成功，并复制！");
				return;
			}
			System.out.println("读取失败，无此数据");

		} finally {
			JdbcUtil.close(re, pState, con); // 释放Connection.PreparedStatement.ResultSet.Reader.Writer资源
			if(in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(out!=null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws SQLException, IOException {
//		new JDBC_lob().insertText();
//		new JDBC_lob().readText();
//		new JDBC_lob().insertBlob();
		new JDBC_lob().readBlob();
		
	}
}
