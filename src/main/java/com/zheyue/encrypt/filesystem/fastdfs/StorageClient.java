/**
* Copyright (C) 2008 Happy Fish / YuQing
*
* FastDFS Java Client may be copied only under the terms of the GNU Lesser
* General Public License (LGPL).
* Please visit the FastDFS Home Page http://www.csource.org/ for more detail.
*/

package com.zheyue.encrypt.filesystem.fastdfs;

import com.zheyue.encrypt.filesystem.common.NameValuePair;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

/**
* Storage client for 2 fields file id: group name and filename
* @author Happy Fish / YuQing
* @version Version 1.9
*/
public class StorageClient
{
	protected Socket trackerSocket;
	protected StorageServer storageServer;
	protected byte errno;
	
/**
* @param trackerSocket Socket of tracker server
* @param storageServer Socket of storage server, can be null
*/
	public StorageClient(Socket trackerSocket, StorageServer storageServer)
	{
		this.trackerSocket = trackerSocket;
		this.storageServer = storageServer;
	}

/**
* @return the error code of last call
*/
	public byte getErrorCode()
	{
		return this.errno;
	}

	/**
	* upload file to storage server (by file name)
	* @param local_filename local filename to upload
	* @param file_ext_name file ext name, do not include dot(.), null to extract ext name from the local filename
	* @param meta_list meta info array
	* @return  2 elements string array if success:<br>
	*           <ul><li>results[0]: the group name to store the file </li></ul>
	*           <ul><li>results[1]: the new created filename</li></ul>
	*         return null if fail
	*/
	public String[] upload_file(String local_filename, String file_ext_name, 
	       NameValuePair[] meta_list) throws IOException, Exception
	{
		final String group_name = null;
		return this.upload_file(group_name, local_filename, file_ext_name, meta_list);
	}
	
	/**
	* upload file to storage server (by file name)
	* @param group_name the group name to upload file to, can be empty
	* @param local_filename local filename to upload
	* @param file_ext_name file ext name, do not include dot(.), null to extract ext name from the local filename
	* @param meta_list meta info array
	* @return  2 elements string array if success:<br>
	*           <ul><li>results[0]: the group name to store the file </li></ul>
	*           <ul><li>results[1]: the new created filename</li></ul>
	*         return null if fail
	*/
	public String[] upload_file(String group_name, String local_filename, String file_ext_name, 
	       NameValuePair[] meta_list) throws IOException, Exception
	{
		if (file_ext_name == null)
		{
			int nPos = local_filename.lastIndexOf('.');
			if (nPos > 0 && local_filename.length() - nPos <= ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN + 1)
			{
				file_ext_name = local_filename.substring(nPos+1);
			}
		}
		
		return this.do_upload_file(group_name, local_filename, null, file_ext_name, meta_list);
	}

	/**
	* upload file to storage server (by file buff)
	* @param file_buff file content/buff
	* @param file_ext_name file ext name, do not include dot(.)
	*	@param meta_list meta info array
	* @return  2 elements string array if success:<br>
	*           <ul><li>results[0]: the group name to store the file</li></ul>
	*           <ul><li>results[1]: the new created filename</li></ul>
	*         return null if fail
	*/
	public String[] upload_file(byte[] file_buff,	String file_ext_name, 
	       NameValuePair[] meta_list) throws IOException, Exception
	{
		final String group_name = null;
		return this.do_upload_file(group_name, null, file_buff, file_ext_name, meta_list);
	}
	
	/**
	* upload file to storage server (by file buff)
	* @param group_name the group name to upload file to, can be empty
	* @param file_buff file content/buff
	* @param file_ext_name file ext name, do not include dot(.)
	*	@param meta_list meta info array
	* @return  2 elements string array if success:<br>
	*           <ul><li>results[0]: the group name to store the file</li></ul>
	*           <ul><li>results[1]: the new created filename</li></ul>
	*         return null if fail
	*/
	public String[] upload_file(String group_name, byte[] file_buff,	String file_ext_name, 
	       NameValuePair[] meta_list) throws IOException, Exception
	{
		return this.do_upload_file(group_name, null, file_buff, file_ext_name, meta_list);
	}
	
