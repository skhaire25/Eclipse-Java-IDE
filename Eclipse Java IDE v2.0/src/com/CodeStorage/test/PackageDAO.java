package com.CodeStorage.test;

import java.util.List;

public interface PackageDAO {
	void addPackage(PackageInfo p);
	void deletePackage(int projectId, String packageName);
	List<PackageInfo> searchPackage(int projectId, String packageName);
	List<PackageInfo> displayAllPackages(int projectId);
	void renamePackage(int projectId, String oldName, String newName);

}
