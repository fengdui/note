/**
* Copyright (C) 2008 Happy Fish / YuQing
*
* FastDFS Java Client may be copied only under the terms of the GNU Lesser
* General Public License (LGPL).
* Please visit the FastDFS Home Page http://www.csource.org/ for more detail.
*/

package com.zheyue.encrypt.filesystem.fastdfs;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

/**
* Tracker client
* @author Happy Fish / YuQing
* @version Version 1.10
*/
public class TrackerClient
{
	protected TrackerGroup tracker_group;
  protected byte errno;
  
	public TrackerClient()
	{
		this.tracker_group = ClientGlobal.g_tracker_group;
	}

	public TrackerClient(TrackerGroup tracker_group)
	{
		this.tracker_group = tracker_group;
	}
	
/**
* @return the error code of last call
*/
	public byte getErrorCode()
	{
		return this.errno;
	}
	
	/**
	* get a connection to tracker server
	* @param
	* @return tracker server Socket object, return null if fail
	*/
	public TrackerServer getConnection() throws IOException
	{
		return this.tracker_group.getConnection();
	}
	
	/**
	* query storage server to upload file
	* @param trackerSocket the Socket object of tracker server
	* @return storage server Socket object, return null if fail
	*/
	public StorageServer getStoreStorage(Socket trackerSocket) throws IOException
	{
		final String group_name = null;
		return this.getStoreStorage(trackerSocket, group_name);
	}
	
	/**
	* query storage server to upload file
	* @param trackerSocket the Socket object of tracker server
	* @param group_name the group name to upload file to, can be empty
	* @return storage server Socket object, return null if fail
	*/
	public StorageServer getStoreStorage(Socket trackerSocket, String group_name) throws IOException
	{
		OutputStream out = trackerSocket.getOutputStream();
		byte[] header;
		String ip_addr;
		int port;
		byte cmd;
		int out_len;
		boolean bNewConnection;
		byte store_path;
		TrackerServer trackerServer;
				
		if (trackerSocket == null)
		{
			trackerServer = getConnection();
			if (trackerServer == null)
			{
				return null;
			}
			trackerSocket = trackerServer.getSocket();
			bNewConnection = true;
		}
		else
		{
			bNewConnection = false;
		}
		
		try
		{
			if (group_name == null || group_name.length() == 0)
			{
				cmd = ProtoCommon.TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITHOUT_GROUP;
				out_len = 0;
			}
			else
			{
				cmd = ProtoCommon.TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITH_GROUP;
				out_len = ProtoCommon.FDFS_GROUP_NAME_MAX_LEN;
			}
			header = ProtoCommon.packHeader(cmd, out_len, (byte)0);
			out.write(header);

			if (group_name != null && group_name.length() > 0)
			{
				byte[] bGroupName;
				byte[] bs;
				int group_len;
				
				bs = group_name.getBytes(ClientGlobal.g_charset);
				bGroupName = new byte[ProtoCommon.FDFS_GROUP_NAME_MAX_LEN];
				
				if (bs.length <= ProtoCommon.FDFS_GROUP_NAME_MAX_LEN)
				{
					group_len = bs.length;
				}
				else
				{
					group_len = ProtoCommon.FDFS_GROUP_NAME_MAX_LEN;
				}
				Arrays.fill(bGroupName, (byte)0);
				System.arraycopy(bs, 0, bGroupName, 0, group_len);
				out.write(bGroupName);
			}
	
			ProtoCommon.RecvPackageInfo pkgInfo = ProtoCommon.recvPackage(trackerSocket.getInputStream(), 
	                                     ProtoCommon.TRACKER_PROTO_CMD_SERVICE_RESP, 
	                                     ProtoCommon.TRACKER_QUERY_STORAGE_STORE_BODY_LEN);
			this.errno = pkgInfo.errno;
			if (pkgInfo.errno != 0)
			{
				return null;
			}
			
			ip_addr = new String(pkgInfo.body, ProtoCommon.FDFS_GROUP_NAME_MAX_LEN, ProtoCommon.FDFS_IPADDR_SIZE-1).trim();
	
			port = (int)ProtoCommon.buff2long(pkgInfo.body, ProtoCommon.FDFS_GROUP_NAME_MAX_LEN
	                        + ProtoCommon.FDFS_IPADDR_SIZE-1);
			store_path = pkgInfo.body[ProtoCommon.TRACKER_QUERY_STORAGE_STORE_BODY_LEN - 1];
			
			return new StorageServer(ClientGlobal.getSocket(ip_addr, port), store_path);
		}
		finally
		{
			if (bNewConnection)
			{
				ProtoCommon.closeSocket(trackerSocket);
			}
		}
	}