	/**
	* upload file to storage server (by callback)
	* @param group_name the group name to upload file to, can be empty
	* @param file_size the file size
	* @param callback the write data callback object
	* @param file_ext_name file ext name, do not include dot(.)
	*	@param meta_list meta info array
	* @return  2 elements string array if success:<br>
	*           <ul><li>results[0]: the group name to store the file</li></ul>
	*           <ul><li>results[1]: the new created filename</li></ul>
	*         return null if fail
	*/
	public String[] upload_file(String group_name, long file_size, 
	       UploadCallback callback, String file_ext_name, 
	       NameValuePair[] meta_list) throws IOException, Exception
	{
		byte[] header;
		byte[] ext_name_bs;
		String new_group_name;
		String remote_filename;
		boolean bNewConnection;
		
		bNewConnection = this.newWritableStorageConnection(group_name);
		try
		{
			byte[] meta_buff;
			byte[] sizeBytes;
			byte[] hexLenBytes;
			long fileBytes;
			FileInputStream fis;
			
			if (meta_list == null)
			{
				meta_buff = new byte[0];
			}
			else
			{
				meta_buff = ProtoCommon.pack_metadata(meta_list).getBytes(ClientGlobal.g_charset);
			}
			
			sizeBytes = new byte[1 + 2 * ProtoCommon.TRACKER_PROTO_PKG_LEN_SIZE];
			
			ext_name_bs = new byte[ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN];
			Arrays.fill(ext_name_bs, (byte)0);
			if (file_ext_name != null && file_ext_name.length() > 0)
			{
				byte[] bs = file_ext_name.getBytes();
				int ext_name_len = bs.length;
				if (ext_name_len > ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN)
				{
					ext_name_len = ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN;
				}
				System.arraycopy(bs, 0, ext_name_bs, 0, ext_name_len);
			}
			
			Arrays.fill(sizeBytes, (byte)0);
			sizeBytes[0] = (byte)this.storageServer.getStorePathIndex();
			hexLenBytes = ProtoCommon.long2buff(meta_buff.length);
			System.arraycopy(hexLenBytes, 0, sizeBytes, 1, hexLenBytes.length);
			hexLenBytes = ProtoCommon.long2buff(file_size);
			System.arraycopy(hexLenBytes, 0, sizeBytes, 1 + ProtoCommon.TRACKER_PROTO_PKG_LEN_SIZE, hexLenBytes.length);
			
			header = ProtoCommon.packHeader(ProtoCommon.STORAGE_PROTO_CMD_UPLOAD_FILE, 
										1 + 2 * ProtoCommon.TRACKER_PROTO_PKG_LEN_SIZE
										+ ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN + meta_buff.length
										+ file_size, (byte)0);
			OutputStream out = this.storageServer.getOutputStream();
			byte[] wholePkg = new byte[header.length + sizeBytes.length + ext_name_bs.length + meta_buff.length];
			System.arraycopy(header, 0, wholePkg, 0, header.length);
			System.arraycopy(sizeBytes, 0, wholePkg, header.length, sizeBytes.length);
			System.arraycopy(ext_name_bs, 0, wholePkg, header.length+sizeBytes.length, ext_name_bs.length);
			if (meta_buff.length > 0)
			{
				System.arraycopy(meta_buff, 0, wholePkg, header.length+sizeBytes.length+ext_name_bs.length, meta_buff.length);
			}
			out.write(wholePkg);
			
			if ((this.errno=(byte)callback.send(out)) != 0)
			{
				return null;
			}
			
			ProtoCommon.RecvPackageInfo pkgInfo = ProtoCommon.recvPackage(this.storageServer.getInputStream(), 
	                                     ProtoCommon.STORAGE_PROTO_CMD_RESP, -1);
			this.errno = pkgInfo.errno;
			if (pkgInfo.errno != 0)
			{
				return null;
			}
			
			if (pkgInfo.body.length <= ProtoCommon.FDFS_GROUP_NAME_MAX_LEN)
			{
				throw new Exception("body length: " + pkgInfo.body.length + " <= " + ProtoCommon.FDFS_GROUP_NAME_MAX_LEN);
			}
	
			new_group_name = new String(pkgInfo.body, 0, ProtoCommon.FDFS_GROUP_NAME_MAX_LEN).trim();
			remote_filename = new String(pkgInfo.body, ProtoCommon.FDFS_GROUP_NAME_MAX_LEN, pkgInfo.body.length - ProtoCommon.FDFS_GROUP_NAME_MAX_LEN);
			String[] results = new String[2];
			results[0] = new_group_name;
			results[1] = remote_filename;
			return results;
		}
		finally
		{
			if (bNewConnection)
			{
				this.storageServer.close();
				this.storageServer = null;
			}
		}
	}
	
