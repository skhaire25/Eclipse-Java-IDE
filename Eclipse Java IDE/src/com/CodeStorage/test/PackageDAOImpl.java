package com.CodeStorage.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PackageDAOImpl implements PackageDAO{
	Connection con;
	PreparedStatement ps;
	ResultSet rs;

	@Override
	public void addPackage(PackageInfo p) {
		// TODO Auto-generated method stub
		try {
			con=DBconnection.getConnection();
			String sql="INSERT INTO package_info(package_name, project_id) VALUES(?,?)";
			ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, p.getPackageName());
			ps.setInt(2, p.getProjectId());

			int rows=ps.executeUpdate();
			if(rows>0){
				ResultSet rsk=ps.getGeneratedKeys();
				if(rsk.next()) {
					System.out.println("Package added successfully!!!");
					System.out.println("Package ID: "+rsk.getInt(1));
				}
			}
			else {
				System.out.println("Package not added!!!");
			}
			
			ps.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deletePackage(int id) {
		// TODO Auto-generated method stub
		try {
			con=DBconnection.getConnection();
			String sql="DELETE FROM package_info WHERE package_id=?";
			ps=con.prepareStatement(sql);
			ps.setInt(1, id);

			int rows=ps.executeUpdate();
			if(rows>0){
				System.out.println("Package deleted successfully!!!");
			}
			else {
				System.out.println("Package not found!!!");
			}
			
			ps.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public PackageInfo searchPackage(int id) {
		// TODO Auto-generated method stub
		try {
			con=DBconnection.getConnection();
			String sql="SELECT*FROM package_info WHERE package_id=?";
			ps=con.prepareStatement(sql);
			ps.setInt(1, id);
			PackageInfo p=null;

			rs=ps.executeQuery();
			if(rs.next()){
				p=new PackageInfo();
				p.setPackageId(rs.getInt("package_id"));
				p.setPackageName(rs.getString("package_name"));
				p.setProjectId(rs.getInt("project_id"));
			}
			else {
				System.out.println("Package not found!!!");
			}
			ps.close();
			con.close();
			return p;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<PackageInfo> displayAllPackages(int projectId) {
		// TODO Auto-generated method stub
		List<PackageInfo> l1 = new ArrayList();
		try {
			con=DBconnection.getConnection();
			String sql = "SELECT*FROM package_info WHERE project_id=? ORDER BY package_id ASC";
			ps=con.prepareStatement(sql);
			ps.setInt(1, projectId);
			
			rs=ps.executeQuery();
			while(rs.next()){
				PackageInfo p=new PackageInfo();
				p.setPackageId(rs.getInt("package_id"));
				p.setPackageName(rs.getString("package_name"));
				p.setProjectId(rs.getInt("project_id"));
				l1.add(p);
			}
			if(l1.isEmpty()){
				System.out.println("No Packages found!!!");
			}
			return l1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
