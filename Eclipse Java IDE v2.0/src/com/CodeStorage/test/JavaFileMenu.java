package com.CodeStorage.test;

import java.util.List;
import java.util.Scanner;

public class JavaFileMenu {

    JavaFileDAO dao = new JavaFileDAOImpl();
    CodeGenerator cg = new CodeGenerator();

    public void javaFileMenu(Scanner sc, PackageInfo pi) {

        int choice;

        do {

            System.out.println("\n============== " + pi.getPackageName() + " ==============");
            System.out.println("1.Create Class");
            System.out.println("2.Create Interface");
            System.out.println("3.Open Java File");
            System.out.println("4.Rename Java File");
            System.out.println("5.Delete Java File");
            System.out.println("6.Display Java Files");
            System.out.println("7.Back");

            choice = InputValidator.getInt(sc, "Enter your choice: ");

            switch (choice) {

            case 1:

                JavaFile j1 = new JavaFile();

                System.out.print("Enter Class Name: ");
                String className = sc.nextLine();

                if (!InputValidator.ValidJavaFileName(className)) {
                    System.out.println("Class name must start with a capital letter and contain no spaces or special characters.");
                    break;
                }

                System.out.print("Add main() method? (Y/N): ");
                String option = sc.nextLine();

                boolean addMain = option.equalsIgnoreCase("Y");

                j1.setFileName(className + ".java");
                j1.setPackageId(pi.getPackageId());

                String code = cg.generateClass(
                        pi.getPackageName(),
                        className,
                        addMain);

                j1.setSourceCode(code);

                dao.addJavaFile(j1);

                break;

            case 2:

                JavaFile j2 = new JavaFile();

                System.out.print("Enter Interface Name: ");
                String interfaceName = sc.nextLine();

                if (!InputValidator.ValidJavaFileName(interfaceName)) {
                    System.out.println("Interface name must start with a capital letter and contain no spaces or special characters.");
                    break;
                }

                j2.setFileName(interfaceName + ".java");
                j2.setPackageId(pi.getPackageId());

                String code2 = cg.generateInterface(pi.getPackageName(), interfaceName);
                j2.setSourceCode(code2);

                dao.addJavaFile(j2);

                break;

            case 3:

                System.out.print("Enter File Name: ");
                String searchName = sc.nextLine();

                List<JavaFile> files = dao.searchJavaFile(pi.getPackageId(), searchName);

                if (!files.isEmpty()) {

                    for (JavaFile j : files) {
                        System.out.println(j);
                    }

                    int id = InputValidator.getInt(sc, "Enter File ID to Open: ");

                    JavaFile selectedFile = null;

                    for (JavaFile j : files) {

                        if (j.getFileId() == id) {
                            selectedFile = j;
                            break;
                        }

                    }

                    if (selectedFile != null) {

                        SourceCodeEditor editor = new SourceCodeEditor();
                        editor.openEditor(sc, selectedFile);

                    } else {

                        System.out.println("Invalid File ID.");

                    }

                }

                break;

            case 4:

                System.out.print("Enter Existing File Name: ");
                String oldName = sc.nextLine();

                System.out.print("Enter New File Name: ");
                String newName = sc.nextLine();

                if (!InputValidator.ValidJavaFileName(newName)) {
                    System.out.println("Invalid Java File Name!");
                    break;
                }

                dao.renameJavaFile(
                        pi.getPackageId(),
                        oldName + ".java",
                        newName + ".java");

                break;

            case 5:

                System.out.print("Enter Exact File Name: ");
                String deleteFileName = sc.nextLine() + ".java";

                dao.deleteJavaFile(pi.getPackageId(), deleteFileName);

                break;

            case 6:

                List<JavaFile> l1 = dao.displayAllJavaFiles(pi.getPackageId());

                for (JavaFile jf : l1) {
                    System.out.println("\n" + jf.getFileId() + ". " + jf.getFileName());
                }

                break;

            case 7:

                break;

            default:

                System.out.println("Invalid Choice!!");

                break;

            }

        } while (choice != 7);

    }

}