	/**
	* upload file to storage server (by file buff)
	* @param group_name the group name to upload file to, can be empty
	* @param local_filename filename to upload
	* @param file_buff file content/buff
  * @param file_ext_name file ext name, do not include dot(.)
	*	@param meta_list meta info array
	* @return  2 elements string array if success:<br>
	*          <ul><li> results[0]: the group name to store the file</li></ul>
	*          <ul><li> results[1]: the new created filename</li></ul> 
	*         return null if fail
	*/
	protected String[] do_upload_file(String group_name, String local_filename, byte[] file_buff,	
           String file_ext_name, NameValuePair[] meta_list) throws IOException, Exception
	{
		byte[] header;
		byte[] ext_name_bs;
		String new_group_name;
		String remote_filename;
		boolean bNewConnection;
		
		bNewConnection = this.newWritableStorageConnection(group_name);
		try
		{
			byte[] meta_buff;
			byte[] sizeBytes;
			byte[] hexLenBytes;
			long fileBytes;
			FileInputStream fis;
			
			if (meta_list == null)
			{
				meta_buff = new byte[0];
			}
			else
			{
				meta_buff = ProtoCommon.pack_metadata(meta_list).getBytes(ClientGlobal.g_charset);
			}
			
			sizeBytes = new byte[1 + 2 * ProtoCommon.TRACKER_PROTO_PKG_LEN_SIZE];

			if (local_filename != null && local_filename.length() > 0)
			{
				File f;
				
				f = new File(local_filename);
				fileBytes = f.length();
				fis = new FileInputStream(f);
			}
			else
			{
				fileBytes = file_buff.length;
				fis = null;
			}
			
			ext_name_bs = new byte[ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN];
			Arrays.fill(ext_name_bs, (byte)0);
			if (file_ext_name != null && file_ext_name.length() > 0)
			{
				byte[] bs = file_ext_name.getBytes();
				int ext_name_len = bs.length;
				if (ext_name_len > ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN)
				{
					ext_name_len = ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN;
				}
				System.arraycopy(bs, 0, ext_name_bs, 0, ext_name_len);
			}
			
			Arrays.fill(sizeBytes, (byte)0);
			sizeBytes[0] = (byte)this.storageServer.getStorePathIndex();
			hexLenBytes = ProtoCommon.long2buff(meta_buff.length);
			System.arraycopy(hexLenBytes, 0, sizeBytes, 1, hexLenBytes.length);
			hexLenBytes = ProtoCommon.long2buff(fileBytes);
			System.arraycopy(hexLenBytes, 0, sizeBytes, 1 + ProtoCommon.TRACKER_PROTO_PKG_LEN_SIZE, hexLenBytes.length);
			
			header = ProtoCommon.packHeader(ProtoCommon.STORAGE_PROTO_CMD_UPLOAD_FILE, 
										1 + 2 * ProtoCommon.TRACKER_PROTO_PKG_LEN_SIZE
										+ ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN + meta_buff.length
										+ fileBytes, (byte)0);
			OutputStream out = this.storageServer.getOutputStream();
			byte[] wholePkg = new byte[header.length + sizeBytes.length + ext_name_bs.length + meta_buff.length];
			System.arraycopy(header, 0, wholePkg, 0, header.length);
			System.arraycopy(sizeBytes, 0, wholePkg, header.length, sizeBytes.length);
			System.arraycopy(ext_name_bs, 0, wholePkg, header.length+sizeBytes.length, ext_name_bs.length);
			if (meta_buff.length > 0)
			{
				System.arraycopy(meta_buff, 0, wholePkg, header.length+sizeBytes.length+ext_name_bs.length, meta_buff.length);
			}
			out.write(wholePkg);
			
			if (fis != null)
			{
				int readBytes;
				byte[] buff = new byte[256 * 1024];
				try
				{
					while ((readBytes=fis.read(buff)) >= 0)
					{
						if (readBytes == 0)
						{
							continue;
						}
						
						out.write(buff, 0, readBytes);
					}
				}
				finally
				{
					fis.close();
				}
			}
			else
			{
				out.write(file_buff);
			}
			
			ProtoCommon.RecvPackageInfo pkgInfo = ProtoCommon.recvPackage(this.storageServer.getInputStream(), 
	                                     ProtoCommon.STORAGE_PROTO_CMD_RESP, -1);
			this.errno = pkgInfo.errno;
			if (pkgInfo.errno != 0)
			{
				return null;
			}
			
			if (pkgInfo.body.length <= ProtoCommon.FDFS_GROUP_NAME_MAX_LEN)
			{
				throw new Exception("body length: " + pkgInfo.body.length + " <= " + ProtoCommon.FDFS_GROUP_NAME_MAX_LEN);
			}
	
			new_group_name = new String(pkgInfo.body, 0, ProtoCommon.FDFS_GROUP_NAME_MAX_LEN).trim();
			remote_filename = new String(pkgInfo.body, ProtoCommon.FDFS_GROUP_NAME_MAX_LEN, pkgInfo.body.length - ProtoCommon.FDFS_GROUP_NAME_MAX_LEN);
			String[] results = new String[2];
			results[0] = new_group_name;
			results[1] = remote_filename;
			return results;
		}
		finally
		{
			if (bNewConnection)
			{
				this.storageServer.close();
				this.storageServer = null;
			}
		}
	}
	
