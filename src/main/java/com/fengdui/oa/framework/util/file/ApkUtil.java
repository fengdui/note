package com.fengdui.oa.framework.util.file;

import com.fengdui.oa.business.common.Constant;
import com.fengdui.oa.business.res.entity.Apk;
import com.fengdui.oa.business.res.entity.ApkIcon;
import com.fengdui.oa.business.res.entity.ApkInfo;
import com.fengdui.oa.framework.util.ImageUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.context.ContextLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Wander.Zeng
 * @data 2015-9-10 下午2:39:10
 * @desc ApkUtil.java
 */
public class ApkUtil {

	public static ApkInfo getApkInfo(File apk) throws Exception {
		Process process = null;
		String command = "aapt dump badging " + apk.getAbsolutePath();
		try {
			process = Runtime.getRuntime().exec(command);
		} catch (Exception e) {
			try {
				command = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(Constant.PATH_COMMOND) + File.separator + command;
				process = Runtime.getRuntime().exec(command);
			} catch (Exception e1) {
				process = null;
				throw e1;
			}
		}

		InputStream is = null;
		BufferedReader br = null;
		try {
			Map<String, List<String>> dumpMap = new HashMap<String, List<String>>();
			process.waitFor();
			is = process.getInputStream();
			if (is.available() <= 0) {
				throw new Exception("解析错误");
			}
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] lineArray = line.split(":", 2);
				if (lineArray.length == 2) {
					String line0 = lineArray[0].trim();
					String line1 = lineArray[1].trim();
					List<String> list = dumpMap.get(line0);
					if (null == list) {
						list = new ArrayList<String>();
					}
					list.add(line1);
					dumpMap.put(line0, list);
				}
			}
			return new ApkInfo(dumpMap);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (null != is) {
					is.close();
					is = null;
				}
				if (null != br) {
					br.close();
					br = null;
				}
				if (null != process) {
					process.destroy();
					process = null;
				}
			} catch (Exception e) {
				throw e;
			}
		}
	}

	public static List<ApkIcon> getApkIcon(File apkFile, Map<String, String> applicationIconMap) throws Exception {
		List<ApkIcon> apkIconList = new ArrayList<ApkIcon>();
		List<Integer> apkIconMissList = new ArrayList<Integer>();

		// 保存已有的 icon 图片
		for (int i = 0; i < Apk.DENSITY_ARRAY.length; i++) {
			String density = Apk.DENSITY_ARRAY[i];
			String iconPathOfZip = applicationIconMap.get(density);
			if (null != iconPathOfZip) {
				File iconFile = null;
				try {
					iconFile = getApkIconFile(apkFile, density, iconPathOfZip);
					Map<String, Integer> dimensions = ImageUtil.getImageDimensions(iconFile);
//					apkIconList.add(new ApkIcon(i + 1, FileSystemManage.upload4Image(iconFile), iconFile, dimensions.get(ImageUtil.KEY_WIDTH), dimensions.get(ImageUtil.KEY_HEIGHT)));
				} catch (Exception e) {
					if (null != iconFile && iconFile.exists()) {
						iconFile.delete();
					}
					for (ApkIcon apkIcon : apkIconList) {
						if (null != apkIcon.getFile() && apkIcon.getFile().exists()) {
							apkIcon.getFile().delete();
						}
						if (null != apkIcon.getFileId()) {
//							FileSystemManage.delete(apkIcon.getFileId());
						}
					}
					throw e;
				}
			} else {
				apkIconMissList.add(i + 1);
			}
		}

		// 补全缺失的 icon 图片
		for (int dpiType : apkIconMissList) {
			ApkIcon apkIcon4Miss = getApkIcon4Miss(apkIconList, dpiType, dpiType);
			// 不对对应级别的图片做针对性处理
			apkIconList.add(new ApkIcon(dpiType, apkIcon4Miss.getFileId(), apkIcon4Miss.getFile(), apkIcon4Miss.getWidth(), apkIcon4Miss.getHeight()));
		}

		// 设置默认 icon 图片
		// ApkIcon apkIcon4Default = getApkIcon4Default(applicationIconMap, apkIconList);
		// apkIconList.add(new ApkIcon(ApkIcon.DPI_TYPE_DEFAULT, apkIcon4Default.getFileId(), apkIcon4Default.getFile()));

		return apkIconList;
	}

	@SuppressWarnings("resource")
	private static File getApkIconFile(File apkFile, String density, String iconPathOfZip) throws Exception {
		ZipFile zipFile = new ZipFile(apkFile);
		ZipEntry zipEntry = null;// 得到zip包中文件
		Enumeration<?> enume = zipFile.entries();

		while (enume.hasMoreElements()) {
			zipEntry = (ZipEntry) enume.nextElement();
			if (zipEntry.getName().equalsIgnoreCase(iconPathOfZip)) {
				InputStream is = null;
				try {
					is = zipFile.getInputStream(zipEntry);
					StringBuilder iconPath = new StringBuilder(apkFile.getParent());
					iconPath.append(File.separator);
					iconPath.append(apkFile.getName().substring(0, apkFile.getName().lastIndexOf(".")) + "_" + density + "_" + iconPathOfZip.substring(iconPathOfZip.lastIndexOf("/") + 1));
					File iconFile = new File(iconPath.toString());
					FileUtils.copyInputStreamToFile(is, iconFile);
					return iconFile;
				} catch (Exception e) {
					throw e;
				} finally {
					try {
						if (null != is) {
							is.close();
							is = null;
						}
					} catch (Exception e) {
						throw e;
					}
				}
			}
		}
		throw new Exception("没有找到" + iconPathOfZip);
	}

	private static ApkIcon getApkIcon4Miss(List<ApkIcon> apkIconList, int dpiType, int dpiType2Find) {
		dpiType2Find++;
		if (dpiType2Find > Apk.DENSITY_ARRAY.length) {
			dpiType2Find = dpiType - 1;
			if (dpiType2Find <= 0) {
				return null;
			}
		}
		for (ApkIcon apkIcon : apkIconList) {
			if (apkIcon.getDpiType().equals(dpiType2Find)) {
				return apkIcon;
			}
		}
		return getApkIcon4Miss(apkIconList, dpiType, dpiType2Find);
	}

	@SuppressWarnings("unused")
	private static ApkIcon getApkIcon4Default(Map<String, String> applicationIconMap, List<ApkIcon> apkIconList) {
		String iconValDefault = applicationIconMap.get(ApkInfo.KEY_DEFAULT);
		int dpiType = 0;
		for (Entry<String, String> entry : applicationIconMap.entrySet()) {
			String iconKey = entry.getKey();
			if (iconKey.equals(ApkInfo.KEY_DEFAULT)) {
				continue;
			}
			String iconVal = entry.getValue();
			if (iconVal.equals(iconValDefault)) {
				dpiType = ArrayUtils.indexOf(Apk.DENSITY_ARRAY, iconKey) + 1;
				break;
			}
		}
		if (dpiType <= 0) {
			dpiType = Apk.DPI_TYPE_DEFAULT_APPOINT;
		}
		for (ApkIcon apkIcon : apkIconList) {
			if (apkIcon.getDpiType().equals(dpiType)) {
				return apkIcon;
			}
		}
		return null;
	}

}
