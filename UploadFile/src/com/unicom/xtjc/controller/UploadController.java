package com.unicom.xtjc.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.unicom.xtjc.domain.RestJSONPhotos;
import com.unicom.xtjc.domain.ResultPhotos;

@Controller
@RequestMapping("/api/Upload")
public class UploadController {
	@RequestMapping(value="/pictureUpload",method=RequestMethod.POST)
	public @ResponseBody
	RestJSONPhotos upload2(HttpServletRequest request, HttpServletResponse response) {
		RestJSONPhotos respond = new RestJSONPhotos();
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
		
		
		List<Object> asklist = new ArrayList<Object>();
		respond.setResult(asklist);

		try {
			
			//String uploadPath = request.getSession().getServletContext().getRealPath("/images");
			String uploadPath="/home/wyf/WinData/UploadImg/";
			File dirfile=new File(uploadPath);
			
			if(!dirfile.exists()){
			
			dirfile.mkdirs();
			
			}
			// 创建一个通用的多部分解析器
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			// 判断 request 是否有文件上传,即多部分请求
			if (multipartResolver.isMultipart(request)) {
				// 转换成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				// 取得request中的所有文件名
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					ResultPhotos rp=new ResultPhotos();
					// 记录上传过程起始时的时间，用来计算上传时间
					int pre = (int) System.currentTimeMillis();
					// 取得上传文件
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						// 取得当前上传文件的文件名称
						String myFileName = file.getOriginalFilename();
						// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
						if (myFileName.trim() != "") {
							System.out.println(myFileName);
							// 重命名上传后的文件名
							UUID uuid = UUID.randomUUID();
							String fileName = uuid+".png";  //采用时间+UUID的方式随即命名 
							
							File localFile = new File(uploadPath, fileName);

							file.transferTo(localFile);
							rp.setPhotoname(fileName);
						}
					}
					// 记录上传该文件后的时间
					int finaltime = (int) System.currentTimeMillis();
					System.out.println(finaltime - pre);
					int totaltime=finaltime - pre;
					rp.setTotaltime(totaltime+"");
					rp.setUploadtime(new Date());
					rp.setFilepath(basePath);
					asklist.add(rp);
				}
				respond.setErrCode("0");
				respond.setMessage("success！");
				respond.setResult(asklist);
			}else{
				respond.setErrCode("-151019");
				respond.setMessage("没有文件上传！");
			}
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			respond.setErrCode("-151017");
			respond.setMessage("服务器状态异常！");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			respond.setErrCode("-151018");
			respond.setMessage("文件读写异常！");
			e.printStackTrace();
		}
		return respond;
	}

	  @RequestMapping("/demo" )   
	    public String toUpload() {  
	          
	        return "demo";  
	    }  
}