	/**
	* delete file from storage server
	* @param group_name the group name of storage server
	*	@param remote_filename filename on storage server
	* @return 0 for success, none zero for fail (error code)
	*/
	public int delete_file(String group_name, String remote_filename) throws IOException, Exception
	{
		boolean bNewConnection;
		
		bNewConnection = this.newUpdatableStorageConnection(group_name, remote_filename);
		try
		{
			ProtoCommon.RecvPackageInfo pkgInfo;
			
			this.send_package(ProtoCommon.STORAGE_PROTO_CMD_DELETE_FILE, group_name, remote_filename);
			pkgInfo = ProtoCommon.recvPackage(this.storageServer.getInputStream(), 
	                                     ProtoCommon.STORAGE_PROTO_CMD_RESP, 0);
			
			this.errno = pkgInfo.errno;
			return pkgInfo.errno;
		}
		finally
		{
			if (bNewConnection)
			{
				this.storageServer.close();
				this.storageServer = null;
			}
		}
	}
	
	/**
	* download file from storage server
	* @param group_name the group name of storage server
	*	@param remote_filename filename on storage server
	* @return file content/buff, return null if fail
	*/
	public byte[] download_file(String group_name, String remote_filename) throws IOException, Exception
	{
		final long file_offset = 0;
		final long download_bytes = 0;
		
		return this.download_file(group_name, remote_filename, file_offset, download_bytes);
	}
	
	/**
	* download file from storage server
	* @param group_name the group name of storage server
	*	@param remote_filename filename on storage server
	* @param file_offset the start offset of the file
	* @param download_bytes download bytes, 0 for remain bytes from offset
	* @return file content/buff, return null if fail
	*/
	public byte[] download_file(String group_name, String remote_filename, long file_offset, long download_bytes) throws IOException, Exception
	{
		boolean bNewConnection;
		
		bNewConnection = this.newReadableStorageConnection(group_name, remote_filename);
		try
		{
			ProtoCommon.RecvPackageInfo pkgInfo;
			
			this.send_download_package(group_name, remote_filename, file_offset, download_bytes);
			pkgInfo = ProtoCommon.recvPackage(this.storageServer.getInputStream(), 
	                                     ProtoCommon.STORAGE_PROTO_CMD_RESP, -1);
			
			this.errno = pkgInfo.errno;
			if (pkgInfo.errno != 0)
			{
				return null;
			}
			
			return pkgInfo.body;
		}
		finally
		{
			if (bNewConnection)
			{
				this.storageServer.close();
				this.storageServer = null;
			}
		}
	}

