package com.fengdui.oa.framework.filesystem;

import com.xh.market.framework.filesystem.common.NameValuePair;
import com.xh.market.framework.filesystem.fastdfs.ClientGlobal;
import com.xh.market.framework.filesystem.fastdfs.StorageClient1;
import com.xh.market.framework.filesystem.fastdfs.TrackerClient;
import com.xh.market.framework.filesystem.fastdfs.TrackerServer;
import com.xh.market.framework.util.ImageUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

/**
 * @author Wander.Zeng
 * @data 2015-9-7 下午3:12:02
 * @desc FileSystemManage.java
 */
public class FileSystemManage {

	private static final Logger logger = LoggerFactory.getLogger(FileSystemManage.class);

	public static final String META_KEY_FILE_NAME = "fileName";
	public static final String META_KEY_FILE_EXT_NAME = "fileExtName";
	public static final String META_KEY_FILE_SIZE = "fileSize";
	public static final String META_KEY_IMAGE_WIDTH = "imageWidth";
	public static final String META_KEY_IMAGE_HEIGHT = "imageHeight";

	private static FileSystemPool fileSystemPool;

	// private static int waitTime = 3;

	private static Object[] getTrackerServer() throws Exception {
		TrackerServer trackerServer = fileSystemPool.getTrackerServer();
		boolean needClose = true;
		if (null == trackerServer) {
			TrackerClient tracker = new TrackerClient();
			trackerServer = tracker.getConnection();
			needClose = false;
		}
		return new Object[] { trackerServer, needClose };
	}

	public static String upload4APK(MultipartFile fileSrc, File file) throws Exception {
		String originalFilename = fileSrc.getOriginalFilename();
		String fileExtName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
		NameValuePair[] metaList = new NameValuePair[3];
		metaList[0] = new NameValuePair(META_KEY_FILE_NAME, originalFilename);
		metaList[1] = new NameValuePair(META_KEY_FILE_EXT_NAME, fileExtName);
		metaList[2] = new NameValuePair(META_KEY_FILE_SIZE, String.valueOf(fileSrc.getSize()));
		if (null == file) {
			return upload(fileSrc.getBytes(), fileExtName, metaList);
		} else {
			return upload(file.getAbsolutePath(), fileExtName, metaList);
		}
	}

	public static String upload4APK(String originalFilename, File file) throws Exception {
		String fileExtName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
		NameValuePair[] metaList = new NameValuePair[3];
		metaList[0] = new NameValuePair(META_KEY_FILE_NAME, originalFilename);
		metaList[1] = new NameValuePair(META_KEY_FILE_EXT_NAME, fileExtName);
		metaList[2] = new NameValuePair(META_KEY_FILE_SIZE, String.valueOf(file.length()));
		return upload(file.getAbsolutePath(), fileExtName, metaList);
	}

	public static String upload4Image(File file) throws Exception {
		String fileName = file.getName();
		String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
		NameValuePair[] metaList = new NameValuePair[5];
		metaList[0] = new NameValuePair(META_KEY_FILE_NAME, fileName);
		metaList[1] = new NameValuePair(META_KEY_FILE_EXT_NAME, fileExtName);
		metaList[2] = new NameValuePair(META_KEY_FILE_SIZE, String.valueOf(file.length()));
		Map<String, Integer> dimensionMap = ImageUtil.getImageDimensions(file);
		metaList[3] = new NameValuePair(META_KEY_IMAGE_WIDTH, String.valueOf(dimensionMap.get(ImageUtil.KEY_WIDTH)));
		metaList[4] = new NameValuePair(META_KEY_IMAGE_HEIGHT, String.valueOf(dimensionMap.get(ImageUtil.KEY_HEIGHT)));
		return upload(file.getAbsolutePath(), fileExtName, metaList);
	}

	public static String upload(byte[] fileByte, String fileExtName, NameValuePair... metaList) throws Exception {
		Object[] objArray = getTrackerServer();
		TrackerServer trackerServer = (TrackerServer) objArray[0];
		boolean needClose = (boolean) objArray[1];
		try {
			StorageClient1 client = new StorageClient1(trackerServer, null);
			return client.upload_file1(fileByte, fileExtName, metaList);
		} catch (Exception e) {
			logger.error("upload error : " + e);
			throw e;
		} finally {
			if (needClose) {
				fileSystemPool.close(trackerServer);
			}
		}
	}

	public static String upload(String fileName, String fileExtName, NameValuePair... metaList) throws Exception {
		Object[] objArray = getTrackerServer();
		TrackerServer trackerServer = (TrackerServer) objArray[0];
		boolean needClose = (boolean) objArray[1];
		try {
			StorageClient1 client = new StorageClient1(trackerServer, null);
			return client.upload_file1(fileName, fileExtName, metaList);
		} catch (Exception e) {
			logger.error("upload error : " + e);
			throw e;
		} finally {
			if (needClose) {
				fileSystemPool.close(trackerServer);
			}
		}
	}

	public static NameValuePair[] getMetadata(String fileId) throws Exception {
		Object[] objArray = getTrackerServer();
		TrackerServer trackerServer = (TrackerServer) objArray[0];
		boolean needClose = (boolean) objArray[1];
		try {
			StorageClient1 client = new StorageClient1(trackerServer, null);
			return client.get_metadata1(fileId);
		} catch (Exception e) {
			logger.error("getMetadata error : " + e);
			throw e;
		} finally {
			if (needClose) {
				fileSystemPool.close(trackerServer);
			}
		}
	}

	public static boolean delete(String fileId) throws Exception {
		if (StringUtils.isBlank(fileId)) {
			return false;
		}
		Object[] objArray = getTrackerServer();
		TrackerServer trackerServer = (TrackerServer) objArray[0];
		boolean needClose = (boolean) objArray[1];
		try {
			StorageClient1 client = new StorageClient1(trackerServer, null);
			return client.delete_file1(fileId) == 0;
		} catch (Exception e) {
			logger.error("delete error : " + e);
			throw new RuntimeException(e);
		} finally {
			if (needClose) {
				fileSystemPool.close(trackerServer);
			}
		}
	}

	public static byte[] download(String fileId) throws Exception {
		Object[] objArray = getTrackerServer();
		TrackerServer trackerServer = (TrackerServer) objArray[0];
		boolean needClose = (boolean) objArray[1];
		try {
			StorageClient1 client = new StorageClient1(trackerServer, null);
			return client.download_file1(fileId);
		} catch (Exception e) {
			logger.error("download error : " + e);
			throw e;
		} finally {
			if (needClose) {
				fileSystemPool.close(trackerServer);
			}
		}
	}

	public static String getFileURL(String fileId) throws Exception {
		Object[] objArray = getTrackerServer();
		TrackerServer trackerServer = (TrackerServer) objArray[0];
		boolean needClose = (boolean) objArray[1];
		try {
			return "http:/" + trackerServer.getInetSocketAddress().getAddress() + ":" + ClientGlobal.g_tracker_http_port + "/" + fileId;
		} catch (Exception e) {
			logger.error("getFileURL error : " + e);
			throw e;
		} finally {
			if (needClose) {
				fileSystemPool.close(trackerServer);
			}
		}
	}

	public static FileSystemPool getFileSystemPool() {
		return fileSystemPool;
	}

	public static void setFileSystemPool(FileSystemPool fileSystemPoolNew) {
		fileSystemPool = fileSystemPoolNew;
	}

}
