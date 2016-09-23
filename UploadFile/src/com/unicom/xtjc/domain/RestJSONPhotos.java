package com.unicom.xtjc.domain;

import java.util.List;

public class RestJSONPhotos {

	private String errCode;
	private String message;
	private List<Object> result;
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Object> getResult() {
		return result;
	}
	public void setResult(List<Object> result) {
		this.result = result;
	}
}