	/**
	* download file from storage server
	* @param group_name the group name of storage server
	*	@param remote_filename filename on storage server
	* @param local_filename  filename on local
	* @return 0 success, return none zero errno if fail
	*/
	public int download_file(String group_name, String remote_filename, 
	                  String local_filename) throws IOException, Exception
	{
		final long file_offset = 0;
		final long download_bytes = 0;
		return this.download_file(group_name, remote_filename, 
	                  file_offset, download_bytes, local_filename);
	}
	
	/**
	* download file from storage server
	* @param group_name the group name of storage server
	*	@param remote_filename filename on storage server
  * @param file_offset the start offset of the file
	* @param download_bytes download bytes, 0 for remain bytes from offset
	* @param local_filename  filename on local
	* @return 0 success, return none zero errno if fail
	*/
	public int download_file(String group_name, String remote_filename, 
	                  long file_offset, long download_bytes, 
	                  String local_filename) throws IOException, Exception
	{
		boolean bNewConnection;
		
		bNewConnection = this.newReadableStorageConnection(group_name, remote_filename);
		try
		{
			ProtoCommon.RecvHeaderInfo header;
			FileOutputStream out = new FileOutputStream(local_filename);
			try
			{
				this.send_download_package(group_name, remote_filename, file_offset, download_bytes);
				
				InputStream in = this.storageServer.getInputStream();
				header = ProtoCommon.recvHeader(in, ProtoCommon.STORAGE_PROTO_CMD_RESP, -1);
				this.errno = header.errno;
				if (header.errno != 0)
				{
					return header.errno;
				}
				
				byte[] buff = new byte[256 * 1024];
				long remainBytes = header.body_len;
				int bytes;
				
				//System.out.println("expect_body_len=" + header.body_len);
				
				while (remainBytes > 0)
				{
					if ((bytes=in.read(buff, 0, remainBytes > buff.length ? buff.length : (int)remainBytes)) < 0)
					{
						throw new IOException("recv package size " + (header.body_len - remainBytes) + " != " + header.body_len);
					}
					
					out.write(buff, 0, bytes);
					remainBytes -= bytes;
					
					//System.out.println("totalBytes=" + (header.body_len - remainBytes));
				}
				
				return 0;
			}
			finally
			{
				out.close();
			}
		}
		finally
		{
			if (bNewConnection)
			{
				this.storageServer.close();
				this.storageServer = null;
			}
		}
	}
	
	/**
	* download file from storage server
	* @param group_name the group name of storage server
	*	@param remote_filename filename on storage server
	* @param callback call callback.recv() when data arrive
	* @return 0 success, return none zero errno if fail
	*/
	public int download_file(String group_name, String remote_filename, 
	                  DowloadCallback callback) throws IOException, Exception
	{
		final long file_offset = 0;
		final long download_bytes = 0;
		return this.download_file(group_name, remote_filename, 
	                  file_offset, download_bytes, callback);
	}
	
