/**
* Copyright (C) 2008 Happy Fish / YuQing
*
* FastDFS Java Client may be copied only under the terms of the GNU Lesser
* General Public License (LGPL).
* Please visit the FastDFS Home Page http://www.csource.org/ for more detail.
**/

package com.zheyue.encrypt.filesystem.fastdfs;


import com.zheyue.encrypt.filesystem.common.NameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Arrays;

/**
* protocol common functions
* @author Happy Fish / YuQing
* @version Version 1.7
*/
public class ProtoCommon
{
	/**
	* receive package info
	*/
	public static class RecvPackageInfo
	{
		public byte errno;
		public byte[] body;
		
		public RecvPackageInfo(byte errno, byte[] body)
		{
			this.errno = errno;
			this.body = body;
		}
	}
	
	/**
	* receive header info
	*/
	public static class RecvHeaderInfo
	{
		public byte errno;
		public long body_len;
		
		public RecvHeaderInfo(byte errno, long body_len)
		{
			this.errno = errno;
			this.body_len = body_len;
		}
	}
	
	public static final byte FDFS_PROTO_CMD_QUIT      = 82;
	public static final byte TRACKER_PROTO_CMD_SERVER_LIST_GROUP = 91;
	public static final byte TRACKER_PROTO_CMD_SERVER_LIST_STORAGE = 92;
	public static final byte TRACKER_PROTO_CMD_SERVER_RESP = 90;
	
	public static final byte TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITHOUT_GROUP = 101;
	public static final byte TRACKER_PROTO_CMD_SERVICE_QUERY_FETCH_ONE = 102;
	public static final byte TRACKER_PROTO_CMD_SERVICE_QUERY_UPDATE = 103;
	public static final byte TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITH_GROUP = 104;
	public static final byte TRACKER_PROTO_CMD_SERVICE_QUERY_FETCH_ALL = 105;
	public static final byte TRACKER_PROTO_CMD_SERVICE_RESP = 100;

	public static final byte STORAGE_PROTO_CMD_UPLOAD_FILE  = 11;
	public static final byte STORAGE_PROTO_CMD_DELETE_FILE	= 12;
	public static final byte STORAGE_PROTO_CMD_SET_METADATA	 = 13;
	public static final byte STORAGE_PROTO_CMD_DOWNLOAD_FILE = 14;
	public static final byte STORAGE_PROTO_CMD_GET_METADATA	 = 15;
	public static final byte STORAGE_PROTO_CMD_RESP	 = 10;

	/**
	* for overwrite all old metadata
	*/
	public static final byte STORAGE_SET_METADATA_FLAG_OVERWRITE = 'O';
	
	/**
	* for replace, insert when the meta item not exist, otherwise update it
	*/
	public static final byte STORAGE_SET_METADATA_FLAG_MERGE = 'M';

	public static final byte TRACKER_PROTO_PKG_LEN_SIZE	= 8;
	public static final byte TRACKER_PROTO_CMD_SIZE		= 1;
	public static final byte FDFS_GROUP_NAME_MAX_LEN  = 16;
	public static final byte FDFS_IPADDR_SIZE	 = 16;
	public static final String FDFS_RECORD_SEPERATOR	= "\u0001";
	public static final String FDFS_FIELD_SEPERATOR	  = "\u0002";

	public static final int TRACKER_QUERY_STORAGE_FETCH_BODY_LEN = FDFS_GROUP_NAME_MAX_LEN
                        + FDFS_IPADDR_SIZE - 1 + TRACKER_PROTO_PKG_LEN_SIZE;
	public static final int TRACKER_QUERY_STORAGE_STORE_BODY_LEN = FDFS_GROUP_NAME_MAX_LEN
                        + FDFS_IPADDR_SIZE + TRACKER_PROTO_PKG_LEN_SIZE;

	protected static final int PROTO_HEADER_CMD_INDEX	  = TRACKER_PROTO_PKG_LEN_SIZE;
	protected static final int PROTO_HEADER_STATUS_INDEX	  = TRACKER_PROTO_PKG_LEN_SIZE+1;
	
	public static final byte FDFS_FILE_EXT_NAME_MAX_LEN  = 5;
	
