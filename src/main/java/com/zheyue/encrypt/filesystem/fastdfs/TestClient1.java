/**
* Copyright (C) 2008 Happy Fish / YuQing
*
* FastDFS Java Client may be copied only under the terms of the GNU Lesser
* General Public License (LGPL).
* Please visit the FastDFS Home Page http://www.csource.org/ for more detail.
**/

package com.zheyue.encrypt.filesystem.fastdfs;


import com.zheyue.encrypt.filesystem.common.NameValuePair;

import java.io.File;
import java.net.InetSocketAddress;

/**
* client test
* @author Happy Fish / YuQing
* @version Version 1.10
*/
public class TestClient1
{
	private TestClient1()
	{
	}
	
	/**
	* entry point
	* @param args comand arguments
	*     <ul><li>args[0]: config filename</li></ul>
	*     <ul><li>args[1]: local filename to upload</li></ul>
	*/
  public static void main(String args[])
  {
  	if (args.length < 2)
  	{
  		System.out.println("Error: Must have 2 parameters, one is config filename, "
  		                 + "the other is the local filename to upload");
  		return;
  	}
  	
  	System.out.println("java.version=" + System.getProperty("java.version"));
  	  
  	String conf_filename = args[0];
  	String local_filename = args[1];
  	String group_name;
  	
  	try
  	{
  		ClientGlobal.init(conf_filename);
  		System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
  		System.out.println("charset=" + ClientGlobal.g_charset);
  		
  		String file_id;
  		
  		TrackerClient tracker = new TrackerClient();
  		TrackerServer trackerServer = tracker.getConnection();
  		
  		StorageServer storageServer = null;
  		/*
  		storageServer = tracker.getStoreStorage(trackerServer.getSocket());
  		if (storageServer == null)
  		{
  			System.out.println("getStoreStorage fail, error code: " + tracker.getErrorCode());
  			return;
  		}
  		*/
  		StorageClient1 client = new StorageClient1(trackerServer.getSocket(), storageServer);
  		byte[] file_buff;
  		NameValuePair[] meta_list;
  		int errno;
  		
  		meta_list = new NameValuePair[4];
  		meta_list[0] = new NameValuePair("width", "800");
  		meta_list[1] = new NameValuePair("heigth", "600");
  		meta_list[2] = new NameValuePair("bgcolor", "#FFFFFF");
  		meta_list[3] = new NameValuePair("author", "Mike");
  		
  		file_buff = "this is a test".getBytes(ClientGlobal.g_charset);
  		System.out.println("file length: " + file_buff.length);
  		
  		file_id = client.upload_file1(file_buff, "txt", meta_list);
  		/*
  		group_name = "group1";
  		file_id = client.upload_file1(group_name, file_buff, "txt", meta_list);
  		*/
  		if (file_id == null)
  		{
  			System.err.println("upload file fail, error code: " + client.getErrorCode());
  			return;
  		}
  		else
  		{
  			System.err.println("file_id: " + file_id);
				
				ServerInfo[] servers = tracker.getFetchStorages1(trackerServer.getSocket(), file_id);
				if (servers == null)
				{
					System.err.println("get storage servers fail, error code: " + tracker.getErrorCode());
				}
				else
				{
					System.err.println("storage servers count: " + servers.length);
					for (int k=0; k<servers.length; k++)
					{
						System.err.println((k+1) + ". " + servers[k].getIpAddr() + ":" + servers[k].getPort());
					}
					System.err.println("");
				}
				
	  		meta_list = new NameValuePair[4];	  		
	  		meta_list[0] = new NameValuePair("width", "1024");
	  		meta_list[1] = new NameValuePair("heigth", "768");
	  		meta_list[2] = new NameValuePair("bgcolor", "#000000");
	  		meta_list[3] = new NameValuePair("title", "Untitle");
				
  			if ((errno=client.set_metadata1(file_id, meta_list, ProtoCommon.STORAGE_SET_METADATA_FLAG_MERGE)) == 0)
  			{
  				System.err.println("set_metadata success");
  			}
  			else
  			{
  				System.err.println("set_metadata fail, error no: " + errno);
  			}
			
  			meta_list = client.get_metadata1(file_id);
  			if (meta_list != null)
  			{
		  		for (int i=0; i<meta_list.length; i++)
		  		{
		  			System.out.println(meta_list[i].getName() + " " + meta_list[i].getValue());
		  		}
  			}
  			
  			//Thread.sleep(30000);
  			
  			file_buff = client.download_file1(file_id);
  			if (file_buff != null)
  			{
  				System.out.println("file length:" + file_buff.length);
  				System.out.println((new String(file_buff)));
  			}
  			
  			//Thread.sleep(10000);
  			if ((errno=client.delete_file1(file_id)) == 0)
  			{
  				System.err.println("Delete file success");
  			}
  			else
  			{
  				System.err.println("Delete file fail, error no: " + errno);
  			}
  		}
  		
  		if ((file_id=client.upload_file1(local_filename, null, meta_list)) != null)
  		{
  			int ts;
  			String token;
  			String file_url;
  			InetSocketAddress inetSockAddr;
  			
  			System.err.println("file_id: " + file_id);
  			
  			inetSockAddr = trackerServer.getInetSocketAddress();
  			file_url = "http://" + inetSockAddr.getAddress().getHostAddress();
  			if (ClientGlobal.g_tracker_http_port != 80)
  			{
  				 file_url += ":" + ClientGlobal.g_tracker_http_port;
  			}
  			file_url += "/" + file_id;
  			if (ClientGlobal.g_anti_steal_token)
  			{
	  			ts = (int)(System.currentTimeMillis() / 1000);
	  			token = ProtoCommon.getToken(file_id, ts, ClientGlobal.g_secret_key);
	  			file_url += "?token=" + token + "&ts=" + ts;
  			}
  			System.err.println("file url: " + file_url);
  			
  			errno = client.download_file1(file_id, 0, 0, "c:\\" + file_id.replaceAll("/", "_"));
  			if (errno == 0)
  			{
  				System.err.println("Download file success");
  			}
  			else
  			{
  				System.err.println("Download file fail, error no: " + errno);
  			}
  			
  			errno = client.download_file1(file_id, new DowloadFileWriter("c:\\" + file_id.replaceAll("/", "-")));
  			if (errno == 0)
  			{
  				System.err.println("Download file success");
  			}
  			else
  			{
  				System.err.println("Download file fail, error no: " + errno);
  			}
  		}

			File f;
			String file_ext_name;
			f = new File(local_filename);
			int nPos = local_filename.lastIndexOf('.');
			if (nPos > 0 && local_filename.length() - nPos <= ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN + 1)
			{
				file_ext_name = local_filename.substring(nPos+1);
			}
			else
			{
				file_ext_name = null;
			}
			
  		file_id = client.upload_file1(null, f.length(), new UploadLocalFileSender(local_filename), file_ext_name, meta_list);
	    if (file_id != null)
	    {
	    	System.out.println("file id: " + file_id);
	    }
	    else
	    {
	    	System.err.println("Upload file fail, error no: " + errno);
	    }
	    
  		/*
  		storageServer.close();
  		
  		storageServer = tracker.getFetchStorage1(trackerServer.getSocket(), file_id);
  		if (storageServer == null)
  		{
  			System.out.println("getFetchStorage fail, errno code: " + tracker.getErrorCode());
  			return;
  		}
  		*/
  		
  		//storageServer.close();
  		trackerServer.close();
  	}
  	catch(Exception ex)
  	{
  		ex.printStackTrace();
  	}
  }
}
