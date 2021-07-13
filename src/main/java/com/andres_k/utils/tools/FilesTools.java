package com.andres_k.utils.tools;

import com.andres_k.utils.configs.GameInfo;

import java.io.*;
import java.util.Scanner;

/**
 * Created by andres_k on 24/03/2015.
 */

public class FilesTools {

    public static boolean validFile(String path) {
        File file = new File(path);

        return (file.exists() && !file.isDirectory());
    }

    public static String readInput(InputStream inputStream) {
        Scanner scan = new Scanner(inputStream).useDelimiter("\\A");

        return (scan.hasNext() ? scan.next() : "");
    }

    public static void writeInInput(InputStream inputStream, String value) {
    }

    public static String readFile(String fileName) {
        File file = new File(fileName);

        if (!file.exists()) {
            ClassLoader classLoader = FilesTools.class.getClassLoader();
            return FilesTools.readInput(classLoader.getResourceAsStream(fileName));
        } else {
            return FilesTools.parseFile(file);
        }
    }

    public static String readTempFile(String fileName) {
        String tempFileName = GameInfo.get().getGamePathTMP() + "/" + fileName;
        File tempFile = new File(tempFileName);

        if (!tempFile.exists()) {
            ClassLoader classLoader = FilesTools.class.getClassLoader();
            return FilesTools.readInput(classLoader.getResourceAsStream(fileName));
        } else {
            return FilesTools.parseFile(tempFile);
        }
    }

    public static String parseFile(File file) {
        StringBuilder content = new StringBuilder();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private static void createParents(String fileName) {
        fileName = fileName.substring(0, fileName.lastIndexOf("/"));
        File file = new File(fileName);

        file.mkdirs();
    }

    public static void writeInFile(String fileName, String value) {
        File file = new File(fileName);

        if (!file.exists())
            createParents(fileName);
        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(value);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeInTempFile(String fileName, String value) {
        fileName = GameInfo.get().getGamePathTMP() + "/" + fileName;
        File file = new File(fileName);
        if (!file.exists())
            createParents(fileName);
        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(value);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFolder(String folderName) {
        folderName = folderName.substring(0, folderName.lastIndexOf("/"));
        File file = new File(folderName);

        file.mkdirs();
    }
}
