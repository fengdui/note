/**
* Copyright (C) 2008 Happy Fish / YuQing
*
* FastDFS Java Client may be copied only under the terms of the GNU Lesser
* General Public License (LGPL).
* Please visit the FastDFS Home Page http://www.csource.org/ for more detail.
*/

package com.zheyue.encrypt.filesystem.fastdfs;

import com.zheyue.encrypt.filesystem.common.NameValuePair;

import java.io.IOException;
import java.net.Socket;

/**
* Storage client for 1 field file id: combined group name and filename
* @author Happy Fish / YuQing
* @version Version 1.9
*/
public class StorageClient1 extends StorageClient
{
	public static final String SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR = "/";
	
/**
* @param trackerSocket Socket of tracker server
* @param storageServer Socket of storage server, can be null
*/
	public StorageClient1(Socket trackerSocket, StorageServer storageServer)
	{
		super(trackerSocket, storageServer);
	}

	public static byte split_file_id(String file_id, String[] results)
	{
		int pos = file_id.indexOf(SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR);
		if ((pos <= 0) || (pos == file_id.length() - 1))
		{
			return -1;
		}
		
		results[0] = file_id.substring(0, pos); //group name
		results[1] = file_id.substring(pos + 1); //file name
		return 0;
  }
  	
	/**
	* upload file to storage server (by file name)
	* @param local_filename local filename to upload
	* @param file_ext_name file ext name, do not include dot(.), null to extract ext name from the local filename
	* @param meta_list meta info array
	* @return  file id(including group name and filename) if success, <br>
	*         return null if fail
	*/
	public String upload_file1(String local_filename, String file_ext_name, 
	       NameValuePair[] meta_list) throws IOException, Exception
	{
		String parts[] = this.upload_file(local_filename, file_ext_name, meta_list);
		if (parts != null)
		{
			return parts[0] + SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR + parts[1];
		}
		else
		{
			return null;
		}
	}

	/**
	* upload file to storage server (by file name)
	* @param group_name the group name to upload file to, can be empty
	* @param local_filename local filename to upload
	* @param file_ext_name file ext name, do not include dot(.), null to extract ext name from the local filename
	* @param meta_list meta info array
	* @return  file id(including group name and filename) if success, <br>
	*         return null if fail
	*/
	public String upload_file1(String group_name, String local_filename, String file_ext_name, 
	       NameValuePair[] meta_list) throws IOException, Exception
	{
		String parts[] = this.upload_file(group_name, local_filename, file_ext_name, meta_list);
		if (parts != null)
		{
			return parts[0] + SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR + parts[1];
		}
		else
		{
			return null;
		}
	}
	
	/**
	* upload file to storage server (by file buff)
	* @param file_buff file content/buff
	* @param file_ext_name file ext name, do not include dot(.)
	*	@param meta_list meta info array
	* @return  file id(including group name and filename) if success, <br>
	*         return null if fail
	*/
	public String upload_file1(byte[] file_buff, String file_ext_name,	
	       NameValuePair[] meta_list) throws IOException, Exception
	{
		String parts[] = this.upload_file(file_buff, file_ext_name, meta_list);
		if (parts != null)
		{
			return parts[0] + SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR + parts[1];
		}
		else
		{
			return null;
		}
	}

	/**
	* upload file to storage server (by file buff)
	* @param group_name the group name to upload file to, can be empty
	* @param file_buff file content/buff
	* @param file_ext_name file ext name, do not include dot(.)
	*	@param meta_list meta info array
	* @return  file id(including group name and filename) if success, <br>
	*         return null if fail
	*/
	public String upload_file1(String group_name, byte[] file_buff, String file_ext_name,	
	       NameValuePair[] meta_list) throws IOException, Exception
	{
		String parts[] = this.upload_file(group_name, file_buff, file_ext_name, meta_list);
		if (parts != null)
		{
			return parts[0] + SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR + parts[1];
		}
		else
		{
			return null;
		}
	}

	/**
	* upload file to storage server (by callback)
	* @param group_name the group name to upload file to, can be empty
	* @param file_size the file size
	* @param callback the write data callback object
	* @param file_ext_name file ext name, do not include dot(.)
	*	@param meta_list meta info array
  * @return file id(including group name and filename) if success, <br>
	*         return null if fail
	*/
	public String upload_file1(String group_name, long file_size, 
	       UploadCallback callback, String file_ext_name, 
	       NameValuePair[] meta_list) throws IOException, Exception
	{
		String parts[] = this.upload_file(group_name, file_size, callback, file_ext_name, meta_list);
		if (parts != null)
		{
			return parts[0] + SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR + parts[1];
		}
		else
		{
			return null;
		}
	}
	
