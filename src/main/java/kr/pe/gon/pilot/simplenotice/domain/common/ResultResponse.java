package kr.pe.gon.pilot.simplenotice.domain.common;

import java.util.HashMap;

public class ResultResponse {

	public final static String OK = "OK";
	public final static String FAIL = "FAIL";
	
	private String code;
	private String msg;
	private HashMap<String, Object> results = new HashMap<String, Object>();
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public HashMap<String, Object> getResults() {
		return results;
	}
	public void setResults(HashMap<String, Object> results) {
		this.results = results;
	}
	
	public static class Builder {
		private String code;
		private String msg;
		
		public Builder(String code) {
			this.code = code;
		}
		
		public Builder msg(String msg) {
			this.msg = msg;
			return this;
		}
		
		public ResultResponse build() {
			return new ResultResponse(this); 
		}
	}
	
	private ResultResponse(Builder builder) {
        code = builder.code;
        msg = builder.msg;
    }	
}
