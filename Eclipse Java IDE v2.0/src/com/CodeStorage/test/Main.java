package com.CodeStorage.test;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ProjectDAO dao = new ProjectDAOImpl();

        int choice;

        do {

            System.out.println("\n============== ECLIPSE JAVA IDE ==============");
            System.out.println("1.Create Project");
            System.out.println("2.Open Project");
            System.out.println("3.Rename Project");
            System.out.println("4.Delete Project");
            System.out.println("5.Display Projects");
            System.out.println("6.Project Tree");
            System.out.println("7.Exit");

            choice = InputValidator.getInt(sc, "Enter your choice: ");

            switch (choice) {

            case 1:

                Project p1 = new Project();

                System.out.print("Enter Project Name: ");
                String projectName = sc.nextLine();

                if (!InputValidator.ValidProjectName(projectName)) {
                    System.out.println("Invalid Project Name!");
                    break;
                }

                p1.setProjectName(projectName);

                dao.addProject(p1);

                break;

            case 2:

                System.out.print("Enter Project Name: ");
                String searchName = sc.nextLine();

                List<Project> projects = dao.searchProject(searchName);

                if (projects.isEmpty()) {

                } else {

                    for (Project p : projects) {
                        System.out.println(p);
                    }

                    System.out.print("Enter Project ID to Open: ");
                    int id = sc.nextInt();

                    Project selectedProject = null;

                    for (Project p : projects) {
                        if (p.getProjectId() == id) {
                            selectedProject = p;
                            break;
                        }
                    }

                    if (selectedProject != null) {

                        PackageMenu pm = new PackageMenu();
                        pm.packageMenu(sc, selectedProject);

                    } else {

                        System.out.println("Invalid Project ID.");

                    }

                }

                break;

            case 3:

                System.out.print("Enter Existing Project Name: ");
                String oldName = sc.nextLine();

                System.out.print("Enter New Project Name: ");
                String newName = sc.nextLine();

                if (!InputValidator.ValidProjectName(newName)) {
                    System.out.println("Invalid Project Name!");
                    break;
                }

                dao.renameProject(oldName, newName);

                break;

            case 4:

                System.out.print("Enter Exact Project Name: ");
                String deleteProjectName = sc.nextLine();

                dao.deleteProject(deleteProjectName);

                break;

            case 5:

                List<Project> l1 = dao.displayAllProjects();

                for (Project pro : l1) {
                    System.out.println(pro);
                }

                break;

            case 6:

                List<Project> l2 = dao.displayAllProjects();

                System.out.println("\nID\tProject Name");
                System.out.println("--------------------------");

                for (Project p : l2) {
                    System.out.println(p.getProjectId() + "\t" + p.getProjectName());

                }

                int projectId = InputValidator.getInt(sc, "\nEnter Project ID: ");

                dao.displayProjectTree(projectId);

                break;
               
            case 7:
            	System.out.println("Exiting...");

            	break;

            default:

                System.out.println("Invalid Choice!!");

                break;

            }

        } while (choice != 7);

        sc.close();
    }

}