package com.CodeStorage.test;

import java.util.List;

public interface PackageDAO {
	void addPackage(PackageInfo p);
    void deletePackage(int id);
    PackageInfo searchPackage(int id);
    List<PackageInfo> displayAllPackages(int projectId);

}
