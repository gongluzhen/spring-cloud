package com.sapit.springcloud.web.modules.sys;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.sapit.springcloud.client.sys.SysFileClient;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.common.util.ftp.core.FtpClientTemplate;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.SysFile;
import com.sapit.springcloud.web.common.utils.UserUtils;
import com.sapit.springcloud.web.common.web.BaseController;

/**
 * 系统-附件表Controller
 * 
 * @author gongluzhen
 * @version 2018-12-12
 */
@Controller
@RequestMapping(value = "/sys/sysFile")
public class SysFileController extends BaseController {
	@Autowired
	private SysFileClient sysFileClient;
	@Autowired
	private FtpClientTemplate ftpClientTemplate;

	@ModelAttribute
	public SysFile get(@RequestParam(required = false) String id) {
		SysFile entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = sysFileClient.getById(id);
		}
		if (entity == null) {
			entity = new SysFile();
		}
		return entity;
	}

	@RequiresPermissions("sys:sysFile:view")
	@RequestMapping(value = { "list", "" })
	public String list(SysFile sysFile, HttpServletRequest request, HttpServletResponse response, Model model) {
		sysFile.setCurrentLoginUser(UserUtils.getUser());
		sysFile.setPage(new Page<SysFile>(request, response));

		Page<SysFile> page = sysFileClient.findPage(sysFile);
		model.addAttribute("page", page);

		return "modules/sys/sysFileList";
	}

	@RequiresPermissions("sys:sysFile:view")
	@RequestMapping(value = "form")
	public String form(SysFile sysFile, Model model) {
		model.addAttribute("sysFile", sysFile);
		return "modules/sys/sysFileForm";
	}

	@RequiresPermissions("sys:sysFile:edit")
	@RequestMapping(value = "save")
	public String save(SysFile sysFile, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysFile)) {
			return form(sysFile, model);
		}
		sysFile.setCurrentLoginUser(UserUtils.getUser());

		sysFileClient.save(sysFile);
		addMessage(redirectAttributes, "保存系统-附件表成功");
		return "redirect:/sys/sysFile/?repage";
	}

	@RequiresPermissions("sys:sysFile:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public JSONObject delete(SysFile sysFile, RedirectAttributes redirectAttributes) {
		sysFileClient.delete(sysFile);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		return jsonObject;
	}
	
	@RequiresPermissions("sys:sysFile:edit")
	@RequestMapping(value = "virtualDelete")
	@ResponseBody
	public JSONObject virtualDelete(SysFile sysFile, RedirectAttributes redirectAttributes) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		return jsonObject;
	}

	@ResponseBody
	@RequestMapping(value = "all")
	public List<SysFile> listData(SysFile sysFile, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(sysFile == null || StringUtils.isBlank(sysFile.getBusinessKey())){
			return null;
		}
		sysFile.setDelFlag(SysFile.DEL_FLAG_NORMAL);
		return sysFileClient.findList(sysFile);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("upload")
	@ResponseBody
	public JSONObject upload(HttpServletRequest request) {
		boolean success = false;
		List<String> paths = Lists.newArrayList();
		String businessTableName = request.getParameter("businessTableName");
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator iter = multiRequest.getFileNames();
			SysFile sysFile = null;
			while (iter.hasNext()) {
				MultipartFile multipartFile = multiRequest.getFile(iter.next().toString());
				if (multipartFile != null) {
					sysFile = new SysFile();
					sysFile.setName(multipartFile.getOriginalFilename());
					sysFile.setContentType(multipartFile.getContentType());;
					sysFile.setSize(multipartFile.getSize());
					sysFile.setBusinessTableName(businessTableName);
					sysFile.setCurrentLoginUser(UserUtils.getUser());

					sysFile = sysFileClient.save(sysFile);
					paths.add(sysFile.getId());

					try {
						success = ftpClientTemplate.uploadFile(sysFile.getId(), multipartFile.getInputStream(), businessTableName);
					} catch (IOException e) {
						success = false;
						e.printStackTrace();
					}
				}
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", success);
		jsonObject.put("paths", paths);
		return jsonObject;
	}

	@RequestMapping("/downLoad")
	public void downLoad(HttpServletResponse response, HttpServletRequest request) {
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			SysFile sysFile = sysFileClient.getById(id);
			if(sysFile != null){
				OutputStream outputStream = null;
				try {
					response.reset();

					String filename = new String(URLDecoder.decode(sysFile.getName().replace("\\", ""), "UTF-8").getBytes(), "ISO-8859-1");
					response.setHeader("Content-Disposition", "attachment;filename=" + filename);
					response.setContentLengthLong(sysFile.getSize());
					response.setHeader("Cache-Control", "no-store");
					response.setHeader("Pragma", "no-cache");
					response.setDateHeader("Expires", 0);
					response.setContentType("multipart/form-data");
					response.setCharacterEncoding("UTF-8");

					outputStream = response.getOutputStream();
					ftpClientTemplate.downloadFile(sysFile.getBusinessTableName(), sysFile.getId(), outputStream);
					
					outputStream.flush();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					IOUtils.closeQuietly(outputStream);
				}
			}
		}
	}

}