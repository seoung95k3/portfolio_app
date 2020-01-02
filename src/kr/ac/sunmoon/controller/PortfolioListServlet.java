package kr.ac.sunmoon.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.sunmoon.model.service.PortfolioService;


@WebServlet("/portfolio_list.do")
public class PortfolioListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		PortfolioService pService = new PortfolioService();
		try {
			request.setAttribute("list", pService.getList());
			request.getRequestDispatcher("portfolio_list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMsg",e.getMessage());
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
		
	}

}
