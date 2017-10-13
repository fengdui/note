package com.fengdui.test.xhoa.business.res.entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wander.Zeng
 * @data 2015-9-10 下午6:09:28
 * @desc ApkInfo.java
 */
public class ApkInfo {

	public static final String KEY_DEFAULT = "default";

	private String packageName;
	private Integer versionCode;
	private String versionName;
	private Integer sdkVersion;
	private Integer targetSdkVersion;
	private List<String> usesPermission;
	private List<String> usesImpliedPermission;
	private List<String> locales;
	private Map<String, String> applicationLabel;
	private List<String> densities;
	private Map<String, String> applicationIcon;
	private String launchableActivityName;
	private List<String> usesFeature;// 用于指定android程序，是否需要某种硬件或软件资源/功能
	private List<String> usesImpliedFeature;
	private List<String> supportsScreens;
	private Boolean supportsAnyDensity;
	private List<String> nativeCode;
	private String usesGlEs;

	public ApkInfo() {

	}

	public ApkInfo(Map<String, List<String>> dumpMap) {
		String[] packageArray = dumpMap.get("package").get(0).split(" ");
		for (String packageInfo : packageArray) {
			String[] packageInfoArray = packageInfo.split("=");
			if (packageInfoArray.length == 2) {
				String val = pickVal(packageInfoArray[1]);
				if (packageInfo.contains("name=")) {
					this.packageName = val;
				} else if (packageInfo.contains("versionCode=")) {
					if (StringUtils.isBlank(val)) {
						this.versionCode = 0;
					} else {
						this.versionCode = Integer.parseInt(val);
					}
				} else if (packageInfo.contains("versionName=")) {
					if (StringUtils.isBlank(val)) {
						this.versionName = "null";
					} else {
						this.versionName = val;
					}
				}
			}
		}

		List<String> sdkVersionVal = dumpMap.get("sdkVersion");
		if (null != sdkVersionVal) {
			this.sdkVersion = Integer.parseInt(pickVal(sdkVersionVal.get(0)));
		}

		List<String> targetSdkVersionVal = dumpMap.get("targetSdkVersion");
		if (null != targetSdkVersionVal) {
			this.targetSdkVersion = Integer.parseInt(pickVal(targetSdkVersionVal.get(0)));
		}

		this.usesPermission = dumpMap.get("uses-permission");
		if (null != usesPermission) {
			for (int i = 0; i < usesPermission.size(); i++) {
				usesPermission.set(i, pickVal(usesPermission.get(i)));
			}
		}

		this.usesImpliedPermission = dumpMap.get("uses-implied-permission");
		if (null != usesImpliedPermission) {
			for (int i = 0; i < usesImpliedPermission.size(); i++) {
				usesImpliedPermission.set(i, pickVal(usesImpliedPermission.get(i)));
			}
		}

		String[] localArray = dumpMap.get("locales").get(0).split(" ");
		this.locales = new ArrayList<String>();
		this.applicationLabel = new HashMap<String, String>();
		for (String local : localArray) {
			local = pickVal(local);
			locales.add(local);
			if (local.equals("--_--")) {
				applicationLabel.put(KEY_DEFAULT, pickVal(dumpMap.get("application-label").get(0)));
			} else {
				applicationLabel.put(local, pickVal(dumpMap.get("application-label-" + local).get(0)));
			}
		}

		String[] densityArray = dumpMap.get("densities").get(0).split(" ");
		this.densities = new ArrayList<String>();
		this.applicationIcon = new HashMap<String, String>();
		for (String density : densityArray) {
			density = pickVal(density);
			densities.add(density);
			applicationIcon.put(density, pickVal(dumpMap.get("application-icon-" + density).get(0)));
		}

		String[] applicationArray = dumpMap.get("application").get(0).split(" ");
		for (String applicationInfo : applicationArray) {
			String[] applicationInfoArray = applicationInfo.split("=");
			if (applicationInfoArray.length == 2) {
				String val = pickVal(applicationInfoArray[1]);
				if (applicationInfo.contains("label=")) {
					applicationLabel.put(KEY_DEFAULT, val);
				} else if (applicationInfo.contains("icon=")) {
					applicationIcon.put(KEY_DEFAULT, val);
				}
			}
		}

		List<String> launchableActivityVal = dumpMap.get("launchable-activity");
		if (null != launchableActivityVal) {
			String[] launchableActivityArray = launchableActivityVal.get(0).split(" ");
			for (String launchableActivityInfo : launchableActivityArray) {
				if (launchableActivityInfo.contains("name=")) {
					this.launchableActivityName = pickVal(launchableActivityInfo.split("=")[1]);
					break;
				}
			}
		}

		this.usesFeature = dumpMap.get("uses-feature");
		for (int i = 0; i < usesFeature.size(); i++) {
			usesFeature.set(i, pickVal(usesFeature.get(i)));
		}

		this.usesImpliedFeature = dumpMap.get("uses-implied-feature");
		for (int i = 0; i < usesImpliedFeature.size(); i++) {
			usesImpliedFeature.set(i, pickVal(usesImpliedFeature.get(i)));
		}

		String[] supportsScreenArray = dumpMap.get("supports-screens").get(0).split(" ");
		this.supportsScreens = new ArrayList<String>();
		for (String supportsScreen : supportsScreenArray) {
			supportsScreens.add(pickVal(supportsScreen));
		}

		this.supportsAnyDensity = Boolean.parseBoolean(pickVal(dumpMap.get("supports-any-density").get(0)));

		List<String> nativeCodeVal = dumpMap.get("native-code");
		if (null != nativeCodeVal) {
			String[] nativeCodeArray = nativeCodeVal.get(0).split(" ");
			this.nativeCode = new ArrayList<String>();
			for (String code : nativeCodeArray) {
				nativeCode.add(pickVal(code));
			}
		}

		List<String> usesGlEsVal = dumpMap.get("uses-gl-es");
		if (null != usesGlEsVal) {
			this.usesGlEs = pickVal(usesGlEsVal.get(0));
		}
	}