	/**
	* query storage server to download file
	* @param trackerSocket tracker server
	*	@param group_name the group name of storage server
	* @param filename filename on storage server
	* @return storage server Socket object, return null if fail
	*/
	public StorageServer getFetchStorage(Socket trackerSocket, 
			String group_name, String filename) throws IOException
	{
		ServerInfo[] servers = this.getStorages(trackerSocket, ProtoCommon.TRACKER_PROTO_CMD_SERVICE_QUERY_FETCH_ONE, 
				group_name, filename);
		if (servers == null)
		{
				return null;
		}
		else
		{
			return new StorageServer(servers[0].connect(), 0);
		}
	}

	/**
	* query storage server to update file (delete file or set meta data)
	* @param trackerSocket tracker server
	*	@param group_name the group name of storage server
	* @param filename filename on storage server
	* @return storage server Socket object, return null if fail
	*/
	public StorageServer getUpdateStorage(Socket trackerSocket, 
			String group_name, String filename) throws IOException
	{
		ServerInfo[] servers = this.getStorages(trackerSocket, ProtoCommon.TRACKER_PROTO_CMD_SERVICE_QUERY_UPDATE, 
				group_name, filename);
		if (servers == null)
		{
				return null;
		}
		else
		{
			return new StorageServer(servers[0].connect(), 0);
		} 
	}

	/**
	* get storage servers to download file
	* @param trackerSocket tracker server
	*	@param group_name the group name of storage server
	* @param filename filename on storage server
	* @return storage servers, return null if fail
	*/
	public ServerInfo[] getFetchStorages(Socket trackerSocket, 
			String group_name, String filename) throws IOException
	{
		return this.getStorages(trackerSocket, ProtoCommon.TRACKER_PROTO_CMD_SERVICE_QUERY_FETCH_ALL, 
				group_name, filename);
	}
	
