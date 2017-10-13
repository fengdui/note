package com.fengdui.oa.framework.communication.socket;

/**
 * @author Wander.Zeng
 * @data 2015-7-14 下午4:16:50
 * @desc ConstantCommunication.java
 */
public class OnlineUserManagerConstant {

	public static final String SEPARATOR = "@";
	public static final String KEY_TYPE = "type";
	public static final String KEY_MSG = "msg";

	/** 上线 */
	public static final String PRE_ONLINE = "[online]";
	/** 被踢下线 */
	public static final String PRE_KICK = "[kick]";
	/** 主动下线 */
	public static final String PRE_OFFLINE = "[offline]";
	/** 在线个数 */
	public static final String PRE_COUNT = "[count]";
	/** 在线列表 */
	public static final String PRE_LIST = "[list]";

	/** 上线 */
	public static final String TYPE_ONLINE = "online";
	/** 上线冲突 */
	public static final String TYPE_ONLINE_CONFLICT = "online_conflict";
	/** 登录冲突 */
	public static final String TYPE_KICK_CONFLICT = "kick_conflict";
	/** 被踢下线 */
	public static final String TYPE_KICK_FORCE = "kick_force";
	/** 在线个数 */
	public static final String TYPE_COUNT = "count";
	/** 在线列表 */
	public static final String TYPE_LIST = "list";
	/** 聊天 */
	public static final String TYPE_CHAT = "chat";

}
