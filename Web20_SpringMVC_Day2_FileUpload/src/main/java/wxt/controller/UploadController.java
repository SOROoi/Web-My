package wxt.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

@Controller
@RequestMapping(path = "/upload")
public class UploadController {

	//传统方式上传
	@RequestMapping(path = "/1")
	public String upload1(HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/uploadFile/");
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		//解析请求
		List<FileItem> list = null;
		try {
			list = upload.parseRequest(request);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("解析请求失败，请检查表单源代码是否有误");
		}

		// 解析失败，List集合为空
		if (list == null) {
			return "fail";
		}

		//解析成功，判断List中文件项，将上传文件写入服务器
		for (FileItem item : list) {
			//判断是否是普通表单项
			if(item.isFormField()) {
				//普通表单项，啥也不干
			}else {
				//上传文件项，写入服务器文件夹
				try {
					item.write(new File(path,item.getName()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					throw new RuntimeException("上传文件项写入服务器失败");
				}
				// 删除临时文件
				item.delete();
			}
		}
		return "success";
		
	}

	//springMVC方式 上传
	@RequestMapping(path = "/2")
	public String upload2(HttpServletRequest request,MultipartFile upload) {
		String path = request.getSession().getServletContext().getRealPath("/uploadFile/");
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
		
		file = new File(path, upload.getOriginalFilename());
		try {
			upload.transferTo(file);		//上传文件
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("写入服务器失败");
		}
		
		return "success";
	}

	//springMVC方式 跨服务器上传
	@RequestMapping(path="/3")
	public String upload3(MultipartFile upload) throws Exception {
		//定义保存文件 的服务器地址
		String path = "http://localhost:8090/uploadFile/";
		
		//跨服务器保存文件
		//1.创建客户端对象
		Client client = Client.create();	//jersey-client包下，获取客户端对象，用于跨服务器连接
		
		//2.客户端和文件服务器进行连接
		WebResource webResource = client.resource(path + upload.getOriginalFilename());	//跨服务器连接，连接到文件服务器 的保存文件路径
		
		//3.保存文件
		webResource.put(upload.getBytes());		//需传入字节数组
		
		return "success";
	}
	
}
