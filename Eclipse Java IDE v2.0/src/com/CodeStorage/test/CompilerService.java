package com.CodeStorage.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class CompilerService {

    private CompilerDAO dao = new CompilerDAOImpl();

    public void exportProject(int fileId) {

        try {

            File workspace = new File("TempWorkspace");

            if (workspace.exists()) {
                deleteDirectory(workspace);
            }

            workspace.mkdirs();

            JavaFile currentFile = dao.getJavaFileById(fileId);

            if (currentFile == null) {
                System.out.println("Java File Not Found!");
                return;
            }

            PackageInfo currentPackage = dao.getPackageById(currentFile.getPackageId());

            if (currentPackage == null) {
                System.out.println("Package Not Found!");
                return;
            }

            int projectId = currentPackage.getProjectId();

            List<PackageInfo> packages = dao.getPackagesByProject(projectId);

            for (PackageInfo pkg : packages) {

                String folderPath = "TempWorkspace"
                        + File.separator
                        + pkg.getPackageName().replace('.', File.separatorChar);

                File packageFolder = new File(folderPath);

                packageFolder.mkdirs();

                List<JavaFile> files = dao.getJavaFilesByPackage(pkg.getPackageId());

                for (JavaFile file : files) {

                    String fileName = file.getFileName();

                    if (!fileName.endsWith(".java")) {
                        fileName += ".java";
                    }

                    File javaFile = new File(packageFolder, fileName);

                    FileWriter writer = new FileWriter(javaFile);

                    writer.write(file.getSourceCode());

                    writer.close();

                }

            }

            System.out.println("\nProject Exported Successfully!");
            System.out.println(workspace.getAbsolutePath());

            if (compileProject()) {

                runProject();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public boolean compileProject() {

        File workspace = new File("TempWorkspace");

        List<File> javaFiles = new ArrayList<>();

        getJavaFiles(workspace, javaFiles);

        if (javaFiles.isEmpty()) {

            System.out.println("No Java Files Found.");

            return false;

        }

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        if (compiler == null) {

            System.out.println("JDK Not Found.");

            return false;

        }

        StandardJavaFileManager fileManager =
                compiler.getStandardFileManager(null, null, null);

        Iterable compilationUnits =
                fileManager.getJavaFileObjectsFromFiles(javaFiles);

        boolean success = compiler.getTask(
                null,
                fileManager,
                null,
                null,
                null,
                compilationUnits).call();

        try {

            fileManager.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

        if (success) {

            System.out.println("\nCompilation Successful!");

        } else {

            System.out.println("\nCompilation Failed!");

        }

        return success;

    }

    public void runProject() {

        File workspace = new File("TempWorkspace");

        String mainClass = findMainClass(workspace, "");

        if (mainClass == null) {

            System.out.println("Main Method Not Found.");

            return;

        }

        try {

            ProcessBuilder pb = new ProcessBuilder(
                    "java",
                    "-cp",
                    workspace.getAbsolutePath(),
                    mainClass);

            pb.redirectErrorStream(true);

            Process process = pb.start();

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    process.getInputStream()));

            String line;

            System.out.println("\n========== Program Output ==========\n");

            while ((line = reader.readLine()) != null) {

                System.out.println(line);

            }

            process.waitFor();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private String findMainClass(File folder, String packageName) {

        File[] files = folder.listFiles();

        if (files == null)
            return null;

        for (File file : files) {

            if (file.isDirectory()) {

                String pkg = packageName.isEmpty()
                        ? file.getName()
                        : packageName + "." + file.getName();

                String result = findMainClass(file, pkg);

                if (result != null)
                    return result;

            } else if (file.getName().endsWith(".java")) {

                try {

                    String source = Files.readString(file.toPath());

                    if (source.contains("public static void main")) {

                        String className =
                                file.getName().replace(".java", "");

                        if (packageName.isEmpty()) {

                            return className;

                        }

                        return packageName + "." + className;

                    }

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

        }

        return null;

    }

    private void getJavaFiles(File folder, List<File> javaFiles) {

        File[] files = folder.listFiles();

        if (files == null)
            return;

        for (File file : files) {

            if (file.isDirectory()) {

                getJavaFiles(file, javaFiles);

            } else if (file.getName().endsWith(".java")) {

                javaFiles.add(file);

            }

        }

    }

    private void deleteDirectory(File file) {

        if (file.isDirectory()) {

            File[] files = file.listFiles();

            if (files != null) {

                for (File f : files) {

                    deleteDirectory(f);

                }

            }

        }

        file.delete();

    }

}