	/**
	* delete file from storage server
	* @param the file id(including group name and filename)
	* @return 0 for success, none zero for fail (error code)
	*/
	public int delete_file1(String file_id) throws IOException, Exception
	{
		String[] parts = new String[2];
		this.errno = this.split_file_id(file_id, parts);
		if (this.errno != 0)
		{
			return this.errno;
		}
		
		return this.delete_file(parts[0], parts[1]);
	}
	
	/**
	* download file from storage server
	* @param the file id(including group name and filename)
	* @return file content/buff, return null if fail
	*/
	public byte[] download_file1(String file_id) throws IOException, Exception
	{
		final long file_offset = 0;
		final long download_bytes = 0;
		
		return this.download_file1(file_id, file_offset, download_bytes);
	}
	
	/**
	* download file from storage server
	* @param the file id(including group name and filename)
	* @param file_offset the start offset of the file
	* @param download_bytes download bytes, 0 for remain bytes from offset
	* @return file content/buff, return null if fail
	*/
	public byte[] download_file1(String file_id, long file_offset, long download_bytes) throws IOException, Exception
	{
		String[] parts = new String[2];
		this.errno = this.split_file_id(file_id, parts);
		if (this.errno != 0)
		{
			return null;
		}
		
		return this.download_file(parts[0], parts[1], file_offset, download_bytes);
	}

	/**
	* download file from storage server
	* @param the file id(including group name and filename)
	* @param local_filename  filename on local
	* @return 0 success, return none zero errno if fail
	*/
	public int download_file1(String file_id, String local_filename) throws IOException, Exception
	{
		final long file_offset = 0;
		final long download_bytes = 0;
		
		return this.download_file1(file_id, file_offset, download_bytes, local_filename);
	}
	
	/**
	* download file from storage server
	* @param the file id(including group name and filename)
	* @param file_offset the start offset of the file
	* @param download_bytes download bytes, 0 for remain bytes from offset
	* @param local_filename  filename on local
	* @return 0 success, return none zero errno if fail
	*/
	public int download_file1(String file_id, long file_offset, long download_bytes, String local_filename) throws IOException, Exception
	{
		String[] parts = new String[2];
		this.errno = this.split_file_id(file_id, parts);
		if (this.errno != 0)
		{
			return this.errno;
		}
		
		return this.download_file(parts[0], parts[1], file_offset, download_bytes, local_filename);
	}
	
	/**
	* download file from storage server
	* @param the file id(including group name and filename)
	* @param callback call callback.recv() when data arrive
	* @return 0 success, return none zero errno if fail
	*/
	public int download_file1(String file_id, DowloadCallback callback) throws IOException, Exception
	{
		final long file_offset = 0;
		final long download_bytes = 0;
		
		return this.download_file1(file_id, file_offset, download_bytes, callback);
	}
	
	/**
	* download file from storage server
	* @param the file id(including group name and filename)
	* @param file_offset the start offset of the file
	* @param download_bytes download bytes, 0 for remain bytes from offset
	* @param callback call callback.recv() when data arrive
	* @return 0 success, return none zero errno if fail
	*/
	public int download_file1(String file_id, long file_offset, long download_bytes, DowloadCallback callback) throws IOException, Exception
	{
		String[] parts = new String[2];
		this.errno = this.split_file_id(file_id, parts);
		if (this.errno != 0)
		{
			return this.errno;
		}
		
		return this.download_file(parts[0], parts[1], file_offset, download_bytes, callback);
	}

	/**
	* get all metadata items from storage server
	* @param the file id(including group name and filename)
	* @return meta info array, return null if fail
	*/
	public NameValuePair[] get_metadata1(String file_id)throws IOException, Exception
	{
		String[] parts = new String[2];
		this.errno = this.split_file_id(file_id, parts);
		if (this.errno != 0)
		{
			return null;
		}
		
		return this.get_metadata(parts[0], parts[1]);
	}
	
	/**
	* set metadata items to storage server
	* @param the file id(including group name and filename)
	*	@param meta_list meta item array
	* @param op_flag flag, can be one of following values: <br>
	*            <ul><li> ProtoCommon.STORAGE_SET_METADATA_FLAG_OVERWRITE: overwrite all old
	*				       metadata items</li></ul>
	*            <ul><li> ProtoCommon.STORAGE_SET_METADATA_FLAG_MERGE: merge, insert when
	*				       the metadata item not exist, otherwise update it</li></ul>
	* @return 0 for success, !=0 fail (error code)
	*/
	public int set_metadata1(String file_id, NameValuePair[] meta_list, byte op_flag) throws IOException, Exception
	{
		String[] parts = new String[2];
		this.errno = this.split_file_id(file_id, parts);
		if (this.errno != 0)
		{
			return this.errno;
		}
		
		return this.set_metadata(parts[0], parts[1], meta_list, op_flag);
	}
}
