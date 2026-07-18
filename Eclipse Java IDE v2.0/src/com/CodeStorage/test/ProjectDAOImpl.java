package com.CodeStorage.test;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAOImpl implements ProjectDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public void addProject(Project p) {

        try {
            con = DBconnection.getConnection();
            String checkSql = "SELECT * FROM project WHERE project_name = ?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setString(1, p.getProjectName());

            ResultSet checkRs = checkPs.executeQuery();
            if (checkRs.next()) {
                System.out.println("Project already exists!!!");
                checkRs.close();
                checkPs.close();
                con.close();
                return;
            }
            checkRs.close();
            checkPs.close();

            String sql = "INSERT INTO project(project_name) VALUES(?)";
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getProjectName());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rsk = ps.getGeneratedKeys();

                if (rsk.next()) {
                    System.out.println("Project added successfully!!!");
                    System.out.println("Project ID: " + rsk.getInt(1));
                }
                rsk.close();
            } else {
                System.out.println("Project not added!!!");
            }

            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProject(String projectName) {

        try {
            con = DBconnection.getConnection();
            String sql = "DELETE FROM project WHERE BINARY project_name = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, projectName);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Project deleted successfully!!!");
            } else {
                System.out.println("Project not found!!!");
            }
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Project> searchProject(String projectName) {
        List<Project> list = new ArrayList<>();
        try {
            con = DBconnection.getConnection();
            String sql = "SELECT * FROM project WHERE LOWER(project_name) LIKE LOWER(?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, projectName + "%");

            rs = ps.executeQuery();
            while (rs.next()) {
                Project p = new Project();
                p.setProjectId(rs.getInt("project_id"));
                p.setProjectName(rs.getString("project_name"));

                list.add(p);
            }
            if (list.isEmpty()) {
                System.out.println("Project not found!!!");
            }
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Project> displayAllProjects() {
        List<Project> list = new ArrayList<>();
        try {
            con = DBconnection.getConnection();
            String sql = "SELECT * FROM project ORDER BY project_id ASC";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Project p = new Project();
                p.setProjectId(rs.getInt("project_id"));
                p.setProjectName(rs.getString("project_name"));
                list.add(p);
            }

            if (list.isEmpty()) {
                System.out.println("No Projects found!!!");
            }
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public void renameProject(String oldName, String newName) {

        try {
            con = DBconnection.getConnection();
            String checkSql = "SELECT * FROM project WHERE project_name=?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setString(1, newName);

            ResultSet checkRs = checkPs.executeQuery();
            if (checkRs.next()) {
                System.out.println("Project already exists!!!");
                checkRs.close();
                checkPs.close();
                con.close();
                return;
            }
            checkRs.close();
            checkPs.close();

            String sql = "UPDATE project SET project_name=? WHERE BINARY project_name=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, newName);
            ps.setString(2, oldName);

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Project renamed successfully!!!");
            else
                System.out.println("Project not found!!!");

            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void displayProjectTree(int projectId) {

        try {
            con = DBconnection.getConnection();
            String projectSql = "SELECT project_name FROM project WHERE project_id=?";
            PreparedStatement projectPs = con.prepareStatement(projectSql);
            projectPs.setInt(1, projectId);

            ResultSet projectRs = projectPs.executeQuery();
            if (!projectRs.next()) {
                System.out.println("Project not found!!!");
                projectRs.close();
                projectPs.close();
                con.close();
                return;
            }
            String projectName = projectRs.getString("project_name");
            System.out.println("\n========== PROJECT TREE ==========");
            System.out.println(projectName);

            projectRs.close();
            projectPs.close();

            String packageSql = "SELECT package_id, package_name FROM package_info WHERE project_id=?";
            PreparedStatement packagePs = con.prepareStatement(packageSql);
            packagePs.setInt(1, projectId);

            ResultSet packageRs = packagePs.executeQuery();
            while (packageRs.next()) {
                int packageId = packageRs.getInt("package_id");
                String packageName = packageRs.getString("package_name");
                System.out.println("├── " + packageName);
                String fileSql = "SELECT file_name FROM java_file WHERE package_id=?";
                PreparedStatement filePs = con.prepareStatement(fileSql);
                filePs.setInt(1, packageId);

                ResultSet fileRs = filePs.executeQuery();
                while (fileRs.next()) {
                    System.out.println("│   ├── " + fileRs.getString("file_name"));

                }
                fileRs.close();
                filePs.close();
            }
            packageRs.close();
            packagePs.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}