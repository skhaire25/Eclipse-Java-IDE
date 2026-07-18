package com.CodeStorage.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SourceCodeEditor {

    JavaFileDAO dao = new JavaFileDAOImpl();

    public void openEditor(Scanner sc, JavaFile file) {

        int choice;

        do {

            System.out.println("\n============== " + file.getFileName() + " ==============\n");

            String[] lines = file.getSourceCode().split("\n");

            for (int i = 0; i < lines.length; i++) {
                System.out.printf("%2d | %s%n", i + 1, lines[i]);
            }

            System.out.println("\n--------------------------------");
            System.out.println("1. Add Code");
            System.out.println("2. Edit Line");
            System.out.println("3. Delete Line");
            System.out.println("4. Compile & Run");
            System.out.println("5. Back");

            choice = InputValidator.getInt(sc, "Enter your choice: ");

            switch (choice) {

            case 1:

                StringBuilder sb = new StringBuilder();

                System.out.println("Enter code (Type SAVE to save):");

                while (true) {

                    String line = sc.nextLine();

                    if (line.equalsIgnoreCase("SAVE")) {
                        break;
                    }

                    sb.append("        ").append(line).append("\n");

                }

                String oldCode = file.getSourceCode();

                String newCode;

                if (oldCode.contains("public static void main")) {

                    int mainStart = oldCode.indexOf("public static void main");

                    int insertIndex = oldCode.indexOf("}", mainStart);

                    newCode = oldCode.substring(0, insertIndex)
                            + sb.toString()
                            + oldCode.substring(insertIndex);

                } else {

                    int classEnd = oldCode.lastIndexOf("}");

                    newCode = oldCode.substring(0, classEnd)
                            + sb.toString()
                            + oldCode.substring(classEnd);

                }

                dao.updateSourceCode(file.getFileId(), newCode);

                file.setSourceCode(newCode);

                System.out.println("Source Code Saved Successfully!");

                break;

            case 2:

                String[] editLines = file.getSourceCode().split("\n");

                int lineNo = InputValidator.getInt(sc, "Enter Line Number: ");

                if (lineNo < 1 || lineNo > editLines.length) {

                    System.out.println("Invalid Line Number!");
                    break;

                }

                System.out.println("\nCurrent Line:");
                System.out.println(editLines[lineNo - 1]);

                System.out.print("Enter New Line: ");
                String newLine = sc.nextLine();

                editLines[lineNo - 1] = newLine;

                String updatedCode = String.join("\n", editLines);

                dao.updateSourceCode(file.getFileId(), updatedCode);

                file.setSourceCode(updatedCode);

                System.out.println("Line Updated Successfully!");

                break;

            case 3:

                List<String> list = new ArrayList<>(
                        Arrays.asList(file.getSourceCode().split("\n")));

                int deleteLine = InputValidator.getInt(sc, "Enter Line Number: ");

                if (deleteLine < 1 || deleteLine > list.size()) {

                    System.out.println("Invalid Line Number!");
                    break;

                }

                list.remove(deleteLine - 1);

                String code = String.join("\n", list);

                dao.updateSourceCode(file.getFileId(), code);

                file.setSourceCode(code);

                System.out.println("Line Deleted Successfully!");

                break;

            case 4:

                CompilerService compiler = new CompilerService();

                compiler.exportProject(file.getFileId());

                break;

            case 5:

                break;

            default:

                System.out.println("Invalid Choice!");

            }

        } while (choice != 5);

    }

}