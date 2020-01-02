package kr.ac.sunmoon.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.sunmoon.model.service.PortfolioService;


@WebServlet("/portfolio_remove.do")
public class PortfolioRemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//parameter: no
		int no = Integer.parseInt(request.getParameter("no"));
		PortfolioService pService = new PortfolioService();
		try {
			pService.remove(no);
			response.sendRedirect("portfolio_list.do");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMsg",e.getMessage());
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}

}