	private ProtoCommon()
	{
	}

/**
* pack header by FastDFS transfer protocol
* @param cmd which command to send
* @param pkg_len package body length
* @param errno status code, should be (byte)0
* @return packed byte buffer
*/
	public static byte[] packHeader(byte cmd, long pkg_len, byte errno) throws UnsupportedEncodingException
	{
		byte[] header;
		byte[] hex_len;
		
		header = new byte[TRACKER_PROTO_PKG_LEN_SIZE + 2];
		Arrays.fill(header, (byte)0);
		
		hex_len = ProtoCommon.long2buff(pkg_len);
		System.arraycopy(hex_len, 0, header, 0, hex_len.length);
		header[PROTO_HEADER_CMD_INDEX] = cmd;
		header[PROTO_HEADER_STATUS_INDEX] = errno;
		return header;
	}

/**
* receive pack header
* @param in input stream
* @param expect_cmd expect response command
* @param expect_body_len expect response package body length
* @return RecvHeaderInfo: errno and pkg body length
*/
	public static RecvHeaderInfo recvHeader(InputStream in, byte expect_cmd, long expect_body_len) throws IOException
	{
		byte[] header;
		int bytes;
		long pkg_len;
		
		header = new byte[TRACKER_PROTO_PKG_LEN_SIZE + 2];
		
		if ((bytes=in.read(header)) != header.length)
		{
			throw new IOException("recv package size " + bytes + " != " + header.length);
		}
		
		if (header[PROTO_HEADER_CMD_INDEX] != expect_cmd)
		{
			throw new IOException("recv cmd: " + header[PROTO_HEADER_CMD_INDEX] + " is not correct, expect cmd: " + expect_cmd);
		}
		
		if (header[PROTO_HEADER_STATUS_INDEX] != 0)
		{
			return new RecvHeaderInfo(header[PROTO_HEADER_STATUS_INDEX], 0);
		}
		
		pkg_len = ProtoCommon.buff2long(header, 0);
		if (pkg_len < 0)
		{
			throw new IOException("recv body length: " + pkg_len + " < 0!");
		}
		
		if (expect_body_len >= 0 && pkg_len != expect_body_len)
		{
			throw new IOException("recv body length: " + pkg_len + " is not correct, expect length: " + expect_body_len);
		}
		
		return new RecvHeaderInfo((byte)0, pkg_len);
	}

/**
* receive whole pack
* @param in input stream
* @param expect_cmd expect response command
* @param expect_body_len expect response package body length
* @return RecvPackageInfo: errno and reponse body(byte buff)
*/
	public static RecvPackageInfo recvPackage(InputStream in, byte expect_cmd, long expect_body_len) throws IOException
	{
		RecvHeaderInfo header = recvHeader(in, expect_cmd, expect_body_len);
		if (header.errno != 0)
		{
			return new RecvPackageInfo(header.errno, null);
		}
		
		byte[] body = new byte[(int)header.body_len];
		int totalBytes = 0;
		int remainBytes = (int)header.body_len;
		int bytes;
		
		while (totalBytes < header.body_len)
		{
			if ((bytes=in.read(body, totalBytes, remainBytes)) < 0)
			{
				break;
			}
			
			totalBytes += bytes;
			remainBytes -= bytes;
		}
		
		if (totalBytes != header.body_len)
		{
			throw new IOException("recv package size " + totalBytes + " != " + header.body_len);
		}
		
		return new RecvPackageInfo((byte)0, body);
	}

/**
* split metadata to name value pair array
* @param meta_buff metadata
* @return name value pair array
*/
	public static NameValuePair[] split_metadata(String meta_buff)
	{
		return split_metadata(meta_buff, FDFS_RECORD_SEPERATOR, FDFS_FIELD_SEPERATOR);
	}

/**
* split metadata to name value pair array
* @param meta_buff metadata
* @param recordSeperator record/row seperator
* @param filedSeperator field/column seperator
* @return name value pair array
*/
	public static NameValuePair[] split_metadata(String meta_buff, 
						    String  recordSeperator, String  filedSeperator)
	{
		String[] rows;
		String[] cols;
		NameValuePair[] meta_list;
	
		rows = meta_buff.split(recordSeperator);
		meta_list = new NameValuePair[rows.length];
		for (int i=0; i<rows.length; i++)
		{
			cols = rows[i].split(filedSeperator, 2);
			meta_list[i] = new NameValuePair(cols[0]);
			if (cols.length == 2)
			{
				meta_list[i].setValue(cols[1]);
			}
		}
		
		return meta_list;
	}

/**
* pack metadata array to string
* @param meta_list metadata array
* @return packed metadata
*/
	public static String pack_metadata(NameValuePair[] meta_list)
	{		
		if (meta_list.length == 0)
		{
			return "";
		}
		
		StringBuffer sb = new StringBuffer(32 * meta_list.length);
		sb.append(meta_list[0].getName()).append(FDFS_FIELD_SEPERATOR).append(meta_list[0].getValue());
		for (int i=1; i<meta_list.length; i++)
		{
			sb.append(FDFS_RECORD_SEPERATOR);
			sb.append(meta_list[i].getName()).append(FDFS_FIELD_SEPERATOR).append(meta_list[i].getValue());
		}
	
		return sb.toString();
	}

/**
* send quit command to server and close socket
* @param sock the Socket object
*/
	public static void closeSocket(Socket sock) throws IOException
	{
		byte[] header;
		header = packHeader(FDFS_PROTO_CMD_QUIT, 0, (byte)0);
		sock.getOutputStream().write(header);
		sock.close();
	}
	
/**
* long convert to buff (big-endian)
* @param long number
* @return 8 bytes buff
*/
	public static byte[] long2buff(long n)
	{
		byte[] bs;
		
		bs = new byte[8];
		bs[0] = (byte)((n >> 56) & 0xFF);
		bs[1] = (byte)((n >> 48) & 0xFF);
		bs[2] = (byte)((n >> 40) & 0xFF);
		bs[3] = (byte)((n >> 32) & 0xFF);
		bs[4] = (byte)((n >> 24) & 0xFF);
		bs[5] = (byte)((n >> 16) & 0xFF);
		bs[6] = (byte)((n >> 8) & 0xFF);
		bs[7] = (byte)(n & 0xFF);
		
		return bs;
	}
	
/**
* long convert to buff
* @param long number
* @return 8 bytes buff
*/
	public static long buff2long(byte[] bs, int offset)
	{
		return  (((long)(bs[offset] >= 0 ? bs[offset] : 256+bs[offset])) << 56) |
		        (((long)(bs[offset+1] >= 0 ? bs[offset+1] : 256+bs[offset+1])) << 48) | 
		        (((long)(bs[offset+2] >= 0 ? bs[offset+2] : 256+bs[offset+2])) << 40) | 
		        (((long)(bs[offset+3] >= 0 ? bs[offset+3] : 256+bs[offset+3])) << 32) | 
		        (((long)(bs[offset+4] >= 0 ? bs[offset+4] : 256+bs[offset+4])) << 24) | 
		        (((long)(bs[offset+5] >= 0 ? bs[offset+5] : 256+bs[offset+5])) << 16) | 
		        (((long)(bs[offset+6] >= 0 ? bs[offset+6] : 256+bs[offset+6])) <<  8) |
		         ((long)(bs[offset+7] >= 0 ? bs[offset+7] : 256+bs[offset+7]));
	}
	
