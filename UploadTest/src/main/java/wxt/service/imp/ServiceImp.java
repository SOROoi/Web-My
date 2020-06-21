package wxt.service.imp;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import wxt.service.ServiceDao;

@Service("uploadService")
public class ServiceImp implements ServiceDao {

	// 通过request 创建保存目录，upload 进行上传
	@Override
	public void upload(HttpServletRequest request, MultipartFile upload) {
		String path = request.getSession().getServletContext().getRealPath("/upload"); // 获得绝对路径
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}

		String name = upload.getOriginalFilename();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		file = new File(path, name + uuid);
		try {
			upload.transferTo(file);	//上传文件
		} catch (IllegalStateException | IOException e) {
			System.out.println("文件上传失败");
			e.printStackTrace();	
		}
	}

}
