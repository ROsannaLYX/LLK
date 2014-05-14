package nec.soft.java.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHelper {
	public static String folder = "resource";

	private static void checkFolder() {
		File file = new File(folder);
		if (!file.exists()) {
			file.mkdir();
		}
	}

	public static void writeToFile(String fileName, String message) {
		writeToFile(fileName, message, false);
	}

	public static void writeToFile(String fileName, String message, boolean append) {
		try {
			checkFolder();
			File file = new File(folder + "/" + fileName);
			if (!file.exists())
				file.createNewFile();
			FileWriter writer = new FileWriter(file, append);
			BufferedWriter bWriter = new BufferedWriter(writer);
			bWriter.write(message);
			writer.close();
			bWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readFromFile(String fileName) {
		try {
			checkFolder();
			File file = new File(folder + "/" + fileName);
			BufferedReader br = new BufferedReader(new FileReader(file));
			StringBuffer sb = new StringBuffer();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
