package userProj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class UserDAO {
	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	
	private void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (psmt != null) {
			try {
				psmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public List<UserVO> getUserList() {
		conn = DBCon.getConnect();
		List<UserVO> list= new ArrayList<>();
		
		try {
			psmt = conn.prepareStatement("select* from user_temp");
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				UserVO vo = new UserVO();
				vo.setUserId(rs.getString("user_Id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setUserPass(rs.getString("user_pass"));
				vo.setUserPhone(rs.getString("user_phone"));
				vo.setUserGender(rs.getString("user_gender"));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}

		return list;
	}


	// 입력
	public UserVO insertUser(UserVO vo) {
		String sql = "insert into user_temp values(?,?,?,?,?)";
		conn = DBCon.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getUserId());
			psmt.setString(2, vo.getUserName());
			psmt.setString(3, vo.getUserPass());
			psmt.setString(4, vo.getUserPhone());
			psmt.setString(5, vo.getUserGender());
			
			int r = psmt.executeUpdate();
			System.out.println(r + "건 입력되었습니다");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return vo;
		
	}
	
	// 수정
	public boolean updateUser(UserVO vo) {
		conn = DBCon.getConnect();
		PreparedStatement psmt = null;
		int modifyCnt =0;
		String sql = "update user_temp set User_name= ?, User_pass=?, User_phone=?, User_gender=? where user_id=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getUserName());
			psmt.setString(2, vo.getUserPass());
			psmt.setString(3, vo.getUserPhone());
			psmt.setString(4, vo.getUserGender());
			psmt.setString(5, vo.getUserId());
			
			modifyCnt = psmt.executeUpdate();
			System.out.println(modifyCnt + "건 수정.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return modifyCnt == 0 ? false : true;
	}
	
	
}
