package wxt.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * 校验请求参数中是否有access-token，有就放行
 * 
 * @author asus pc
 *
 */

@Component
public class CheckFileter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		// 1.获得request对象
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		// 2.获得参数access-token的value
		String token = request.getParameter("access-token");
		if (StringUtils.isBlank(token)) {
			// 若access-token不存在、为空，则不执行
			requestContext.setSendZuulResponse(false);
			// 返回状态码
			requestContext.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
		}
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		// 在处理请求头之前执行过滤器
		return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
	}

}
