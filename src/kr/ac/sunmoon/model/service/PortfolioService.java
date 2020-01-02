package kr.ac.sunmoon.model.service;

import java.util.ArrayList;

import kr.ac.sunmoon.model.dao.PortfolioDAO;
import kr.ac.sunmoon.model.dto.Portfolio;
import kr.ac.sunmoon.model.dto.PortfolioData;

public class PortfolioService {

	private PortfolioDAO pDao = new PortfolioDAO();
	
	public void register(Portfolio p) throws Exception {
		///Tx 적용 필요함.
		int no = pDao.insert(p);//여기선 성공하지만
		ArrayList<PortfolioData> list = p.getDataList();
		if(list != null && list.size()>0) {
			pDao.insert(no,p.getDataList());//여기서 실패할 수 있다			
		}
		//여기까지 Tx필요
	}//문제가 있는 코드이다.
	
	public ArrayList<Portfolio> getList() throws Exception {
		return pDao.select();
	}
	public ArrayList<Portfolio> search(String title) throws Exception {
		return pDao.select(title);
	}
	public Portfolio getDetail(int no) throws Exception {
		Portfolio p = pDao.select(no);
		p.setDataList(pDao.selectDataList(no));
		return p;
	}
	public void modify(Portfolio p) throws Exception {
		pDao.update(p);
	}
	public void remove(int no) throws Exception {
		pDao.delete(no);
	}
}











