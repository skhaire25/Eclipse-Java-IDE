package com.CodeStorage.test;

public class PackageInfo {
	private int packageId;
    private String packageName;
    private int projectId;

    public PackageInfo() {
        super();
    }

    public PackageInfo(int packageId, String packageName, int projectId) {
        super();
        this.packageId = packageId;
        this.packageName = packageName;
        this.projectId = projectId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "\n Package ID: " + packageId + "\n Package Name: " + packageName + "\n Project ID: " + projectId + "\n";
    }

}
