package com.CodeStorage.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CompilerDAOImpl implements CompilerDAO {

    @Override
    public JavaFile getJavaFileById(int fileId) {

        JavaFile file = null;

        try (Connection con = DBconnection.getConnection()) {

            String sql = "SELECT * FROM java_file WHERE file_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, fileId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                file = new JavaFile();

                file.setFileId(rs.getInt("file_id"));
                file.setFileName(rs.getString("file_name"));
                file.setSourceCode(rs.getString("source_code"));
                file.setPackageId(rs.getInt("package_id"));

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return file;

    }

    @Override
    public PackageInfo getPackageById(int packageId) {

        PackageInfo pkg = null;

        try (Connection con = DBconnection.getConnection()) {

            String sql = "SELECT * FROM package_info WHERE package_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, packageId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                pkg = new PackageInfo();

                pkg.setPackageId(rs.getInt("package_id"));
                pkg.setPackageName(rs.getString("package_name"));
                pkg.setProjectId(rs.getInt("project_id"));

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return pkg;

    }

    @Override
    public List<PackageInfo> getPackagesByProject(int projectId) {

        List<PackageInfo> list = new ArrayList<>();

        try (Connection con = DBconnection.getConnection()) {

            String sql = "SELECT * FROM package_info WHERE project_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, projectId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                PackageInfo pkg = new PackageInfo();

                pkg.setPackageId(rs.getInt("package_id"));
                pkg.setPackageName(rs.getString("package_name"));
                pkg.setProjectId(rs.getInt("project_id"));

                list.add(pkg);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return list;

    }

    @Override
    public List<JavaFile> getJavaFilesByPackage(int packageId) {

        List<JavaFile> list = new ArrayList<>();

        try (Connection con = DBconnection.getConnection()) {

            String sql = "SELECT * FROM java_file WHERE package_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, packageId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                JavaFile file = new JavaFile();

                file.setFileId(rs.getInt("file_id"));
                file.setFileName(rs.getString("file_name"));
                file.setSourceCode(rs.getString("source_code"));
                file.setPackageId(rs.getInt("package_id"));

                list.add(file);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return list;

    }

}