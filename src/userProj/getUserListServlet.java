package userProj;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


@WebServlet("/getUserListServlet")
public class getUserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public getUserListServlet() {
        super();
    }
    // 조회
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset-UTF-8");

		JSONArray ary = new JSONArray();
		
		UserDAO dao = new UserDAO();
		List<UserVO> list = dao.getUserList();
		
		for(UserVO vo : list) {
			JSONObject obj = new JSONObject();
			obj.put("userid", vo.getUserId());
			obj.put("name", vo.getUserName());
			obj.put("pass", vo.getUserPass());
			obj.put("phone", vo.getUserPhone());
			obj.put("gender", vo.getUserGender());
			ary.add(obj);
		}
		response.getWriter().print(ary);
		}
	// 입력
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
		
		UserVO uvo = dao.insertUser(vo);
		
		JSONObject obj = new JSONObject();
		obj.put("userid", vo.getUserId());
		obj.put("name", vo.getUserName());
		obj.put("pass", vo.getUserPass());
		obj.put("phone", vo.getUserPhone());
		obj.put("gender", vo.getUserGender());
		
		response.getWriter().print(obj);
		
	}

}