	/**
	* download file from storage server
	* @param group_name the group name of storage server
	*	@param remote_filename filename on storage server
  * @param file_offset the start offset of the file
	* @param download_bytes download bytes, 0 for remain bytes from offset
	* @param callback call callback.recv() when data arrive
	* @return 0 success, return none zero errno if fail
	*/
	public int download_file(String group_name, String remote_filename, 
	                  long file_offset, long download_bytes, 
	                  DowloadCallback callback) throws IOException, Exception
	{
		boolean bNewConnection;
		int result;
		
		bNewConnection = this.newReadableStorageConnection(group_name, remote_filename);
		try
		{
			  ProtoCommon.RecvHeaderInfo header;
				this.send_download_package(group_name, remote_filename, file_offset, download_bytes);
				
				InputStream in = this.storageServer.getInputStream();
				header = ProtoCommon.recvHeader(in, ProtoCommon.STORAGE_PROTO_CMD_RESP, -1);
				this.errno = header.errno;
				if (header.errno != 0)
				{
					return header.errno;
				}
				
				byte[] buff = new byte[2 * 1024];
				long remainBytes = header.body_len;
				int bytes;
				
				//System.out.println("expect_body_len=" + header.body_len);
				
				while (remainBytes > 0)
				{
					if ((bytes=in.read(buff, 0, remainBytes > buff.length ? buff.length : (int)remainBytes)) < 0)
					{
						throw new IOException("recv package size " + (header.body_len - remainBytes) + " != " + header.body_len);
					}
					
					if ((result=callback.recv(header.body_len, buff, bytes)) != 0)
					{
						this.errno = (byte)result;
						return result;
					}
					
					remainBytes -= bytes;
					//System.out.println("totalBytes=" + (header.body_len - remainBytes));
				}
				
				return 0;
		}
		finally
		{
			if (bNewConnection)
			{
				this.storageServer.close();
				this.storageServer = null;
			}
		}
	}
	
	/**
	* get all metadata items from storage server
	* @param group_name the group name of storage server
	*	@param remote_filename filename on storage server
	* @return meta info array, return null if fail
	*/
	public NameValuePair[] get_metadata(String group_name, String remote_filename)throws IOException, Exception
	{
		boolean bNewConnection;
		
		bNewConnection = this.newUpdatableStorageConnection(group_name, remote_filename);
		try
		{
			ProtoCommon.RecvPackageInfo pkgInfo;
			
			this.send_package(ProtoCommon.STORAGE_PROTO_CMD_GET_METADATA, group_name, remote_filename);
			pkgInfo = ProtoCommon.recvPackage(this.storageServer.getInputStream(), 
	                                     ProtoCommon.STORAGE_PROTO_CMD_RESP, -1);
			
			this.errno = pkgInfo.errno;
			if (pkgInfo.errno != 0)
			{
				return null;
			}
			
			return ProtoCommon.split_metadata(new String(pkgInfo.body, ClientGlobal.g_charset));
		}
		finally
		{
			if (bNewConnection)
			{
				this.storageServer.close();
				this.storageServer = null;
			}
		}
	}
	
