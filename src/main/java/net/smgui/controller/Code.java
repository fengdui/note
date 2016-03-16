package net.smgui.controller;

import net.smgui.common.Cue;
import net.smgui.common.PageRespData;
import net.smgui.service.CompileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Code {

	@Autowired
	private CompileService compileService;

	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	@ResponseBody
	public Object fileUpload(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		if (!fileName.endsWith(".java")) {
			return new PageRespData(false, "上传出错 : 后缀名不正确");
		}
		try {
			String filePath = compileService.importFile(file);
			return new PageRespData(true, Cue.SUCCESS, filePath);
		} catch (Exception e) {
			return new PageRespData(false, "上传出错 : " + e.getMessage());
		}
	}

	@RequestMapping(value = "/compileJava", method = RequestMethod.POST)
	@ResponseBody
	public Object CompileJava(HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/file/java");
//		String msg = Runtime.getRuntime().exec();
//		return new Result(msg);
		System.out.println("aaa");
		return new PageRespData(true, Cue.SUCCESS, path);
	}
}