	public static String md5(byte[] source) throws java.security.NoSuchAlgorithmException
	{
  	char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',  'e', 'f'};
    java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
    md.update(source);
    byte tmp[] = md.digest();
    char str[] = new char[32];
    int k = 0;
    for (int i = 0; i < 16; i++)
    {
     str[k++] = hexDigits[tmp[i] >>> 4 & 0xf];
     str[k++] = hexDigits[tmp[i] & 0xf];
    }
    
  	return new String(str);
 }
 
 /**
* get token for file URL
* @param file_id the file id return by FastDFS server
* @param ts unix timestamp, unit: second
* @param secret_key the secret key
* @return token string
*/
 public static String getToken(String file_id, int ts, String secret_key) throws Exception
 {
 	byte[] bsFileId = file_id.getBytes(ClientGlobal.g_charset);
 	byte[] bsKey = secret_key.getBytes(ClientGlobal.g_charset);
 	byte[] bsTimestamp = (new Integer(ts)).toString().getBytes(ClientGlobal.g_charset);
 	
 	byte[] buff = new byte[bsFileId.length + bsKey.length + bsTimestamp.length];
 	System.arraycopy(bsFileId, 0, buff, 0, bsFileId.length);
 	System.arraycopy(bsKey, 0, buff, bsFileId.length, bsKey.length);
 	System.arraycopy(bsTimestamp, 0, buff, bsFileId.length + bsKey.length, bsTimestamp.length);
 	
 	return md5(buff);
 }
}
