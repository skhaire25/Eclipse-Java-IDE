package com.CodeStorage.test;

import java.util.List;
import java.util.Scanner;

public class PackageMenu {
	PackageDAO dao=new PackageDAOImpl();
	
	public void packageMenu(Scanner sc, Project project) {
		
		int choice;
		do {
			System.out.println("\n =============="+project.getProjectName()+"==============");
			System.out.println("1.Create Package");
			System.out.println("2.Open Package");
			System.out.println("3.Delete Package");
			System.out.println("4.Display Packages");
			System.out.println("5.Back");
			
			System.out.print("Enter your choice: ");
			choice=sc.nextInt();
			
			switch(choice) {
			case 1:
				sc.nextLine();
				PackageInfo p1=new PackageInfo();
				System.out.print("Enter Package Name: ");
				p1.setPackageName(sc.nextLine());
				p1.setProjectId(project.getProjectId());
				
				dao.addPackage(p1);
				break;
				
			case 2:
				System.out.print("Enter Package ID: ");
				int id=sc.nextInt();
				
				PackageInfo p=dao.searchPackage(id);
				if(p != null) {
					JavaFileMenu jfm= new JavaFileMenu();
					jfm.javaFileMenu(sc, p);
				}
				break;
			
			case 3:
				System.out.print("Enter Package ID: ");
				int did=sc.nextInt();
				
				dao.deletePackage(did);
				break;
				
			case 4:
				List<PackageInfo> l1=dao.displayAllPackages(project.getProjectId());
				for(PackageInfo pro:l1 ) {
					System.out.println(pro);
				}
				break;
				
			case 5:
				break;
				
			default:
				System.out.print("Invalid Choice!!");
				break;
				
			}
			
		}while(choice!=5);
	}

}
