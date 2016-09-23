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
			// ����һ��ͨ�õĶಿ�ֽ�����
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			// �ж� request �Ƿ����ļ��ϴ�,���ಿ������
			if (multipartResolver.isMultipart(request)) {
				// ת���ɶಿ��request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				// ȡ��request�е������ļ���
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					ResultPhotos rp=new ResultPhotos();
					// ��¼�ϴ�������ʼʱ��ʱ�䣬���������ϴ�ʱ��
					int pre = (int) System.currentTimeMillis();
					// ȡ���ϴ��ļ�
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						// ȡ�õ�ǰ�ϴ��ļ����ļ�����
						String myFileName = file.getOriginalFilename();
						// ������Ʋ�Ϊ����,˵�����ļ����ڣ�����˵�����ļ�������
						if (myFileName.trim() != "") {
							System.out.println(myFileName);
							// �������ϴ�����ļ���
							UUID uuid = UUID.randomUUID();
							String fileName = uuid+".png";  //����ʱ��+UUID�ķ�ʽ�漴���� 
							
							File localFile = new File(uploadPath, fileName);

							file.transferTo(localFile);
							rp.setPhotoname(fileName);
						}
					}
					// ��¼�ϴ����ļ����ʱ��
					int finaltime = (int) System.currentTimeMillis();
					System.out.println(finaltime - pre);
					int totaltime=finaltime - pre;
					rp.setTotaltime(totaltime+"");
					rp.setUploadtime(new Date());
					rp.setFilepath(basePath);
					asklist.add(rp);
				}
				respond.setErrCode("0");
				respond.setMessage("success��");
				respond.setResult(asklist);
			}else{
				respond.setErrCode("-151019");
				respond.setMessage("û���ļ��ϴ���");
			}
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			respond.setErrCode("-151017");
			respond.setMessage("������״̬�쳣��");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			respond.setErrCode("-151018");
			respond.setMessage("�ļ���д�쳣��");
			e.printStackTrace();
		}
		return respond;
	}

	  @RequestMapping("/demo" )   
	    public String toUpload() {  
	          
	        return "demo";  
	    }  
}
