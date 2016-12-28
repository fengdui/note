/**
* Copyright (C) 2008 Happy Fish / YuQing
*
* FastDFS Java Client may be copied only under the terms of the GNU Lesser
* General Public License (LGPL).
* Please visit the FastDFS Home Page http://www.csource.org/ for more detail.
*/

package com.zheyue.encrypt.filesystem.fastdfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
* Tracker Server Info
* @author Happy Fish / YuQing
* @version Version 1.10
*/
public class TrackerServer
{
	protected Socket trackerSocket;
	protected InetSocketAddress trackerInetSocketAddress;
	
/**
* Constructor
* @param trackerSocket Socket of tracker server
* @param trackerInetSocketAddress the tracker server info
*/
	public TrackerServer(Socket sock, InetSocketAddress trackerInetSocketAddress)
	{
		this.trackerSocket = sock;
		this.trackerInetSocketAddress = trackerInetSocketAddress;
	}
	
/**
* get the connected tracker socket
* @return the tracker socket
*/
	public Socket getSocket()
	{
		return this.trackerSocket;
	}
	
/**
* get the tracker server info
* @return the tracker server info
*/
	public InetSocketAddress getInetSocketAddress()
	{
		return this.trackerInetSocketAddress;
	}
	
	public OutputStream getOutputStream() throws IOException
	{
		return this.trackerSocket.getOutputStream();
	}
	
	public InputStream getInputStream() throws IOException
	{
		return this.trackerSocket.getInputStream();
	}
	
	public void close() throws IOException
	{
		if (this.trackerSocket != null)
		{
			ProtoCommon.closeSocket(this.trackerSocket);
			this.trackerSocket = null;
		}
	}
}