	/**
	* query storage server to download file
	* @param trackerSocket tracker server
	* @cmd command code, ProtoCommon.TRACKER_PROTO_CMD_SERVICE_QUERY_FETCH_ONE or 
	                     ProtoCommon.TRACKER_PROTO_CMD_SERVICE_QUERY_UPDATE
	*	@param group_name the group name of storage server
	* @param filename filename on storage server
	* @return storage server Socket object, return null if fail
	*/
	protected ServerInfo[] getStorages(Socket trackerSocket, 
			byte cmd, String group_name, String filename) throws IOException
	{
		OutputStream out = trackerSocket.getOutputStream();
		byte[] header;
		byte[] bFileName;
		byte[] bGroupName;
		byte[] bs;
		int len;
		String ip_addr;
		int port;
		
		bs = group_name.getBytes(ClientGlobal.g_charset);
		bGroupName = new byte[ProtoCommon.FDFS_GROUP_NAME_MAX_LEN];
		bFileName = filename.getBytes(ClientGlobal.g_charset);
		
		if (bs.length <= ProtoCommon.FDFS_GROUP_NAME_MAX_LEN)
		{
			len = bs.length;
		}
		else
		{
			len = ProtoCommon.FDFS_GROUP_NAME_MAX_LEN;
		}
		Arrays.fill(bGroupName, (byte)0);
		System.arraycopy(bs, 0, bGroupName, 0, len);
		
		header = ProtoCommon.packHeader(cmd, ProtoCommon.FDFS_GROUP_NAME_MAX_LEN + bFileName.length, (byte)0);
		byte[] wholePkg = new byte[header.length + bGroupName.length + bFileName.length];
		System.arraycopy(header, 0, wholePkg, 0, header.length);
		System.arraycopy(bGroupName, 0, wholePkg, header.length, bGroupName.length);
		System.arraycopy(bFileName, 0, wholePkg, header.length + bGroupName.length, bFileName.length);
		out.write(wholePkg);
		
		ProtoCommon.RecvPackageInfo pkgInfo = ProtoCommon.recvPackage(trackerSocket.getInputStream(), 
                                     ProtoCommon.TRACKER_PROTO_CMD_SERVICE_RESP, -1);
		this.errno = pkgInfo.errno;
		if (pkgInfo.errno != 0)
		{
			return null;
		}
		
		if (pkgInfo.body.length < ProtoCommon.TRACKER_QUERY_STORAGE_FETCH_BODY_LEN)
		{
			throw new IOException("Invalid body length: " + pkgInfo.body.length);
		}
		
		if ((pkgInfo.body.length - ProtoCommon.TRACKER_QUERY_STORAGE_FETCH_BODY_LEN) % (ProtoCommon.FDFS_IPADDR_SIZE - 1) != 0)
		{
			throw new IOException("Invalid body length: " + pkgInfo.body.length);
		}
		
		int server_count = 1 + (pkgInfo.body.length - ProtoCommon.TRACKER_QUERY_STORAGE_FETCH_BODY_LEN) / (ProtoCommon.FDFS_IPADDR_SIZE - 1);
		
		ip_addr = new String(pkgInfo.body, ProtoCommon.FDFS_GROUP_NAME_MAX_LEN, ProtoCommon.FDFS_IPADDR_SIZE-1).trim();
		int offset = ProtoCommon.FDFS_GROUP_NAME_MAX_LEN + ProtoCommon.FDFS_IPADDR_SIZE - 1;
		
		port = (int)ProtoCommon.buff2long(pkgInfo.body, offset);
    offset += ProtoCommon.TRACKER_PROTO_PKG_LEN_SIZE;
    
    ServerInfo[] servers = new ServerInfo[server_count];
    servers[0] = new ServerInfo(ip_addr, port);
    for (int i=1; i<server_count; i++)
    {
    	servers[i] = new ServerInfo(new String(pkgInfo.body, offset, ProtoCommon.FDFS_IPADDR_SIZE-1).trim(), port);
    	offset += ProtoCommon.FDFS_IPADDR_SIZE - 1;
    }

		return servers;
	}
	
	/**
	* query storage server to download file
	* @param trackerSocket tracker server
	*	@param the file id(including group name and filename)
	* @return storage server Socket object, return null if fail
	*/
	public StorageServer getFetchStorage1(Socket trackerSocket, String file_id) throws IOException
	{
		String[] parts = new String[2];
		this.errno = StorageClient1.split_file_id(file_id, parts);
		if (this.errno != 0)
		{
			return null;
		}
		
		return this.getFetchStorage(trackerSocket, parts[0], parts[1]);
	}
	
	/**
	* get storage servers to download file
	* @param trackerSocket tracker server
	*	@param the file id(including group name and filename)
	* @return storage servers, return null if fail
	*/
	public ServerInfo[] getFetchStorages1(Socket trackerSocket, String file_id) throws IOException
	{
		String[] parts = new String[2];
		this.errno = StorageClient1.split_file_id(file_id, parts);
		if (this.errno != 0)
		{
			return null;
		}
		
		return this.getFetchStorages(trackerSocket, parts[0], parts[1]);
	}
}
