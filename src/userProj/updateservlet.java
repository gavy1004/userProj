package userProj;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

@WebServlet("/updateservlet")
public class updateservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public updateservlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset-UTF-8");
		
		String id = request.getParameter("userid");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		String phone = request.getParameter("phone");
		String gender = request.getParameter("gender");
		
		UserDAO dao = new UserDAO();
		UserVO vo = new UserVO();
		vo.setUserId(id);
		vo.setUserName(name);
		vo.setUserPass(pass);
		vo.setUserPhone(phone);
		vo.setUserGender(gender);
		
		JSONObject obj = new JSONObject();
		if (dao.updateUser(vo)) {
			obj.put("retCode", "Success");
		} else {
			obj.put("retCode", "Fail");
		}
		response.getWriter().print(obj);
	}

}
