package com.fengdui.oa.business.auth.web;

import com.fengdui.oa.business.auth.entity.User;
import com.fengdui.oa.business.auth.service.UserService;
import com.fengdui.oa.business.auth.verify.GeetestConfig;
import com.fengdui.oa.business.auth.verify.GeetestLib;
import com.fengdui.oa.framework.constant.ConstantSession;
import com.fengdui.oa.framework.orm.MybatisService;
import com.fengdui.oa.framework.util.http.HttpUtil;
import com.fengdui.oa.framework.web.BaseController;
import com.fengdui.oa.framework.web.PageRespData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wander.Zeng
 * @data 2015-6-29 上午10:24:14
 * @desc LoginController.java
 */
@Controller
public class LoginController extends BaseController<Serializable> {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

//	@Autowired
//	private Producer captchaProducer;
	@Autowired
	private UserService userService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Value("#{config['online.user.manager.server.port']}")
	private Integer onlineUserManagerServerPort;

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

	protected Map<String, Object> prepareModel(HttpServletRequest request) {
		Map<String, Object> valMap = new HashMap<String, Object>();
		valMap.put("urlLoginVerifyPrepare", "loginVerifyPrepare");
		valMap.put("urlLoginVerifyResult", "loginVerifyResult");
		valMap.put("urlLoginCheck", "loginCheck");
		valMap.put("urlLoginCaptchaImage", "loginCaptchaImage");
		valMap.put("urlMain", "/main");
		valMap.put("urlWebsocket", "ws://" + request.getServerName() + ":" + onlineUserManagerServerPort + "/");
		return valMap;
	}

	/** 访问登录页 */
	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView tologin(HttpSession session, HttpServletRequest request, String loginnameRedirect) {
		session.setAttribute(ConstantSession.KEY_ONLINE_USER_MANAGER_SERVER_PORT, onlineUserManagerServerPort);
		Map<String, Object> valMap = prepareModel(request);
		if (StringUtils.isNotBlank(loginnameRedirect)) {
			valMap.put("loginnameRedirect", loginnameRedirect);
		}
		return new ModelAndView("auth/login", valMap);
	}

	@RequestMapping(value = { "/logout", "/tologout" }, method = RequestMethod.GET)
	public ModelAndView tologout() {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			currentUser.logout();
		}
		// return new ModelAndView("redirect:/login?loginnameRedirect=" + loginnameRedirect, prepareModel(request));
		// return new ModelAndView("redirect:/login?loginnameRedirect=" + loginnameRedirect);
		return new ModelAndView("forward:/login");
	}

	@RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
	@ResponseBody
	public Object loginCheck(User user, HttpServletRequest request, HttpSession session, boolean verifyDefault, String captchaVal) {
		String ip = HttpUtil.getIPFromClient(request);
		logger.info("acount=[{}]; ip=[{}])", user.getLoginname(), ip);

		if (StringUtils.isBlank(user.getLoginname())) {
			logger.error("loginname is null");
			return new PageRespData(false, "要填写用户名哟", "loginname");
		}
		if (StringUtils.isBlank(user.getPassword())) {
			logger.error("password is null");
			return new PageRespData(false, "密码未填写", "password");
		}
		if (!verifyDefault) {
			if (StringUtils.isBlank(captchaVal)) {
				logger.error("captchaVal is null");
				return new PageRespData(false, "请输入验证码", "captchaImage");
			} else {
				String captchaCode = (String) session.getAttribute("SESSION_KEY");
				if (!captchaVal.equalsIgnoreCase(captchaCode)) {
					logger.error("captchaVal is wrong");
					return new PageRespData(false, "验证码错误", "captchaImage");
				}
			}
		}

		Subject currentUser = SecurityUtils.getSubject();
		System.out.println(user.getLoginname());
		UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginname(), user.getPasswordEncrypt());
		try {
			currentUser.login(token);
		} catch (AuthenticationException e) {
			String[] msgArray = e.getMessage().split(ConstantCommon.SEPARATOR);
			logger.error(msgArray[0]);
			return new PageRespData(false, msgArray[1], msgArray[2]);
		}
		session.setAttribute(ConstantSession.KEY_LOGINNAME, user.getLoginname());

		return new PageRespData(true, "");
	}

	@RequestMapping(value = "/loginVerifyPrepare", method = RequestMethod.GET)
	@ResponseBody
	public Object loginVerifyPrepare(HttpServletRequest request) {
		GeetestLib gtSdk = new GeetestLib();
		gtSdk.setCaptchaId(GeetestConfig.getCaptcha_id());
		gtSdk.setPrivateKey(GeetestConfig.getPrivate_key());
		gtSdk.setGtSession(request);

		String resStr = "{}";
		if (gtSdk.preProcess() == 1) {
			// gt server is in use
			resStr = gtSdk.getSuccessPreProcessRes();
			gtSdk.setGtServerStatusSession(request, 1);
		} else {
			// gt server is down
			resStr = gtSdk.getFailPreProcessRes();
			gtSdk.setGtServerStatusSession(request, 0);
		}
		return resStr;
	}

	@RequestMapping(value = "/loginVerifyResult", method = RequestMethod.POST)
	@ResponseBody
	public Object loginVerifyResult(HttpServletRequest request) {
		// get session to share the object
		GeetestLib geetest = GeetestLib.getGtSession(request);
		int gt_server_status_code = GeetestLib.getGtServerStatusSession(request);

		String gtResult = "fail";

		if (gt_server_status_code == 1) {
			gtResult = geetest.enhencedValidateRequest(request);
			logger.info("gtResult : " + gtResult);
		} else {
			// use you own system when geetest-server is down:failback
			logger.info("failback:use your own server captcha validate");
			gtResult = "fail";
		}

		if (gtResult.equals(GeetestLib.success_res)) {
			// handle the Success result
			return GeetestLib.success_res + ":" + geetest.getVersionInfo();
		} else if (gtResult.equals(GeetestLib.forbidden_res)) {
			// handle the Forbidden result
			return GeetestLib.forbidden_res + ":" + geetest.getVersionInfo();
		} else {
			// handle the Fail result
			return GeetestLib.fail_res + ":" + geetest.getVersionInfo();
		}
	}

	@RequestMapping(value = "/loginCaptchaImage", method = RequestMethod.GET)
	public ModelAndView getCaptchaImage(HttpSession session, HttpServletResponse response) throws Exception {
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");

//		String capText = captchaProducer.createText();
//		session.setAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY, capText);
//
//		BufferedImage bi = captchaProducer.createImage(capText);
//		ServletOutputStream out = response.getOutputStream();
//		ImageIO.write(bi, "jpg", out);
//		try {
//			out.flush();
//		} finally {
//			out.close();
//		}
		return null;
	}

}
