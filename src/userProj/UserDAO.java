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
	public UserVO updateUser(UserVO vo) {
		conn = DBCon.getConnect();
		PreparedStatement psmt = null;
		String sql = "update user_temp set User_phone=? where user_id=?";
		String sql2 = "select * from user_temp where user_id = ?";
		UserVO rvo = new UserVO();
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getUserPhone());
			psmt.setString(2, vo.getUserId());
			
			int r =  psmt.executeUpdate();
			System.out.println(r + "건 수정.");
			
			//수정한 데이터
			psmt = conn.prepareStatement(sql2);
			psmt.setString(1, vo.getUserId());
			rs = psmt.executeQuery();
			if(rs.next()) {
				rvo.setUserPhone(rs.getString("user_phone"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return rvo;
	}
	
	
}
