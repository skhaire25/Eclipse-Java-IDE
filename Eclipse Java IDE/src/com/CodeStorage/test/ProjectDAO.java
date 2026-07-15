	package com.CodeStorage.test;
	import java.util.List;
	
	public interface ProjectDAO {
		void addProject(Project p);
		void deleteProject(int id);
		Project searchProject(int id);
		List<Project> displayAllProjects();
	
	}
