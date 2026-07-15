package com.CodeStorage.test;

import java.util.Scanner;

public class SourceCodeEditor {
	
	JavaFileDAO dao=new JavaFileDAOImpl();
	
	public void openEditor(Scanner sc, JavaFile file) {
		int choice;
		do {
			System.out.println("\n =============="+file.getFileName()+"==============");
			System.out.println(file.getSourceCode());
			System.out.println("\n1.Edit Source Code");
			System.out.println("2.Back");
			
			System.out.print("Enter your choice: ");
			choice=sc.nextInt();
			sc.nextLine();
			
			switch(choice) {
			case 1:
				StringBuilder sb=new StringBuilder();
				System.out.println("Enter code (type SAVE to finish and save code):");
				
				while(true) {
					String line =sc.nextLine();
					if(line.equalsIgnoreCase("SAVE")) {
						break;
					}
					sb.append("    ").append(line).append("\n");
				}
				
				String oldCode= file.getSourceCode();
				
				int index=oldCode.lastIndexOf("}");
				
				String newCode= oldCode.substring(0,index)+sb.toString()+"}";
				dao.updateSourceCode(file.getFileId(), newCode);
				file.setSourceCode(newCode);
				System.out.println("Source Code Saved Successfully!!!");
				break;
				
			case 2:
				break;
				
			default:
				System.out.print("Invalid Choice!!");
				break;
				
			}
			
		}while(choice!=2);
		
	}

}
