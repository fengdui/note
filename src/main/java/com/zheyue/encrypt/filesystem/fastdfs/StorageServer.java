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
import java.net.Socket;

/**
* Storage Server Info
* @author Happy Fish / YuQing
* @version Version 1.4
*/
public class StorageServer
{
	protected Socket storageSocket;
	protected int store_path_index = 0;
	
/**
* @param storageSocket Socket of storage server, can be null
* @param store_path the store path index on the storage server
*/
	public StorageServer(Socket sock, int store_path)
	{
		this.storageSocket = sock;
		this.store_path_index = store_path;
	}

/**
* @param storageSocket Socket of storage server, can be null
* @param store_path the store path index on the storage server
*/
	public StorageServer(Socket sock, byte store_path)
	{
		this.storageSocket = sock;
		if (store_path < 0)
		{
			this.store_path_index = 256 + store_path;
		}
		else
		{
			this.store_path_index = store_path;
		}
	}
	
/**
* @return the storage socket
*/
	public Socket getSocket()
	{
		return this.storageSocket;
	}
	
/**
* @return the store path index on the storage server
*/
	public int getStorePathIndex()
	{
		return this.store_path_index;
	}
	
	public OutputStream getOutputStream() throws IOException
	{
		return this.storageSocket.getOutputStream();
	}
	
	public InputStream getInputStream() throws IOException
	{
		return this.storageSocket.getInputStream();
	}
	
	public void close() throws IOException
	{
		if (this.storageSocket != null)
		{
			ProtoCommon.closeSocket(this.storageSocket);
			this.storageSocket = null;
		}
	}
}
