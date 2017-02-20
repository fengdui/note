package com.fengdui.oa.business.common.web;

import com.xh.market.framework.filesystem.FileSystemManage;
import com.xh.market.framework.orm.MybatisService;
import com.xh.market.framework.util.encrypt.EncryptUtil;
import com.xh.market.framework.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * @author Wander.Zeng
 * @data 2015-9-14 下午10:03:01
 * @desc FileController.java
 */
@Controller
@RequestMapping("/file")
public class FileController extends BaseController<Serializable> {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

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

	@RequestMapping(value = "/{fileId}", method = RequestMethod.GET)
	@ResponseBody
	public Object fetchFile(@PathVariable String fileId, HttpServletResponse response) throws Exception {
		fileId = EncryptUtil.decodeFileId(fileId);
		//		HttpHeaders headers = new HttpHeaders();
		//		// headers.setContentType(MediaType.IMAGE_JPEG);
		//		headers.add("Content-Type", "image/png");
		//		// headers.add("Content-Length", "" + body.length);
		//		// headers.add("Content-Disposition", "attachment;filename=" + fileId.substring(fileId.lastIndexOf("/") + 1));
		//		headers.add("Content-Disposition", "filename=" + fileId.substring(fileId.lastIndexOf("/") + 1));
		//		headers.add("Last-Modified", String.valueOf(System.currentTimeMillis()));
		//		return new ResponseEntity<byte[]>(FileSystemManage.download(fileId), headers, HttpStatus.NOT_MODIFIED);
		return FileSystemManage.download(fileId);
	}

}
