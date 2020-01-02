package kr.ac.sunmoon.model.dto;

public class PortfolioData {
	private int no;
	private String originalFileName;
	private String realFileName;
	private int portfolioNo;
	public PortfolioData(int no, String originalFileName, String realFileName, int portfolioNo) {
		super();
		this.no = no;
		this.originalFileName = originalFileName;
		this.realFileName = realFileName;
		this.portfolioNo = portfolioNo;
	}
	public PortfolioData(int no, String originalFileName, String realFileName) {
		super();
		this.no = no;
		this.originalFileName = originalFileName;
		this.realFileName = realFileName;
	}
	public PortfolioData(String originalFileName, String realFileName) {
		super();
		this.originalFileName = originalFileName;
		this.realFileName = realFileName;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getRealFileName() {
		return realFileName;
	}
	public void setRealFileName(String realFileName) {
		this.realFileName = realFileName;
	}
	public int getPortfolioNo() {
		return portfolioNo;
	}
	public void setPortfolioNo(int portfolioNo) {
		this.portfolioNo = portfolioNo;
	}
}
