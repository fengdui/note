package net.smgui.controller;

import net.smgui.entity.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Code {
	@RequestMapping("/code")
	public String gotoCode() {
		System.out.println("aaaaa");
		return "code";
	}

	@RequestMapping(value = "/compile_java", method = RequestMethod.POST)
	public Result CompileJava(HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/file/java");
//		String msg = Runtime.getRuntime().exec();
//		return new Result(msg);
		return new Result(path);
	}
}