	private String pickVal(String val) {
		return val.trim().replace("'", "");
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Integer getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public Integer getSdkVersion() {
		return sdkVersion;
	}

	public void setSdkVersion(Integer sdkVersion) {
		this.sdkVersion = sdkVersion;
	}

	public Integer getTargetSdkVersion() {
		return targetSdkVersion;
	}

	public void setTargetSdkVersion(Integer targetSdkVersion) {
		this.targetSdkVersion = targetSdkVersion;
	}

	public List<String> getUsesPermission() {
		return usesPermission;
	}

	public void setUsesPermission(List<String> usesPermission) {
		this.usesPermission = usesPermission;
	}

	public List<String> getUsesImpliedPermission() {
		return usesImpliedPermission;
	}

	public void setUsesImpliedPermission(List<String> usesImpliedPermission) {
		this.usesImpliedPermission = usesImpliedPermission;
	}

	public List<String> getLocales() {
		return locales;
	}

	public void setLocales(List<String> locales) {
		this.locales = locales;
	}

	public Map<String, String> getApplicationLabel() {
		return applicationLabel;
	}

	public void setApplicationLabel(Map<String, String> applicationLabel) {
		this.applicationLabel = applicationLabel;
	}

	public List<String> getDensities() {
		return densities;
	}

	public void setDensities(List<String> densities) {
		this.densities = densities;
	}

	public Map<String, String> getApplicationIcon() {
		return applicationIcon;
	}

	public void setApplicationIcon(Map<String, String> applicationIcon) {
		this.applicationIcon = applicationIcon;
	}

	public String getLaunchableActivityName() {
		return launchableActivityName;
	}

	public void setLaunchableActivityName(String launchableActivityName) {
		this.launchableActivityName = launchableActivityName;
	}

	public List<String> getUsesFeature() {
		return usesFeature;
	}

	public void setUsesFeature(List<String> usesFeature) {
		this.usesFeature = usesFeature;
	}

	public List<String> getUsesImpliedFeature() {
		return usesImpliedFeature;
	}

	public void setUsesImpliedFeature(List<String> usesImpliedFeature) {
		this.usesImpliedFeature = usesImpliedFeature;
	}

	public List<String> getSupportsScreens() {
		return supportsScreens;
	}

	public void setSupportsScreens(List<String> supportsScreens) {
		this.supportsScreens = supportsScreens;
	}

	public Boolean getSupportsAnyDensity() {
		return supportsAnyDensity;
	}

	public void setSupportsAnyDensity(Boolean supportsAnyDensity) {
		this.supportsAnyDensity = supportsAnyDensity;
	}

	public List<String> getNativeCode() {
		return nativeCode;
	}

	public void setNativeCode(List<String> nativeCode) {
		this.nativeCode = nativeCode;
	}

	public String getUsesGlEs() {
		return usesGlEs;
	}

	public void setUsesGlEs(String usesGlEs) {
		this.usesGlEs = usesGlEs;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
