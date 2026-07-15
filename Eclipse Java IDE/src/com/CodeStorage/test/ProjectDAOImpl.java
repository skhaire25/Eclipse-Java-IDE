package com.CodeStorage.test;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAOImpl implements ProjectDAO{
	Connection con;
	PreparedStatement ps;
	ResultSet rs;

	@Override
	public void addProject(Project p) {
		// TODO Auto-generated method stub
		try {
			con=DBconnection.getConnection();
			String sql="INSERT INTO project(project_name) VALUES(?)";
			ps=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, p.getProjectName());

			int rows=ps.executeUpdate();
			if(rows>0){
				ResultSet rsk=ps.getGeneratedKeys();
				if(rsk.next()) {
					System.out.println("Project added successfully!!!");
					System.out.println("Project ID: "+rsk.getInt(1));
				}
			}
			else {
				System.out.println("Project not added!!!");
			}
			
			ps.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteProject(int id) {
		// TODO Auto-generated method stub
		try {
			con=DBconnection.getConnection();
			String sql="DELETE FROM project WHERE project_id=?";
			ps=con.prepareStatement(sql);
			ps.setInt(1, id);

			int rows=ps.executeUpdate();
			if(rows>0){
				System.out.println("Project deleted successfully!!!");
			}
			else {
				System.out.println("Project not found!!!");
			}
			
			ps.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Project searchProject(int id) {
		// TODO Auto-generated method stub
		try {
			con=DBconnection.getConnection();
			String sql="SELECT*FROM project WHERE project_id=?";
			ps=con.prepareStatement(sql);
			ps.setInt(1, id);
			Project p=null;

			rs=ps.executeQuery();
			if(rs.next()){
				p=new Project();
				p.setProjectId(rs.getInt("project_id"));
				p.setProjectName(rs.getString("project_name"));
			}
			else {
				System.out.println("Project not found!!!");
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
	public List<Project> displayAllProjects() {
		// TODO Auto-generated method stub
		List<Project> l1 = new ArrayList();
		try {
			con=DBconnection.getConnection();
			String sql="SELECT*FROM project";
			ps=con.prepareStatement(sql);
			
			rs=ps.executeQuery();
			while(rs.next()){
				Project p=new Project();
				p.setProjectId(rs.getInt("project_id"));
				p.setProjectName(rs.getString("project_name"));
				l1.add(p);
			}
			if(l1.isEmpty()){
				System.out.println("No Projects found!!!");
			}
			return l1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
