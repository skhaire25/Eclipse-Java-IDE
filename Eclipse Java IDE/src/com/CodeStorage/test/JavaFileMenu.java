package com.CodeStorage.test;

import java.util.List;
import java.util.Scanner;

public class JavaFileMenu {
	JavaFileDAO dao=new JavaFileDAOImpl();
	CodeGenerator cg=new CodeGenerator();
	
	public void javaFileMenu(Scanner sc, PackageInfo pi) {
		int choice;
		do {
			System.out.println("\n =============="+pi.getPackageName()+"==============");
			System.out.println("1.Create Class");
			System.out.println("2.Create Interface");
			System.out.println("3.Open Java File");
			System.out.println("4.Delete Java File");
			System.out.println("5.Display Java Files");
			System.out.println("6.Back");
			
			System.out.print("Enter your choice: ");
			choice=sc.nextInt();
			
			switch(choice) {
			case 1:
				sc.nextLine();
				JavaFile j1=new JavaFile();
				System.out.print("Enter Class Name: ");
				String className=sc.nextLine();
				j1.setFileName(className+".java");
				j1.setPackageId(pi.getPackageId());
				
				String code=cg.generateClass(pi.getPackageName(),className);
				j1.setSourceCode(code);
				
				dao.addJavaFile(j1);
				break;
				
			case 2:
				sc.nextLine();
				JavaFile j2=new JavaFile();
				System.out.print("Enter Interface Name: ");
				String interfaceName=sc.nextLine();
				j2.setFileName(interfaceName+".java");
				j2.setPackageId(pi.getPackageId());
							
				String code2=cg.generateInterface(pi.getPackageName(),interfaceName);
				j2.setSourceCode(code2);
				
				dao.addJavaFile(j2);
				break;
			
			case 3:
				System.out.print("Enter File ID: ");
				int id=sc.nextInt();
				
				JavaFile j=dao.searchJavaFile(id);
				if(j != null) {
					SourceCodeEditor editor=new SourceCodeEditor();
					editor.openEditor(sc, j);
				}
				break;
				
			case 4:
				System.out.print("Enter File ID: ");
				int did=sc.nextInt();
				
				dao.deleteJavaFile(did);
				break;
				
			case 5:
				List<JavaFile> l1=dao.displayAllJavaFiles(pi.getPackageId());
				for(JavaFile jf:l1 ) {
					System.out.println("\n "+jf.getFileId() + ". " + jf.getFileName());
				}
				break;
			
			case 6:
				break;
				
			default:
				System.out.print("Invalid Choice!!");
				break;
				
			}
			
		}while(choice!=6);
		
	}

}
