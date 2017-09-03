package cn.my.e3mall.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Repository
public class GlobalExceptionReslover implements HandlerExceptionResolver {

	private Logger logger = LoggerFactory.getLogger(GlobalExceptionReslover.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ex.printStackTrace();
		// 当前请求来源
		String referer = request.getHeader("Referer");

		logger.debug("出现异常");
		logger.info("info");
		logger.error(ex.getMessage());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "网络繁忙请重试");
		modelAndView.addObject("referer", referer);

		modelAndView.setViewName("error/exception");
		return modelAndView;
	}
}