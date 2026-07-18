package com.CodeStorage.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PackageDAOImpl implements PackageDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public void addPackage(PackageInfo p) {

        try {

            con = DBconnection.getConnection();

            // Check if package already exists in this project
            String checkSql = "SELECT * FROM package_info WHERE project_id=? AND package_name=?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, p.getProjectId());
            checkPs.setString(2, p.getPackageName());

            ResultSet checkRs = checkPs.executeQuery();

            if (checkRs.next()) {
                System.out.println("Package already exists in this project!!!");
                checkRs.close();
                checkPs.close();
                con.close();
                return;
            }

            checkRs.close();
            checkPs.close();

            String sql = "INSERT INTO package_info(package_name, project_id) VALUES(?,?)";
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, p.getPackageName());
            ps.setInt(2, p.getProjectId());

            int rows = ps.executeUpdate();

            if (rows > 0) {

                ResultSet rsk = ps.getGeneratedKeys();

                if (rsk.next()) {
                    System.out.println("Package added successfully!!!");
                    System.out.println("Package ID: " + rsk.getInt(1));
                }

                rsk.close();

            } else {

                System.out.println("Package not added!!!");

            }

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deletePackage(int projectId, String packageName) {

        try {

            con = DBconnection.getConnection();

            String sql = "DELETE FROM package_info WHERE project_id=? AND BINARY package_name=?";

            ps = con.prepareStatement(sql);

            ps.setInt(1, projectId);
            ps.setString(2, packageName);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Package deleted successfully!!!");
            } else {
                System.out.println("Package not found!!!");
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<PackageInfo> searchPackage(int projectId, String packageName) {

        List<PackageInfo> list = new ArrayList<>();

        try {

            con = DBconnection.getConnection();

            String sql = "SELECT * FROM package_info WHERE project_id=? AND LOWER(package_name) LIKE LOWER(?)";

            ps = con.prepareStatement(sql);

            ps.setInt(1, projectId);
            ps.setString(2, packageName + "%");

            rs = ps.executeQuery();

            while (rs.next()) {

                PackageInfo p = new PackageInfo();

                p.setPackageId(rs.getInt("package_id"));
                p.setPackageName(rs.getString("package_name"));
                p.setProjectId(rs.getInt("project_id"));

                list.add(p);

            }

            if (list.isEmpty()) {
                System.out.println("Package not found!!!");
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
    public List<PackageInfo> displayAllPackages(int projectId) {

        List<PackageInfo> list = new ArrayList<>();

        try {

            con = DBconnection.getConnection();

            String sql = "SELECT * FROM package_info WHERE project_id=? ORDER BY package_id ASC";

            ps = con.prepareStatement(sql);

            ps.setInt(1, projectId);

            rs = ps.executeQuery();

            while (rs.next()) {

                PackageInfo p = new PackageInfo();

                p.setPackageId(rs.getInt("package_id"));
                p.setPackageName(rs.getString("package_name"));
                p.setProjectId(rs.getInt("project_id"));

                list.add(p);

            }

            if (list.isEmpty()) {
                System.out.println("No Packages found!!!");
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
    public void renamePackage(int projectId, String oldName, String newName) {

        try {
            con = DBconnection.getConnection();
            String checkSql = "SELECT * FROM package_info WHERE project_id=? AND package_name=?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, projectId);
            checkPs.setString(2, newName);
            
            ResultSet checkRs = checkPs.executeQuery();
            if (checkRs.next()) {
                System.out.println("Package already exists in this project!!!");
                checkRs.close();
                checkPs.close();
                con.close();
                return;

            }
            checkRs.close();
            checkPs.close();

            String idSql = "SELECT package_id FROM package_info WHERE project_id=? AND package_name=?";
            PreparedStatement idPs = con.prepareStatement(idSql);
            idPs.setInt(1, projectId);
            idPs.setString(2, oldName);

            ResultSet idRs = idPs.executeQuery();
            int packageId = -1;

            if (idRs.next()) {
                packageId = idRs.getInt("package_id");
            } else {
                System.out.println("Package not found!!!");
                idRs.close();
                idPs.close();
                con.close();
                return;
            }
            idRs.close();
            idPs.close();

            String sql = "UPDATE package_info SET package_name=? WHERE project_id=? AND BINARY package_name=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, newName);
            ps.setInt(2, projectId);
            ps.setString(3, oldName);

            int rows = ps.executeUpdate();
            ps.close();

            if (rows > 0) {
                String fileSql = "SELECT file_name, source_code FROM java_file WHERE package_id=?";
                PreparedStatement filePs = con.prepareStatement(fileSql);
                filePs.setInt(1, packageId);
                ResultSet fileRs = filePs.executeQuery();

                while (fileRs.next()) {
                    String fileName = fileRs.getString("file_name");
                    String sourceCode = fileRs.getString("source_code");

                    sourceCode = sourceCode.replace("package " + oldName + ";","package " + newName + ";");

                    String updateSql = "UPDATE java_file SET source_code=? WHERE package_id=? AND file_name=?";
                    PreparedStatement updatePs = con.prepareStatement(updateSql);
                    updatePs.setString(1, sourceCode);
                    updatePs.setInt(2, packageId);
                    updatePs.setString(3, fileName);
                    updatePs.executeUpdate();
                    updatePs.close();
                }

                fileRs.close();
                filePs.close();
                System.out.println("Package renamed successfully!!!");

            } else {

                System.out.println("Package not found!!!");

            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}