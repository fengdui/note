package smgui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Code {
	@RequestMapping("/code")
	public String gotoCode() {
		System.out.println("sss");
		return "code";
	}
}
