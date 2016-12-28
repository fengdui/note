package com.zheyue.encrypt.filesystem;

import com.zheyue.encrypt.filesystem.fastdfs.ClientGlobal;
import com.zheyue.encrypt.filesystem.fastdfs.ProtoCommon;
import com.zheyue.encrypt.filesystem.fastdfs.TrackerClient;
import com.zheyue.encrypt.filesystem.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


/**
 * @author FD
 * @date 2016/11/30
 */
public class FileSystemPool {

	private static final Logger logger = LoggerFactory.getLogger(FileSystemPool.class);

	private Integer connectTimeout;
	private Integer networkTimeout;
	private String charset;
	private String trackerServers;
	private Integer trackerHttpPort;
	private Boolean antiStealToken;
	private String secretKey;
	private int poolSize;
	private LinkedBlockingQueue<TrackerServer> tsPool = null;

	private FileSystemPool() {

	}

	public void init() throws Exception {
		ClientGlobal.init(connectTimeout, networkTimeout, charset, trackerServers, trackerHttpPort, antiStealToken, secretKey);
		initPool();
	}

	private void initPool() throws Exception {
		this.tsPool = new LinkedBlockingQueue<TrackerServer>();
		for (int i = 0; i < poolSize; i++) {
			TrackerClient tracker = new TrackerClient();
			TrackerServer trackerServer = tracker.getConnection();
			if (null != trackerServer) {
				ProtoCommon.activeTest(trackerServer.getSocket());
				this.tsPool.put(trackerServer);
			}
		}
		keepingPool();
	}

	private void keepingPool() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				for (TrackerServer trackerServer : tsPool) {
					try {
						ProtoCommon.activeTest(trackerServer.getSocket());
					} catch (IOException e) {
						logger.error("keepingPool error : " + e);
					}
				}
			}
		}, 30 * 1000, 30 * 1000);
	}

	public TrackerServer getTrackerServer() {
		return this.tsPool.poll();
	}

	public TrackerServer getTrackerServer(long timeout) throws Exception {
		TrackerServer trackerServer = this.getTrackerServer();
		if (null == trackerServer) {
			return this.tsPool.poll(timeout, TimeUnit.SECONDS);
		}
		return trackerServer;
	}

	public boolean close(TrackerServer trackerServer) throws Exception {
		if (null != trackerServer) {
			this.tsPool.put(trackerServer);
			return true;
		}
		return false;
	}

	public void reset() throws Exception {
		this.initPool();
	}

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Integer getNetworkTimeout() {
		return networkTimeout;
	}

	public void setNetworkTimeout(Integer networkTimeout) {
		this.networkTimeout = networkTimeout;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getTrackerServers() {
		return trackerServers;
	}

	public void setTrackerServers(String trackerServers) {
		this.trackerServers = trackerServers;
	}

	public Integer getTrackerHttpPort() {
		return trackerHttpPort;
	}

	public void setTrackerHttpPort(Integer trackerHttpPort) {
		this.trackerHttpPort = trackerHttpPort;
	}

	public Boolean getAntiStealToken() {
		return antiStealToken;
	}

	public void setAntiStealToken(Boolean antiStealToken) {
		this.antiStealToken = antiStealToken;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

}
