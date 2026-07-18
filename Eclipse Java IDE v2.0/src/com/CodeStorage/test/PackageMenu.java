package com.CodeStorage.test;

import java.util.List;
import java.util.Scanner;

public class PackageMenu {

    PackageDAO dao = new PackageDAOImpl();

    public void packageMenu(Scanner sc, Project project) {

        int choice;

        do {

            System.out.println("\n============== " + project.getProjectName() + " ==============");
            System.out.println("1.Create Package");
            System.out.println("2.Open Package");
            System.out.println("3.Rename Package");
            System.out.println("4.Delete Package");
            System.out.println("5.Display Packages");
            System.out.println("6.Back");

            choice = InputValidator.getInt(sc, "Enter Package ID: ");

            switch (choice) {

            case 1:

                PackageInfo p1 = new PackageInfo();

                System.out.print("Enter Package Name: ");
                String packageName = sc.nextLine();

                if (!InputValidator.ValidPackageName(packageName)) {
                    System.out.println("Invalid Package Name!");
                    break;
                }

                p1.setPackageName(packageName);
                p1.setProjectId(project.getProjectId());

                dao.addPackage(p1);

                break;

            case 2:

                System.out.print("Enter Package Name: ");
                String searchName = sc.nextLine();

                List<PackageInfo> packages = dao.searchPackage(project.getProjectId(), searchName);

                if (packages.isEmpty()) {

                } else {

                    for (PackageInfo p : packages) {
                        System.out.println(p);
                    }

                    int id = InputValidator.getInt(sc, "Enter Package ID to Open: ");

                    PackageInfo selectedPackage = null;

                    for (PackageInfo p : packages) {

                        if (p.getPackageId() == id) {
                            selectedPackage = p;
                            break;
                        }
                    }

                    if (selectedPackage != null) {

                        JavaFileMenu jfm = new JavaFileMenu();
                        jfm.javaFileMenu(sc, selectedPackage);

                    } else {

                        System.out.println("Invalid Package ID.");

                    }

                }

                break;

            case 3:

                System.out.print("Enter Existing Package Name: ");
                String oldName = sc.nextLine();

                System.out.print("Enter New Package Name: ");
                String newName = sc.nextLine();

                if (!InputValidator.ValidPackageName(newName)) {
                    System.out.println("Invalid Package Name!");
                    break;
                }

                dao.renamePackage(project.getProjectId(), oldName, newName);

                break;

            case 4:
                System.out.print("Enter Exact Package Name: ");
                String deletePackageName = sc.nextLine();

                dao.deletePackage(project.getProjectId(), deletePackageName);

                break;

            case 5:

                List<PackageInfo> list = dao.displayAllPackages(project.getProjectId());

                for (PackageInfo p : list) {
                    System.out.println(p);
                }

                break;

            case 6:

                break;

            default:

                System.out.println("Invalid Choice!!");

                break;

            }

        } while (choice != 6);

    }

}