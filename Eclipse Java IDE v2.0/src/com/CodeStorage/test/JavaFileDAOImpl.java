package com.CodeStorage.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JavaFileDAOImpl implements JavaFileDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public void addJavaFile(JavaFile j) {

        try {
            con = DBconnection.getConnection();
            String checkSql = "SELECT * FROM java_file WHERE package_id=? AND file_name=?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, j.getPackageId());
            checkPs.setString(2, j.getFileName());

            ResultSet checkRs = checkPs.executeQuery();
            if (checkRs.next()) {
                System.out.println("Java File already exists in this package!!!");
                checkRs.close();
                checkPs.close();
                con.close();
                return;
            }
            checkRs.close();
            checkPs.close();

            String sql = "INSERT INTO java_file(file_name, source_code, package_id) VALUES(?,?,?)";
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, j.getFileName());
            ps.setString(2, j.getSourceCode());
            ps.setInt(3, j.getPackageId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rsk = ps.getGeneratedKeys();

                if (rsk.next()) {
                    System.out.println("File added successfully!!!");
                    System.out.println("File ID: " + rsk.getInt(1));
                }

                rsk.close();
            } else {
                System.out.println("File not added!!!");
            }
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteJavaFile(int packageId, String fileName) {

        try {
            con = DBconnection.getConnection();
            String sql = "DELETE FROM java_file WHERE package_id=? AND BINARY file_name=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, packageId);
            ps.setString(2, fileName);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("File deleted successfully!!!");
            } else {
                System.out.println("File not found!!!");
            }
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<JavaFile> searchJavaFile(int packageId, String fileName) {
        List<JavaFile> list = new ArrayList<>();

        try {
            con = DBconnection.getConnection();
            String sql = "SELECT * FROM java_file WHERE package_id=? AND LOWER(file_name) LIKE LOWER(?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, packageId);
            ps.setString(2, fileName + "%");

            rs = ps.executeQuery();
            while (rs.next()) {

                JavaFile j = new JavaFile();
                j.setFileId(rs.getInt("file_id"));
                j.setFileName(rs.getString("file_name"));
                j.setSourceCode(rs.getString("source_code"));
                j.setPackageId(rs.getInt("package_id"));
                list.add(j);
            }
            if (list.isEmpty()) {
                System.out.println("File not found!!!");
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
    public List<JavaFile> displayAllJavaFiles(int packageId) {
        List<JavaFile> list = new ArrayList<>();
        try {
            con = DBconnection.getConnection();
            String sql = "SELECT * FROM java_file WHERE package_id=? ORDER BY file_id ASC";
            ps = con.prepareStatement(sql);
            ps.setInt(1, packageId);

            rs = ps.executeQuery();
            while (rs.next()) {

                JavaFile j = new JavaFile();
                j.setFileId(rs.getInt("file_id"));
                j.setFileName(rs.getString("file_name"));
                j.setSourceCode(rs.getString("source_code"));
                j.setPackageId(rs.getInt("package_id"));
                list.add(j);
            }
            if (list.isEmpty()) {
                System.out.println("No Files found!!!");
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
    public void updateSourceCode(int fileId, String sourceCode) {

        try {
            con = DBconnection.getConnection();
            String sql = "UPDATE java_file SET source_code=? WHERE file_id=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, sourceCode);
            ps.setInt(2, fileId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Source Code updated successfully!!!");
            } else {
                System.out.println("File not found!!!");
            }
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    @Override
    public void renameJavaFile(int packageId, String oldName, String newName) {

        try {

            con = DBconnection.getConnection();
            String checkSql = "SELECT * FROM java_file WHERE package_id=? AND file_name=?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, packageId);
            checkPs.setString(2, newName);

            ResultSet checkRs = checkPs.executeQuery();
            if (checkRs.next()) {
                System.out.println("Java File already exists in this package!!!");
                checkRs.close();
                checkPs.close();
                con.close();
                return;
            }
            checkRs.close();
            checkPs.close();


            String selectSql = "SELECT source_code FROM java_file WHERE package_id=? AND file_name=?";
            PreparedStatement selectPs = con.prepareStatement(selectSql);
            selectPs.setInt(1, packageId);
            selectPs.setString(2, oldName);

            ResultSet rs = selectPs.executeQuery();
            String sourceCode = "";
            if (rs.next()) {
                sourceCode = rs.getString("source_code");
            } else {
                System.out.println("Java File not found!!!");
                rs.close();
                selectPs.close();
                con.close();
                return;
            }

            rs.close();
            selectPs.close();

            String oldClass = oldName.replace(".java", "");
            String newClass = newName.replace(".java", "");

            sourceCode = sourceCode.replaceFirst("class " + oldClass,"class " + newClass);
            sourceCode = sourceCode.replaceFirst("interface " + oldClass,"interface " + newClass);

            String sql = "UPDATE java_file SET file_name=?, source_code=? WHERE package_id=? AND BINARY file_name=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, newName);
            ps.setString(2, sourceCode);
            ps.setInt(3, packageId);
            ps.setString(4, oldName);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Java File renamed successfully!!!");
            } else {
                System.out.println("Java File not found!!!");
            }
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}