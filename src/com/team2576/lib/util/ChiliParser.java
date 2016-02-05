package com.team2576.lib.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class ChiliParser {
	
	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("name", "foo");
		json.put("num", 100);
		System.out.println(json);
		// Print filesystem for idiot-proof measures
		try {
			Files.walk(Paths.get("")).forEach(filePath -> {
			    if (Files.isRegularFile(filePath)) {
			        System.out.println(filePath);
			    }
			});
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Parse input
		try {
			Map<?, ?> data = (Map<?, ?>) JSONValue.parse(new FileReader("config/es_LA.json"));
			System.out.println(((Map<?, ?>)data.get("translations")).get("Estado"));
			Map<?, ?> data2 = (Map<?, ?>) JSONValue.parse(new FileReader("config/config.json"));
			System.out.println(data2.get("local"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
