package com.CodeStorage.test;

import java.util.List;

public interface JavaFileDAO {
	void addJavaFile(JavaFile j);
    void deleteJavaFile(int packageId, String fileName);
    List<JavaFile> searchJavaFile(int packageId, String fileName);
    List<JavaFile> displayAllJavaFiles(int packageId);
    void updateSourceCode(int fileId, String sourceCode);
    void renameJavaFile(int packageId, String oldName, String newName);

}