	/**
	* set metadata items to storage server
	* @param group_name the group name of storage server
	*	@param remote_filename filename on storage server
	*	@param meta_list meta item array
	* @param op_flag flag, can be one of following values: <br>
	*            <ul><li> ProtoCommon.STORAGE_SET_METADATA_FLAG_OVERWRITE: overwrite all old
	*				       metadata items</li></ul>
	*            <ul><li> ProtoCommon.STORAGE_SET_METADATA_FLAG_MERGE: merge, insert when
	*				       the metadata item not exist, otherwise update it</li></ul>
	* @return 0 for success, !=0 fail (error code)
	*/
	public int set_metadata(String group_name, String remote_filename, 
							NameValuePair[] meta_list, byte op_flag) throws IOException, Exception
	{
		boolean bNewConnection;
		
		bNewConnection = this.newUpdatableStorageConnection(group_name, remote_filename);
		try
		{
			byte[] header;
			byte[] groupBytes;
			byte[] filenameBytes;
			byte[] meta_buff;
			byte[] bs;
			int groupLen;
			byte[] sizeBytes;
			ProtoCommon.RecvPackageInfo pkgInfo;
			
			if (meta_list == null)
			{
				meta_buff = new byte[0];
			}
			else
			{
				meta_buff = ProtoCommon.pack_metadata(meta_list).getBytes(ClientGlobal.g_charset);
			}
			
			filenameBytes = remote_filename.getBytes(ClientGlobal.g_charset);
			sizeBytes = new byte[2 * ProtoCommon.TRACKER_PROTO_PKG_LEN_SIZE];
			Arrays.fill(sizeBytes, (byte)0);
			
			bs = ProtoCommon.long2buff(filenameBytes.length);
			System.arraycopy(bs, 0, sizeBytes, 0, bs.length);
			bs = ProtoCommon.long2buff(meta_buff.length);
			System.arraycopy(bs, 0, sizeBytes, ProtoCommon.TRACKER_PROTO_PKG_LEN_SIZE, bs.length);
			
			groupBytes = new byte[ProtoCommon.FDFS_GROUP_NAME_MAX_LEN];
			bs = group_name.getBytes(ClientGlobal.g_charset);
			
			Arrays.fill(groupBytes, (byte)0);
			if (bs.length <= groupBytes.length)
			{
				groupLen = bs.length;
			}
			else
			{
				groupLen = groupBytes.length;
			}
			System.arraycopy(bs, 0, groupBytes, 0, groupLen);
			
			header = ProtoCommon.packHeader(ProtoCommon.STORAGE_PROTO_CMD_SET_METADATA, 
			           2 * ProtoCommon.TRACKER_PROTO_PKG_LEN_SIZE + 1 + groupBytes.length
			           + filenameBytes.length + meta_buff.length, (byte)0);
			OutputStream out = this.storageServer.getOutputStream();
			byte[] wholePkg = new byte[header.length + sizeBytes.length + 1 + groupBytes.length + filenameBytes.length];
			System.arraycopy(header, 0, wholePkg, 0, header.length);
			System.arraycopy(sizeBytes, 0, wholePkg, header.length, sizeBytes.length);
			wholePkg[header.length+sizeBytes.length] = op_flag;
			System.arraycopy(groupBytes, 0, wholePkg, header.length+sizeBytes.length+1, groupBytes.length);
			System.arraycopy(filenameBytes, 0, wholePkg, header.length+sizeBytes.length+1+groupBytes.length, filenameBytes.length);
			out.write(wholePkg);
			if (meta_buff.length > 0)
			{
				out.write(meta_buff);
		  }
		  
			pkgInfo = ProtoCommon.recvPackage(this.storageServer.getInputStream(), 
	                                     ProtoCommon.STORAGE_PROTO_CMD_RESP, 0);
			
			this.errno = pkgInfo.errno;
			return pkgInfo.errno;
		}
		finally
		{
			if (bNewConnection)
			{
				this.storageServer.close();
				this.storageServer = null;
			}
		}
	}

	/**
	* check storage socket, if null create a new connection
	* @param group_name the group name to upload file to, can be empty
	* @return true if create a new connection
	*/
	protected boolean newWritableStorageConnection(String group_name) throws IOException, Exception
	{
		if (this.storageServer != null)
		{
			return false;
		}
		else
		{
			TrackerClient tracker = new TrackerClient();
  		this.storageServer = tracker.getStoreStorage(this.trackerSocket, group_name);
  		if (this.storageServer == null)
  		{
  			throw new Exception("getStoreStorage fail, errno code: " + tracker.getErrorCode());
  		}
  		return true;
		}
  }

	/**
	* check storage socket, if null create a new connection
	* @param group_name the group name of storage server
	*	@param remote_filename filename on storage server
	* @return true if create a new connection
	*/
	protected boolean newReadableStorageConnection(String group_name, String remote_filename) throws IOException, Exception
	{
		if (this.storageServer != null)
		{
			return false;
		}
		else
		{
			TrackerClient tracker = new TrackerClient();
  		this.storageServer = tracker.getFetchStorage(this.trackerSocket, group_name, remote_filename);
  		if (this.storageServer == null)
  		{
  			throw new Exception("getStoreStorage fail, errno code: " + tracker.getErrorCode());
  		}
  		return true;
		}
  }

	/**
	* check storage socket, if null create a new connection
	* @param group_name the group name of storage server
	*	@param remote_filename filename on storage server
	* @return true if create a new connection
	*/
	protected boolean newUpdatableStorageConnection(String group_name, String remote_filename) throws IOException, Exception
	{
		if (this.storageServer != null)
		{
			return false;
		}
		else
		{
			TrackerClient tracker = new TrackerClient();
  		this.storageServer = tracker.getUpdateStorage(this.trackerSocket, group_name, remote_filename);
  		if (this.storageServer == null)
  		{
  			throw new Exception("getStoreStorage fail, errno code: " + tracker.getErrorCode());
  		}
  		return true;
		}
  }
  
