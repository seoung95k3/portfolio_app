package kr.ac.sunmoon.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.sunmoon.model.service.PortfolioService;


@WebServlet("/portfolio_search.do")
public class PortfolioSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PortfolioService pService = new PortfolioService();
		try {
			request.setCharacterEncoding("utf-8");
			String title =request.getParameter("title");
			request.setAttribute("list", pService.search(title));
			request.getRequestDispatcher("portfolio_list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMsg",e.getMessage());
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}

}
