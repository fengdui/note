package com.fengdui.oa.business.test.web;

import com.xh.market.business.test.service.TestService;
import com.xh.market.business.test.service.TestTmpService;
import com.xh.market.framework.orm.MybatisService;
import com.xh.market.framework.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Random;

/**
 * @author Wander.Zeng
 * @data 2015-6-4 下午3:53:26
 * @desc TestController.java
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseController<Serializable> {

	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private TestService testService;
	@Autowired
	private TestTmpService testTmpService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	protected String getUrlMain() {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected MybatisService getService() {
		return null;
	}

	@Override
	protected Serializable prepareEntity() {
		return null;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	// @RequestMapping(value = "/test")
	public void test(HttpServletRequest request, HttpServletResponse response) {
		logger.error("wander");

		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().write("Hello");
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(HttpServletRequest request, HttpServletResponse response) {
		try {
			testService.test();
		} catch (Exception e) {
			logger.error(e.toString());
		}

		return "test/test";
	}

	@RequestMapping(value = "/viewTmp", method = RequestMethod.GET)
	public String viewTmp(HttpServletRequest request, HttpServletResponse response) {
		try {
			testTmpService.test();
		} catch (Exception e) {
			logger.error(e.toString());
		}

		return "test/test";
	}

	@RequestMapping("/viewModel")
	public String viewModel(Model model) {
		String[] strArray = { "www", "wander", "test" };
		Random random = new Random();
		int idx = random.nextInt(strArray.length);
		model.addAttribute("name", strArray[idx]);
		return "test/test_model";
	}

}
