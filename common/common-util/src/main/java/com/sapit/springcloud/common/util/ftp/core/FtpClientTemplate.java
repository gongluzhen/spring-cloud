package com.sapit.springcloud.common.util.ftp.core;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpClientTemplate {
	private Logger log = LoggerFactory.getLogger(getClass());

	private GenericObjectPool<FTPClient> ftpClientPool;

	public FtpClientTemplate(FtpClientFactory ftpClientFactory) {
		this.ftpClientPool = new GenericObjectPool<>(ftpClientFactory);
		this.ftpClientPool.setMinIdle(0);
		this.ftpClientPool.setMinEvictableIdleTimeMillis(60 * 1000);
		this.ftpClientPool.setSoftMinEvictableIdleTimeMillis(60 * 1000);
		this.ftpClientPool.setTimeBetweenEvictionRunsMillis(3000);
	}

	public boolean uploadFile(String uuid, InputStream inputStream, String remotePath) {
		log.info(uuid + "开始上传.....");

		FTPClient ftpClient = null;
		try {
			ftpClient = ftpClientPool.borrowObject();

			ftpClient.changeWorkingDirectory("~");
			ftpClient.makeDirectory(remotePath);
			ftpClient.changeWorkingDirectory(remotePath);

			ftpClient.storeUniqueFile(uuid, inputStream);
			log.info(uuid + "文件上传成功!");
			return true;
		} catch (Exception e) {
			log.error("上传文件异常!", e);
		} finally {
			IOUtils.closeQuietly(inputStream);
			// 将对象放回池中
			ftpClientPool.returnObject(ftpClient);
		}
		return false;
	}

	/**
	 * 下载文件
	 *
	 * @param remotePath
	 *            FTP服务器文件目录
	 * @param fileName
	 *            需要下载的文件名称
	 * @param localPath
	 *            下载后的文件路径
	 * @return true or false
	 */
	public boolean downloadFile(String remotePath, String uuid, OutputStream outputStream) {
		FTPClient ftpClient = null;
		try {
			ftpClient = ftpClientPool.borrowObject();
			// 验证FTP服务器是否登录成功
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				return false;
			}
			ftpClient.changeWorkingDirectory("~");
			ftpClient.changeWorkingDirectory(remotePath);
			FTPFile[] ftpFiles = ftpClient.listFiles();

			for (FTPFile file : ftpFiles) {
				if (uuid.equalsIgnoreCase(file.getName())) {
					ftpClient.retrieveFile(file.getName(), outputStream);
					break;
				}
			}
			return true;
		} catch (Exception e) {
			log.error("下载文件异常", e);
		} finally {
			ftpClientPool.returnObject(ftpClient);
		}
		return false;
	}
}
