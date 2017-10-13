package com.fengdui.test.xhoa.framework.web;

import com.fengdui.test.xhoa.business.time.service.QuartzScheduleManager;
import com.fengdui.test.xhoa.business.time.service.TimeTaskService;
import com.fengdui.test.xhoa.framework.communication.socket.OnlineUserManagerServer;
import org.java_websocket.WebSocketImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-7-14 上午11:17:42
 * @desc InitServlet.java
 */
public class InitService {

	private static final Logger logger = LoggerFactory.getLogger(InitService.class);

	@Value("#{config['online.user.manager.server.port']}")
	private Integer onlineUserManagerServerPort;

	@Value("#{config['online.user.manager.server.debug']}")
	private boolean onlineUserManagerServerDebug;

	private OnlineUserManagerServer managerServer;

	@Autowired
	private TimeTaskService timeTaskService;
	@Autowired
	private QuartzScheduleManager quartzScheduleManager;
	public void init() {
		this.startOnlineUserManagerServer();
//		selfStartJob();
	}

	private void startOnlineUserManagerServer() {
		try {
			WebSocketImpl.DEBUG = onlineUserManagerServerDebug;
			if (null != onlineUserManagerServerPort) {
				managerServer = new OnlineUserManagerServer(onlineUserManagerServerPort);
				managerServer.start();
			} else {
				throw new Exception("port is null");
			}
		} catch (Exception e) {
			logger.error("OnlineUserManagerServer start error : " + e);
		}
	}

	public OnlineUserManagerServer getManagerServer() {
		return managerServer;
	}
//	private void selfStartJob() {
//		List<TimeTask> list = timeTaskService.queryAll();
//		for(TimeTask t : list) {
//			if(t.getSelfStart()) {
//				try {
//					quartzScheduleManager.startJob(t);
//				} catch (SchedulerException e) {
//					logger.error("Job selfStart error" + e);
//				}
//			}
//		}
//	}
}
