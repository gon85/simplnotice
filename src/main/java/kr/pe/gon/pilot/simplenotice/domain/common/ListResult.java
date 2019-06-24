package kr.pe.gon.pilot.simplenotice.domain.common;

import java.util.List;

public class ListResult<T> {
	private long totalCount;
	private List<T> list;
	
	public ListResult() {
		
	}
	public ListResult(int totalCount, List<T> list) {
		this.totalCount = totalCount;
		this.list = list;
	}
	
	public long getCount() {
		if (list == null) 
			return 0;
		else 
			return list.size();
	}
	
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
}