package com.CodeStorage.test;

import java.util.List;

public interface CompilerDAO {
    JavaFile getJavaFileById(int fileId);
    PackageInfo getPackageById(int packageId);
    List<PackageInfo> getPackagesByProject(int projectId);
    List<JavaFile> getJavaFilesByPackage(int packageId);

}