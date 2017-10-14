package com.fengdui.test.tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class TomcatEmbed {
	private Tomcat tomcat;
	private int port = 8888;
	private String hostname = "localhost";
	private String baseDir = ".";//临时目录，tomcat会在里面写一些东西
	private String appBase = ".";//tomcat放项目的地方，对应解压版的webapp目录
	private String contextPath = "";
	private String appDir = "webapp";//项目目录
	public static void main(String[] args) {
		TomcatEmbed tomcatEmbed  = new TomcatEmbed();
		try {
			tomcatEmbed.start();
		} catch (Exception e) {
			System.out.println("tomcat start error : " + e);
			System.exit(-1);
		}
	}
	
	public void start() throws Exception {
		this.tomcat = new Tomcat();
		this.tomcat.setSilent(false);
		this.tomcat.setPort(this.port);
		this.tomcat.setHostname(this.hostname);
		this.tomcat.setBaseDir(this.baseDir);
		this.tomcat.getHost().setAppBase(System.getProperty("user.dir")+File.separator+this.appBase);
		this.tomcat.addWebapp(this.contextPath, this.appDir);
		this.tomcat.start();
		this.tomcat.getServer().await();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				if(null != TomcatEmbed.this.tomcat) {
					try {
						tomcat.stop();
					} catch (LifecycleException e) {
						System.out.println("tomcat stop error : " + e);
					}
				}
			}
		});
	}

	public Tomcat getTomcat() {
		return tomcat;
	}

	public void setTomcat(Tomcat tomcat) {
		this.tomcat = tomcat;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getBaseDir() {
		return baseDir;
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public String getAppBase() {
		return appBase;
	}

	public void setAppBase(String appBase) {
		this.appBase = appBase;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getAppDir() {
		return appDir;
	}

	public void setAppDir(String appDir) {
		this.appDir = appDir;
	}
}
