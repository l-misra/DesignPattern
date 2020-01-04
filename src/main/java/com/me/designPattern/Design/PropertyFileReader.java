package com.me.designPattern.Design;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

public class PropertyFileReader {

	
	public static void main(String[] args) {
		HashMap<String , Operation> HM_CMDS= new HashMap<>();
		final String path = System.getProperty("user.dir")
				+ "\\src\\main\\java\\com\\me\\designPattern\\Design\\cmds.properties";
		try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
			stream.forEach(s -> {
				String cmdAndClassPath[] = s.split("=");
				try {
					HM_CMDS.put(cmdAndClassPath[0].trim(), (Operation)Class.forName(cmdAndClassPath[1].trim()).newInstance());
				} catch (Exception e) {
					e.printStackTrace();
				} 
				
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(HM_CMDS);

	}

}
