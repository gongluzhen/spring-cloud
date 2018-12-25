package com.sapit.springcloud.web.common.web;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/upload")
public class FileUploadController extends BaseController {

	@SuppressWarnings("rawtypes")
	@RequestMapping("")
	@ResponseBody
	public List<String> springUpload(HttpServletRequest request) throws IllegalStateException, IOException {
		List<String> paths = Lists.newArrayList();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
					try {
//						paths.add(FDFSUtil.upload(file.getInputStream(), file.getOriginalFilename(), file.getSize()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return paths;
	}

	/**
	 * 合同管理下载文件
	 * 
	 * @param fileId
	 * @param fileName
	 * @param response
	 * @param request
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/downFile")
	public void springdownLoad(String fileId, String fileName, HttpServletResponse response, HttpServletRequest request)
			throws IllegalStateException, IOException {
		// 一次遍历所有文件
		if (fileId != null) {
			try {
				BufferedOutputStream bos = null;
				OutputStream fos = null;
				try {
					fos = response.getOutputStream();
					bos = new BufferedOutputStream(fos);
					fileOutputStream(response, fileName);
					byte[] download2Byte = null;
					fos.write(download2Byte);
					fos.flush();
				} catch (Exception e) {

				} finally {
					bos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param response
	 * @param fileName
	 * @return
	 */
	/**
	 * 下载文件
	 * 
	 * @param response
	 * @param fileName
	 * @return
	 */
	private OutputStream fileOutputStream(HttpServletResponse response, String fileName) {
		response.reset();
		try {
			// response.setHeader("Content-Disposition",
			// "attachment;fileName=" + URLDecoder.decode(fileName.replace("\\",
			// ""), "UTF-8"));
			fileName = new String(URLDecoder.decode(fileName.replace("\\", ""), "UTF-8").getBytes(), "ISO-8859-1");
			// 或是file_name = URLEncoder.encode(file_name,"UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("multipart/form-data");
			response.setCharacterEncoding("UTF-8");
			return response.getOutputStream();
		} catch (IOException e) {
			return null;
		}
	}
}
