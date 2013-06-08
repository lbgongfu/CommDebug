package com.lbgongfu.commdebug.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class FileHelper {
	private String workspace = System.getProperty("user.home");
	
	public FileHelper() {
		
	}
	
	public boolean isWorkspaceSeted() {
		Path path = Paths.get("config.properties");
		if (!path.toFile().exists()) {
			return false;
		}
		readWorkspace();
		return true;
	}
	
	private void readWorkspace() {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("config.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.workspace = props.getProperty("workspace");
	}

	public static void main(String[] args) {
		new FileHelper().isWorkspaceSeted();
	}
}
