//DB에 종속되는 코드. MySQL에서만 사용 가능  DAO는 MySQL이나 오라클이나 다 다르게 만들어줘야
package kr.ac.sunmoon.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.ac.sunmoon.model.dto.Portfolio;
import kr.ac.sunmoon.model.dto.PortfolioData;
import kr.ac.sunmoon.util.DBManager;

public class PortfolioDAO {
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public ArrayList<Portfolio> select() throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select no,title,leader,members,start_date,end_date,reg_date,"
				+ "(select count(*) from portfolio_data where portfolio_no = p.no) "
				+ "from portfolio p";
		ArrayList<Portfolio> list = new ArrayList<Portfolio>();
		
		try {
			con = DBManager.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new Portfolio(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						null, rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8)));
			}
		}finally {
			DBManager.close(rs, stmt, con);
		}
		return list;
	}	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public ArrayList<Portfolio> select(String title) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select no,title,leader,members,start_date,end_date,reg_date,"
				+ "(select count(*) from portfolio_data where portfolio_no = p.no) "
				+ "from portfolio p "
				+ "where title like ?";
		ArrayList<Portfolio> list = new ArrayList<Portfolio>();
		
		try {
			con = DBManager.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, "%"+title+"%");
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new Portfolio(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						null, rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8)));
			}
		}finally {
			DBManager.close(rs, stmt, con);
		}
		return list;
	}	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public ArrayList<PortfolioData> selectDataList(int no) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select no,original_file_name,real_file_name "
				+ "from portfolio_data "
				+ "where portfolio_no = ?";
		ArrayList<PortfolioData> list = new ArrayList<PortfolioData>();
		
		try {
			con = DBManager.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, no);
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new PortfolioData(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		}finally {
			DBManager.close(rs, stmt, con);
		}
		return list;
	}	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Portfolio select(int no) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select no,title,leader,members,content,start_date,end_date,reg_date "
				+ "from portfolio "
				+ "where no = ?";
		Portfolio p = null;
		
		try {
			con = DBManager.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, no);
			rs = stmt.executeQuery();
			if(rs.next()) {
				p = new Portfolio(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(5), rs.getString(6), rs.getString(7),0);
			}
		}finally {
			DBManager.close(rs, stmt, con);
		}
		return p;
	}	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void update(Portfolio p) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "update portfolio set title=?,leader=?,members=?,content=?,"
				+ "	start_date=STR_TO_DATE(?, '%Y-%m-%d'),"
				+ "end_date=STR_TO_DATE(?, '%Y-%m-%d'),reg_date=curdate()";
		try {
			con = DBManager.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, p.getTitle());
			stmt.setString(2, p.getLeader());
			stmt.setString(3, p.getMember());
			stmt.setString(4, p.getContent());
			stmt.setString(5, p.getStartDate());
			stmt.setString(6, p.getEndDate());
			stmt.executeUpdate();
		} finally {
			DBManager.close(stmt, con);
		}
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public int insert(Portfolio p) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null, noStmt = null;//일반Statement만 실행,PreparedStatement은 특정 것만 실행
		ResultSet rs = null;
		String sql = "insert into portfolio(title,leader,members,content,start_date,end_date,reg_date)"
				+ " values(?,?,?,?,STR_TO_DATE(?, '%Y-%m-%d'),STR_TO_DATE(?, '%Y-%m-%d'),curdate())";
		
		String noSql = "Select last_insert_id() from portfolio";//?가 없어서 뒤에 stmt.setString(1, p.getTitle()); 등이 필요 없음
		int no =0;
		
		try {
			con = DBManager.getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(sql);
			noStmt = con.prepareStatement(noSql);
			stmt.setString(1, p.getTitle());
			stmt.setString(2, p.getLeader());
			stmt.setString(3, p.getMember());
			stmt.setString(4, p.getContent());
			stmt.setString(5, p.getStartDate());
			stmt.setString(6, p.getEndDate());
			stmt.executeUpdate();
			rs = noStmt.executeQuery();
			if (rs.next()) {
				no = rs.getInt(1);				
			}
			con.commit();//트레디션 실행하고서 / 커밋하고선 꼭 해줘야 함
		}catch (Exception e) {
			con.rollback();
			throw e;
		} finally {
			con.setAutoCommit(true);//*중요 코드* 매우매우매우매우매우매우
			DBManager.close(rs,stmt, con);//con은 service쪽에서 관리해줘야 한다. 지금은 수동으로 하는 등 여러가지 이유로 DAO에서한다.
			DBManager.close(noStmt, con);
			
		}
		return no;
	}
	/////////////////////////
	public void insert(int no,ArrayList<PortfolioData> list) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "insert into portfolio_data(original_file_name,real_file_name,portfolio_no) "
				+ "values(?,?,?)";
		try {
			con = DBManager.getConnection();
			stmt = con.prepareStatement(sql);
			
			for (PortfolioData p : list) {
				stmt.setString(1, p.getOriginalFileName());
				stmt.setString(2, p.getRealFileName());
				stmt.setInt(3, no);
				stmt.executeUpdate();				
			}
		} finally {
			DBManager.close(stmt, con);
		}
	}
	
	public void delete(int no) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "delete from portfolio where no = ?";
		try {
			con = DBManager.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, no);
			stmt.executeUpdate();
		} finally {
			DBManager.close(stmt, con);
		}
	}
	
}





















