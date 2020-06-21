package wxt.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface ServiceDao {

	public void upload(HttpServletRequest request,MultipartFile upload);
}
