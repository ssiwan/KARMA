package com.stanfield.prototype.bdd.helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import io.restassured.RestAssured;

public class RestConfig {
	   
	public String getURI() {
	       // Allows for setting the URI at the maven command level.
	       // Example: mvn verify -Dserver.port=9000 -Dserver.host=http://localhost -Dapplication.base=/api/
		   // If not set at the maven command level, URI is retrieved from property file.
	       Properties props = getProperties();
	       RestAssured.port = getPort(props);
	       RestAssured.basePath = getBasePath(props);
	       String host = getHost(props);
	       RestAssured.baseURI = host + ":" + RestAssured.port + RestAssured.basePath;
	       System.out.println("URI = " + RestAssured.baseURI);
	       return RestAssured.baseURI;
	   }
	
	public Properties getProperties() {
	       Properties props; 
	       Properties defaults = new Properties(); 
	       props = new Properties(defaults);
	       
	       try {
	           InputStream stream = new FileInputStream("./restassured.properties");
	           try { 

	               props.load(stream); 
	           } finally { 
	               stream.close(); 
	           } 
	       } catch (IOException e) { 
	    	   e.printStackTrace(); 
	       }
	       return props;
	}
	
	public int getPort(Properties props) {
		int portNumber = 4200; // default to Spring Boot port
	    String port = props.getProperty("server.port");
	    String systemPort = System.getProperty("server.port");
	    if (systemPort == null) {
	    	System.out.println("Retrieving port = " + port + " from property file");
	    	portNumber = Integer.valueOf(port);
	    }
	    else{
	    	System.out.println("Retrieving port = " + systemPort + " from system properties...maven command line.");
	    	portNumber = Integer.valueOf(systemPort);
	    }
	    return portNumber;
	}
	
	public String getHost(Properties props) {
		String selectedHost;
	    String systemHost = System.getProperty("server.host");
	    String host = props.getProperty("server.host");
	    if(systemHost==null){
	    	System.out.println("Retrieving host = " + host + " from property file.");
	        selectedHost = host;
	    } else {
	    	System.out.println("Retrieving host = " + systemHost + " from system properties...maven command line");
	    	selectedHost = systemHost;
	    }
	    return selectedHost;
	}
	
	public String getBasePath(Properties props) {
		String selectedBasePath;
	    String basePath = props.getProperty("application.base");
	    String systemBasePath = System.getProperty("application.base");
	    if(systemBasePath==null){
	    	System.out.println("Retrieving basePath = " + basePath + " from property file");
	    	selectedBasePath = basePath;
	    } else {
	    	System.out.println("Retrieving basePath = " + systemBasePath + " from system properties...maven command line.");
	    	selectedBasePath = systemBasePath;
	    }
		return selectedBasePath;
	}

}
