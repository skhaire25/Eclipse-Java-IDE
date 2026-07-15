package com.CodeStorage.test;

import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		ProjectDAO dao=new ProjectDAOImpl();
		
		int choice;
		do {
			System.out.println("\n ==============ECLIPSE JAVA IDE==============");
			System.out.println("1.Create Project");
			System.out.println("2.Open Project");
			System.out.println("3.Delete Project");
			System.out.println("4.Display Projects");
			System.out.println("5.Exit");
			
			System.out.print("Enter your choice: ");
			choice=sc.nextInt();
			
			switch(choice) {
			case 1:
				sc.nextLine();
				Project p1=new Project();
				System.out.print("Enter Project Name: ");
				p1.setProjectName(sc.nextLine());
				
				dao.addProject(p1);
				break;
				
			case 2:
				System.out.print("Enter Project ID: ");
				int id=sc.nextInt();
				
				Project p=dao.searchProject(id);
				if(p != null) {
					PackageMenu pm= new PackageMenu();
					pm.packageMenu(sc, p);
				}
				break;
			
			case 3:
				System.out.print("Enter Project ID: ");
				int did=sc.nextInt();
				
				dao.deleteProject(did);
				break;
				
			case 4:
				List<Project> l1=dao.displayAllProjects();
				for(Project pro:l1 ) {
					System.out.println(pro);
				}
				break;
				
			case 5:
				System.out.println("Exiting...");
				break;
				
			default:
				System.out.print("Invalid Choice!!");
				break;
				
			}
			
		}while(choice!=5);
	}

}
