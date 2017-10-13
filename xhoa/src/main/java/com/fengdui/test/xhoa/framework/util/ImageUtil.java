package com.fengdui.test.xhoa.framework.util;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ImageUtil {

	public static final String KEY_WIDTH = "width";
	public static final String KEY_HEIGHT = "height";

	public static int getImageWidth(File file) throws Exception {
		String fileName = file.getName();
		String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
		Iterator<ImageReader> readers = ImageIO.getImageReadersBySuffix(fileExtName);
		ImageReader reader = readers.next();
		ImageInputStream imageInputStream = ImageIO.createImageInputStream(file);
		reader.setInput(imageInputStream, true);
		return reader.getWidth(0);
	}

	public static int getImageHeight(File file) throws Exception {
		String fileName = file.getName();
		String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
		Iterator<ImageReader> readers = ImageIO.getImageReadersBySuffix(fileExtName);
		ImageReader reader = readers.next();
		ImageInputStream imageInputStream = ImageIO.createImageInputStream(file);
		reader.setInput(imageInputStream, true);
		return reader.getHeight(0);
	}

	public static Map<String, Integer> getImageDimensions(File file) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		String fileName = file.getName();
		String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
		Iterator<ImageReader> readers = ImageIO.getImageReadersBySuffix(fileExtName);
		ImageReader reader = readers.next();
		ImageInputStream imageInputStream = ImageIO.createImageInputStream(file);
		reader.setInput(imageInputStream, true);
		map.put(KEY_WIDTH, reader.getWidth(0));
		map.put(KEY_HEIGHT, reader.getHeight(0));
		return map;
	}

	public static void resizeImageByLib(File fileSrc, String fileTar, double scale) throws Exception {
		Thumbnails.of(fileSrc).scale(scale).toFile(fileTar);
	}

}
