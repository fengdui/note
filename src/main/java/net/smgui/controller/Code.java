package net.smgui.controller;

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

	@RequestMapping(value = "code")
	public String gotoCode() {
		System.out.println(this.getClass().getClassLoader());
		return "code";
	}

	@RequestMapping(value = "/compile_java", method = RequestMethod.POST)
	public Object CompileJava(HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/file/java");
//		String msg = Runtime.getRuntime().exec();
//		return new Result(msg);
		return new PageRespData(true, path);
	}

	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	@ResponseBody
	public Object fileUpload(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		if (!fileName.endsWith(".java")) {
			return new PageRespData(false, "上传出错 : 后缀名不正确");
		}
		try {
			String filePath = compileService.importFile(file);
			return new PageRespData(true, Cue.SAVE_SUCCESS, filePath);
		} catch (Exception e) {
			getLogger().error("fileUpload error : " + e);
			return new PageRespData(false, "上传出错 : " + e.getMessage());
		}
	}
}
