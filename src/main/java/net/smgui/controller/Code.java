package net.smgui.controller;

import net.smgui.common.Cue;
import net.smgui.common.Result;
import net.smgui.common.Src;
import net.smgui.entity.Problem;
import net.smgui.service.CompileService;
import net.smgui.service.Crawler;
import net.smgui.util.ContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Controller
public class Code {

	@Autowired
	private CompileService compileService;
	@Autowired
	private Crawler crawler;

//	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
//	@ResponseBody
//	public Object fileUpload(MultipartFile file) {
//		String fileName = file.getOriginalFilename();
//		if (!fileName.endsWith(".java")) {
//			return new Result(false, "上传出错 : 后缀名不正确");
//		}
//		try {
//			String filePath = compileService.importFile(file);
//			return new Result(true, Cue.SUCCESS, filePath);
//		} catch (Exception e) {
//			return new Result(false, "上传出错 : " + e.getMessage());
//		}
//	}
	@RequestMapping("/code/{pid}")
	@ResponseBody
	public Problem code(@PathVariable("pid") String pid) {
		Problem pro = crawler.crawl(pid);
		return pro;
	}
	@RequestMapping("/code")
	public ModelAndView code(ModelAndView mv) {
		Problem pro = crawler.crawl("1002");
		mv.setViewName("code");
		mv.addObject("pro", pro);
		return mv;
	}

	@RequestMapping(value = "/compileJava", method = RequestMethod.POST)
	@ResponseBody
	public Object CompileJava(HttpServletRequest request, @RequestBody Src code) {
		String path = request.getSession().getServletContext().getRealPath("/file/java");
		try {
//			path = compileService.savaJava(code.getCode(), path);
//			if (path == null) {
//				return false;
//			}
			String ans = compileService.compileJava(code.getCode());
			System.out.println("ans"+ans);
			if (ans.length() > 1000) {
				ans = ans.substring(0, 1000)+".....";
			}
			if (ans.trim().length() > 0) {
				return new Result(true, ans);
			}
			else {
				return new Result(true, "编译成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "编译失败");
		}
	}
	@RequestMapping(value = "/runCode", method = RequestMethod.POST)
	@ResponseBody
	public Object runCode(HttpServletRequest request, HttpServletResponse response, @RequestBody Src code) throws UnsupportedEncodingException {

		System.out.println("XXX");
		InputStream is = new ByteArrayInputStream(code.getCode().getBytes("UTF-8"));
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os, true, "UTF-8");
		InputStream SystemIn = System.in;
		PrintStream SystemOut = System.out;
		System.setIn(is);
		System.setOut(ps);
		try {

			Class clazz = ContextUtil.map.get("Main");
//			Object obj = clazz.newInstance();
			Method m = clazz.getMethod("main", String[].class);
			m.invoke(null, new Object[]{new String[]{}});
			String str = os.toString();
			return new Result(true, str);
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace(ps);
			String str = os.toString();
			return new Result(true, str);
		}
		finally {
			System.setIn(SystemIn);
			System.setOut(SystemOut);
		}
	}
}
