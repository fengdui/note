package com.fengdui.oa.framework.communication.socket;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author Wander.Zeng
 * @data 2015-7-14 下午2:22:05
 * @desc OnlineManager.java
 */
public class OnlineUserManagerServer extends WebSocketServer {

	private static final Logger logger = LoggerFactory.getLogger(OnlineUserManagerServer.class);

	private static final Map<WebSocket, String> onlineUserMap = new HashMap<WebSocket, String>();

	public OnlineUserManagerServer(int port) throws UnknownHostException {
		super(new InetSocketAddress(port));
		logger.debug("using port [" + port + "] to startup the OnlineUserManagerServer");
	}

	/** 建立新连接时自动调用此方法 */
	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		logger.debug("OnlineUserManagerServer open");
	}

	/** 当接收到新消息时调用此方法 */
	@Override
	public void onMessage(WebSocket conn, String message) {
		logger.debug("new Message : " + message);

		if (null != message) {
			if (message.startsWith(OnlineUserManagerConstant.PRE_ONLINE)) {
				userOnline(conn, message.split(OnlineUserManagerConstant.SEPARATOR)[1]);
			} else if (message.startsWith(OnlineUserManagerConstant.PRE_KICK)) {
				userKick(message.split(OnlineUserManagerConstant.SEPARATOR)[1]);
			} else if (message.startsWith(OnlineUserManagerConstant.PRE_OFFLINE)) {
				userOffline(conn);
			} else if (message.startsWith(OnlineUserManagerConstant.PRE_COUNT)) {
				userCount(conn);
			} else if (message.startsWith(OnlineUserManagerConstant.PRE_LIST)) {
				userList(conn);
			} else {
				userChat(conn, message);
			}
		}
	}

	/** 连接关闭时自动调用此方法 */
	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		logger.debug("client close : " + conn.getRemoteSocketAddress());
		userOffline(conn);
	}

	/** 当连接出现错误时自动调用此方法 */
	@Override
	public void onError(WebSocket conn, Exception ex) {
		logger.error("client error : " + ex);
	}

	private void sendMsgToUser(WebSocket conn, String type, Object msg) {
		if (null != conn) {
			JSONObject resp = new JSONObject();
			resp.put(OnlineUserManagerConstant.KEY_TYPE, type);
			resp.put(OnlineUserManagerConstant.KEY_MSG, msg);
			conn.send(resp.toString());
		}
	}

	public void sendMsgToAll(String msg) {
		Set<WebSocket> connSet = onlineUserMap.keySet();
		synchronized (connSet) {
			for (WebSocket conn : connSet) {
				onMessage(conn, msg);
			}
		}
	}

	public void userSessionTimeout(String user) {
		WebSocket conn = getConnByUser(user);
		if (null != conn) {
			userOffline(conn);
		}
	}

	private void userOnline(WebSocket conn, String user) {
		WebSocket connExist = getConnByUser(user);
		if (null == connExist) {
			onlineUserMap.put(conn, user);
			sendMsgToUser(conn, OnlineUserManagerConstant.TYPE_ONLINE, "");
		} else {
			sendMsgToUser(conn, OnlineUserManagerConstant.TYPE_KICK_CONFLICT, "");
			sendMsgToUser(connExist, OnlineUserManagerConstant.TYPE_ONLINE_CONFLICT, conn.getRemoteSocketAddress().getHostString());
		}
	}

	private void userKick(String user) {
		sendMsgToUser(getConnByUser(user), OnlineUserManagerConstant.TYPE_KICK_FORCE, "");
	}

	private boolean userOffline(WebSocket conn) {
		if (onlineUserMap.containsKey(conn)) {
			onlineUserMap.remove(conn);
			return true;
		} else {
			return false;
		}
	}

	private void userCount(WebSocket conn) {
		sendMsgToUser(conn, OnlineUserManagerConstant.TYPE_COUNT, onlineUserMap.size());
	}

	private void userList(WebSocket conn) {
		sendMsgToUser(conn, OnlineUserManagerConstant.TYPE_LIST, onlineUserMap.values());
	}

	private void userChat(WebSocket conn, String msg) {
		sendMsgToUser(conn, OnlineUserManagerConstant.TYPE_CHAT, msg);
	}

	private WebSocket getConnByUser(String user) {
		if (StringUtils.isBlank(user)) {
			return null;
		}
		Set<Entry<WebSocket, String>> entrySet = onlineUserMap.entrySet();
		synchronized (entrySet) {
			for (Entry<WebSocket, String> entry : entrySet) {
				if (user.equals(entry.getValue())) {
					return entry.getKey();
				}
			}
		}
		return null;
	}

}
