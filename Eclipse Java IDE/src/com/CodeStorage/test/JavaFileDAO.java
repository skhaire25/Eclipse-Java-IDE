package com.CodeStorage.test;

import java.util.List;

public interface JavaFileDAO {
	void addJavaFile(JavaFile j);
    void deleteJavaFile(int id);
    JavaFile searchJavaFile(int id);
    List<JavaFile> displayAllJavaFiles(int packageId);
    void updateSourceCode(int fileId, String sourceCode);

}
