package com.stanfield.prototype.bdd.helpers;

import io.restassured.response.Response;


import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;

import io.restassured.path.json.JsonPath;

public class JsonHelper {

	public Object convertJsonToEntity(Map<String, String> extractedJsonObject, Object entity) {
		Object newEntity = new Object();
		if (entity != null) {
			newEntity = entity;
			Set set = extractedJsonObject.entrySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				Map.Entry mentry = (Map.Entry) iterator.next();
				System.out.print("key is: " + mentry.getKey() + " & Value is: ");
				System.out.println(mentry.getValue());
				String key = (String) mentry.getKey();
				if (key != null && key.length() > 0) {
					key = key.replaceFirst(key.substring(0, 1), key.substring(0, 1).toLowerCase());
				}
				try {
					PropertyUtils.setProperty(newEntity, key, mentry.getValue());
				} catch (NoSuchMethodException e) {
					System.out.println(e.getMessage());
				} catch (InvocationTargetException ite) {
					System.out.println(ite.getMessage());
				} catch (IllegalAccessException iae) {
					System.out.println(iae.getMessage());
				}
			}
		}
		return newEntity;
	}
	   
	   public int getVariableFromJsonPath(JsonPath jsonPath, String jsonVariableName) {
		   int value = 0;
		   if (jsonPath != null) {
			   value = jsonPath.getInt(jsonVariableName);
		   }
		   return value;
	   }
	   
	   public JsonPath getJsonPath(Response response) {
		   JsonPath jsonPath = null;
		   if (response != null) {
			   Response extractedResponse = response.then().extract().response();
			   String extractedString = extractedResponse.body().asString();
			   if (extractedString != null && !extractedString.equals("")) {
				   System.out.println("Json extracted from body = " + extractedString);
				   jsonPath = JsonPath.from(extractedString);
			   }
		   }   
		   return jsonPath;
	   }

	   public Map<String, String> getJsonFromList(Response response){
		   Map<String, String> extractedJsonObject = null;
		   JsonPath jsonPath = getJsonPath(response);
		   if (jsonPath != null) {
			   List<Map<String, String>> extractedJsonObjects = jsonPath.getList("$");
			   if(extractedJsonObjects.size() == 1) {
				   extractedJsonObject = extractedJsonObjects.get(0);
			   }
		   }	   
	 	   return extractedJsonObject;
	   }
	   
	   public Map<String, String> getJsonFromMap(Response response) {
		   Map<String, String> extractedJsonObject = null;
		   JsonPath jsonPath = getJsonPath(response);
		   if (jsonPath != null) {
			   extractedJsonObject = jsonPath.getMap("$");
		   }
		   return extractedJsonObject;
	   }
}
