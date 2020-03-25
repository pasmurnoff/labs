package servlet;

import DAO.JDBCConnection;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(filterName = "Login", urlPatterns = "/")
public class Login implements Filter {

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String login = req.getParameter("name");
        String password = req.getParameter("password");

        HttpSession session = req.getSession();

        if ((session) != null && (session.getAttribute("user")) != null) {
            req.getRequestDispatcher("profile.jsp").forward(req, resp);
        } else if (login==null) {
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }else {
            String validate = null;
            if (JDBCConnection.validate(login, password)) {
                validate = "true";
            } else {
                validate = "false";
            }
            if ("true".equals(validate)) {
                req.getSession().setAttribute("user","name");
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            } else {
                req.getSession().setAttribute("user", "userName");
                req.getRequestDispatcher("loginerror.jsp").forward(req, resp);
            }
        }
    }
}
