/**
* Copyright (C) 2008 Happy Fish / YuQing
*
* FastDFS Java Client may be copied only under the terms of the GNU Lesser
* General Public License (LGPL).
* Please visit the FastDFS Home Page http://www.csource.org/ for more detail.
**/

package com.zheyue.encrypt.filesystem.fastdfs;


import com.zheyue.encrypt.filesystem.common.IniFileReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
* Global variables
* @author Happy Fish / YuQing
* @version Version 1.10
*/
public class ClientGlobal
{
	public static int g_network_timeout; //millisecond
	public static String g_charset;
	public static int g_tracker_http_port;
	public static boolean g_anti_steal_token;  //if anti-steal token
	public static String g_secret_key;   //generage token secret key
	public static TrackerGroup g_tracker_group;
	
	public static final int DEFAULT_NETWORK_TIMEOUT = 30; //second
	
/**
* load global variables
* @param conf_filename config filename
* @return 
*/
	public static void init(String conf_filename) throws FileNotFoundException, IOException, Exception
	{
  		IniFileReader iniReader;
  		String[] szTrackerServers;
			String[] parts;
			
  		iniReader = new IniFileReader(conf_filename);
  		
  		g_network_timeout = iniReader.getIntValue("network_timeout", DEFAULT_NETWORK_TIMEOUT);
  		if (g_network_timeout < 0)
  		{
  			g_network_timeout = DEFAULT_NETWORK_TIMEOUT;
  		}
  		g_network_timeout *= 1000; //millisecond

  		g_charset = iniReader.getStrValue("charset");
  		if (g_charset == null || g_charset.length() == 0)
  		{
  			g_charset = "ISO8859-1";
  		}
  		
  		szTrackerServers = iniReader.getValues("tracker_server");
  		if (szTrackerServers == null)
  		{
  			throw new Exception("item \"tracker_server\" in " + conf_filename + " not found");
  		}
  		
  		InetSocketAddress[] tracker_servers = new InetSocketAddress[szTrackerServers.length];
  		for (int i=0; i<szTrackerServers.length; i++)
  		{
  			parts = szTrackerServers[i].split("\\:", 2);
  			if (parts.length != 2)
  			{
  				throw new Exception("the value of item \"tracker_server\" is invalid, the correct format is host:port");
  			}
  			
  			tracker_servers[i] = new InetSocketAddress(parts[0].trim(), Integer.parseInt(parts[1].trim()));
  		}
  		g_tracker_group = new TrackerGroup(tracker_servers);
  		
  		g_tracker_http_port = iniReader.getIntValue("http.tracker_http_port", 80);
  		g_anti_steal_token = iniReader.getBoolValue("http.anti_steal_token", false);
  		if (g_anti_steal_token)
  		{
  			g_secret_key = iniReader.getStrValue("http.secret_key");
  		}
	}
	
/**
* construct Socket object
* @param ip_addr ip address or hostname
* @param port port number
* @return connected Socket object
*/
	public static Socket getSocket(String ip_addr, int port) throws IOException
	{
		Socket sock = new Socket();
		sock.setSoTimeout(ClientGlobal.g_network_timeout);
		sock.connect(new InetSocketAddress(ip_addr, port), ClientGlobal.g_network_timeout);
		return sock;
	}
	
	private ClientGlobal()
	{
	}
}
