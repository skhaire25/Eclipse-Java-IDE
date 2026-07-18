package com.CodeStorage.test;

public class CodeGenerator {

    public String generateClass(String packageName, String className, boolean addMain) {

        String code;

        if (addMain) {

            code = "package " + packageName + ";\n\n"
                    + "public class " + className + " {\n\n"
                    + "    public static void main(String[] args) {\n\n"
                    + "    }\n"
                    + "}";

        } else {

            code = "package " + packageName + ";\n\n"
                    + "public class " + className + " {\n\n"
                    + "}";

        }

        return code;
    }

    public String generateInterface(String packageName, String interfaceName) {

        String code = "package " + packageName + ";\n\n"
                + "public interface " + interfaceName + " {\n\n"
                + "}";

        return code;
    }

}