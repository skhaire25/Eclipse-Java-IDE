package com.CodeStorage.test;

public class CodeGenerator {
	
	public String generateClass(String packageName, String className) {
		String code="package "+packageName+";\n\n"
					+"public class "+className+" {\n\n"
					+"\n"
					+"}";
		return code;
	}
	
	public String generateInterface(String packageName, String className) {
		String code="package "+packageName+";\n\n"
					+"public interface "+className+" {\n\n"
					+"\n"
					+"}";
		return code;
	}

}
