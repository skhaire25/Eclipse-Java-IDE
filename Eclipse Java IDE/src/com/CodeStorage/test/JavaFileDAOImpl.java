package com.CodeStorage.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JavaFileDAOImpl implements JavaFileDAO{
	Connection con;
	PreparedStatement ps;
	ResultSet rs;

	@Override
	public void addJavaFile(JavaFile j) {
		// TODO Auto-generated method stub
		try {
			con=DBconnection.getConnection();
			String sql="INSERT INTO java_file(file_name, source_code, package_id) VALUES(?,?,?)";
			ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, j.getFileName());
			ps.setString(2, j.getSourceCode());
			ps.setInt(3, j.getPackageId());

			int rows=ps.executeUpdate();
			if(rows>0){
				ResultSet rsk=ps.getGeneratedKeys();
				if(rsk.next()) {
					System.out.println("File added successfully!!!");
					System.out.println("File ID: "+rsk.getInt(1));
				}
			}
			else {
				System.out.println("File not added!!!");
			}
			
			ps.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteJavaFile(int id) {
		// TODO Auto-generated method stub
		try {
			con=DBconnection.getConnection();
			String sql="DELETE FROM java_file WHERE file_id=?";
			ps=con.prepareStatement(sql);
			ps.setInt(1, id);

			int rows=ps.executeUpdate();
			if(rows>0){
				System.out.println("File deleted successfully!!!");
			}
			else {
				System.out.println("File not found!!!");
			}
			
			ps.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public JavaFile searchJavaFile(int id) {
		// TODO Auto-generated method stub
		try {
			con=DBconnection.getConnection();
			String sql="SELECT*FROM java_file WHERE file_id=?";
			ps=con.prepareStatement(sql);
			ps.setInt(1, id);
			JavaFile j=null;

			rs=ps.executeQuery();
			if(rs.next()){
				j=new JavaFile();
				j.setFileId(rs.getInt("file_id"));
				j.setFileName(rs.getString("file_name"));
				j.setSourceCode(rs.getString("source_code"));
				j.setPackageId(rs.getInt("package_id"));
			}
			else {
				System.out.println("File not found!!!");
			}
			ps.close();
			con.close();
			return j;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<JavaFile> displayAllJavaFiles(int packageId) {
		// TODO Auto-generated method stub
		List<JavaFile> l1 = new ArrayList();
		try {
			con=DBconnection.getConnection();
			String sql="SELECT*FROM java_file WHERE package_id=?";
			ps=con.prepareStatement(sql);
			ps.setInt(1, packageId);
			
			rs=ps.executeQuery();
			while(rs.next()){
				JavaFile j=new JavaFile();
				j.setFileId(rs.getInt("file_id"));
				j.setFileName(rs.getString("file_name"));
				j.setSourceCode(rs.getString("source_code"));
				j.setPackageId(rs.getInt("package_id"));
				l1.add(j);
			}
			if(l1.isEmpty()){
				System.out.println("No Files found!!!");
			}
			return l1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateSourceCode(int fileId, String sourceCode) {
		// TODO Auto-generated method stub
		try {
			con=DBconnection.getConnection();
			String sql="UPDATE java_file SET source_code=? WHERE file_id=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, sourceCode);
			ps.setInt(2, fileId);
			JavaFile j=null;

			int rows=ps.executeUpdate();
			if(rows>0){
				System.out.println("Source Code updated successfully!!!");
			}
			else {
				System.out.println("File not found!!!");
			}
			
			ps.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
