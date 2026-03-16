package com.dao;

import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.model.UserModel;
import com.util.DBUtil;

public class userdao 
{
	Connection cn=null;
	public int userregistration(UserModel rmodel)
	{
		int x=0;
		cn=new DBUtil().getConnection();
		
		String qry="insert into userregister(firstname,lastname,email,mobileno,address,city,state,pincode,password,role) values(?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement st=cn.prepareStatement(qry);
			st.setString(1,rmodel.getFirstname());
			st.setString(2,rmodel.getLastname());
			st.setString(3,rmodel.getEmail());
			st.setString(4,rmodel.getContact());
			st.setString(5,rmodel.getAddress());
			st.setString(+6,rmodel.getCity());
			st.setString(7,rmodel.getState());
			st.setInt(8, rmodel.getPincode());
			st.setString(9,rmodel.getPassword());
			st.setString(10,rmodel.getRole());
			
			System.out.println("Executing insert for user: " + rmodel.getFirstname() + ", " + rmodel.getEmail());
	        x = st.executeUpdate();
	        System.out.println("executeUpdate returned: " + x);

	    } catch (SQLException e) {
	        System.out.println("SQL Exception: " + e.getMessage());
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try { cn.close(); } catch (Exception e) { e.printStackTrace(); }
	    }
		
		
		return x;
	}
	public UserModel UserLogin(UserModel lmodel)
	{
		UserModel Model=null;
		cn=new DBUtil().getConnection();
		String qry="select * from userregister where email=? and password=?";
		
		try {
			PreparedStatement st=cn.prepareStatement(qry);
			st.setString(1,lmodel.getEmail());
			st.setString(2,lmodel.getPassword());
			ResultSet rs=st.executeQuery();
			
				if(rs.next()) {
				    Model = new UserModel();
				    Model.setUserid(rs.getInt("userid"));
				    Model.setFirstname(rs.getString("firstname"));
				    Model.setLastname(rs.getString("lastname"));
				    Model.setEmail(rs.getString("email"));
				    Model.setContact(rs.getString("mobileno")); 
				    Model.setAddress(rs.getString("address"));
				    Model.setCity(rs.getString("city"));
				    Model.setState(rs.getString("state"));
				    Model.setPincode(rs.getInt("pincode"));
				    Model.setPassword(rs.getString("password"));
				    Model.setRole(rs.getString("role"));
				}
			cn.close();
		} 
		catch (Exception e) 
		{
			
			e.printStackTrace();
		}
		
		
		return Model;
	}
	public int updateUser(UserModel m) {
	    int result = 0;
	    try {
	        cn = new DBUtil().getConnection();
	       
	        String sql = "UPDATE userregister SET firstname=?, lastname=?, mobileno=?, address=?, city=?, state=?, pincode=? WHERE userid=?";
	        PreparedStatement ps = cn.prepareStatement(sql);
	        ps.setString(1, m.getFirstname());
	        ps.setString(2, m.getLastname());
	        ps.setString(3, m.getContact());
	        ps.setString(4, m.getAddress());
	        ps.setString(5, m.getCity());
	        ps.setString(6, m.getState());
	        ps.setInt(7, m.getPincode());
	        ps.setInt(8, m.getUserid());
	        
	        result = ps.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
}