package kr.pe.gon.pilot.simplenotice.domain.common;

public class PageInfo {
	private int totalRow = 0;
	private int rowPerPage = 10;
	private int currentPage = 1;
	private int pageNoPerPage = 10;
	
	public int getRowPerPage() {
		return rowPerPage;
	}
	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageNoPerPage() {
		return pageNoPerPage;
	}
	public void setPageNoPerPage(int pageNoPerPage) {
		this.pageNoPerPage = pageNoPerPage;
	}
	public int getTotalRow() {
		return totalRow;
	}
	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

}