	/**
	* send package to storage server
	* @param cmd which command to send
	* @param group_name the group name of storage server
	*	@param remote_filename filename on storage server
	*/
	protected void send_package(byte cmd, String group_name, String remote_filename) throws IOException
	{
		byte[] header;
		byte[] groupBytes;
		byte[] filenameBytes;
		byte[] bs;
		int groupLen;
		
		groupBytes = new byte[ProtoCommon.FDFS_GROUP_NAME_MAX_LEN];
		bs = group_name.getBytes(ClientGlobal.g_charset);
		filenameBytes = remote_filename.getBytes(ClientGlobal.g_charset);
		
		Arrays.fill(groupBytes, (byte)0);
		if (bs.length <= groupBytes.length)
		{
			groupLen = bs.length;
		}
		else
		{
			groupLen = groupBytes.length;
		}
		System.arraycopy(bs, 0, groupBytes, 0, groupLen);
		
		header = ProtoCommon.packHeader(cmd, groupBytes.length + filenameBytes.length, (byte)0);
		OutputStream out = this.storageServer.getOutputStream();
		byte[] wholePkg = new byte[header.length + groupBytes.length + filenameBytes.length];
		System.arraycopy(header, 0, wholePkg, 0, header.length);
		System.arraycopy(groupBytes, 0, wholePkg, header.length, groupBytes.length);
		System.arraycopy(filenameBytes, 0, wholePkg, header.length+groupBytes.length, filenameBytes.length);
		out.write(wholePkg);
	}
	
	/**
	* send package to storage server
	* @param group_name the group name of storage server
	*	@param remote_filename filename on storage server
	* @param file_offset the start offset of the file
	* @param download_bytes download bytes
	*/
	protected void send_download_package(String group_name, String remote_filename, long file_offset, long download_bytes) throws IOException
	{
		byte[] header;
		byte[] bsOffset;
		byte[] bsDownBytes;
		byte[] groupBytes;
		byte[] filenameBytes;
		byte[] bs;
		int groupLen;
		
		bsOffset = ProtoCommon.long2buff(file_offset);
		bsDownBytes = ProtoCommon.long2buff(download_bytes);
		groupBytes = new byte[ProtoCommon.FDFS_GROUP_NAME_MAX_LEN];
		bs = group_name.getBytes(ClientGlobal.g_charset);
		filenameBytes = remote_filename.getBytes(ClientGlobal.g_charset);
		
		Arrays.fill(groupBytes, (byte)0);
		if (bs.length <= groupBytes.length)
		{
			groupLen = bs.length;
		}
		else
		{
			groupLen = groupBytes.length;
		}
		System.arraycopy(bs, 0, groupBytes, 0, groupLen);
		
		header = ProtoCommon.packHeader(ProtoCommon.STORAGE_PROTO_CMD_DOWNLOAD_FILE, 
             bsOffset.length + bsDownBytes.length + groupBytes.length + filenameBytes.length, (byte)0);
		OutputStream out = this.storageServer.getOutputStream();
		byte[] wholePkg = new byte[header.length + bsOffset.length + bsDownBytes.length + groupBytes.length + filenameBytes.length];
		System.arraycopy(header, 0, wholePkg, 0, header.length);
		System.arraycopy(bsOffset, 0, wholePkg, header.length, bsOffset.length);
		System.arraycopy(bsDownBytes, 0, wholePkg, header.length+bsOffset.length, bsDownBytes.length);
		System.arraycopy(groupBytes, 0, wholePkg, header.length+bsOffset.length+bsDownBytes.length, groupBytes.length);
		System.arraycopy(filenameBytes, 0, wholePkg, header.length+bsOffset.length+bsDownBytes.length+groupBytes.length, filenameBytes.length);
		out.write(wholePkg);
	}
}
