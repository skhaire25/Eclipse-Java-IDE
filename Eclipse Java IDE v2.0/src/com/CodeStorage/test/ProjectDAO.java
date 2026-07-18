	package com.CodeStorage.test;
	import java.util.List;
	
	public interface ProjectDAO {
		void addProject(Project p);
		void deleteProject(String projectName);
		List<Project> searchProject(String projectName);
		List<Project> displayAllProjects();
		void renameProject(String oldName, String newName);
		void displayProjectTree(int projectId);
	
	}
