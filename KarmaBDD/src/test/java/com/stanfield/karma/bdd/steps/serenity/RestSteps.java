package com.stanfield.karma.bdd.steps.serenity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stanfield.karma.bdd.helpers.EntityFactory;
import com.stanfield.karma.bdd.helpers.JsonHelper;
import com.stanfield.karma.bdd.helpers.RestConfig;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.restassured.http.ContentType;
import io.restassured.mapper.factory.GsonObjectMapperFactory;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import io.restassured.RestAssured;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import static com.google.gson.FieldNamingPolicy.UPPER_CAMEL_CASE;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class RestSteps {
	private String link;
	public static String URI = null;
	private Response response;
	private RequestSpecification request;
	private Object entity;
	private String entityName;
	private Map<String,String> params = new HashMap<String, String>();
	private JsonHelper helper = new JsonHelper();
	
   public void clear() {
		link = null;
		response = null;
		request = null;
		entity = null;
		Serenity.getCurrentSession().clear();
   }
   
   public RequestSpecification getRequest() {
	   if (request == null) {
		   SerenityRest.setDefaultParser(Parser.JSON);
		   RestConfig restConfig = new RestConfig();
		   URI = restConfig.getURI();
		   /** Important** only setDefaultConfig with this custom config when interacting with
		    *  .Net Http requests that use capital camel case for attribute names */
		   //RestAssured.config = getCustomConfig();
		   //SerenityRest.setDefaultConfig(getCustomConfig());
		   request = SerenityRest.given()
				   .contentType("application/json")
				   .baseUri(URI)
				   .basePath(link);
	   
	   }		   
	   return request;
   }
   
   /**
    * This custom RestAssured.config is necessary when invoking Http Requests that return objects that use
    * .Net naming standards for attributes, that is, uppercase field names.  Java uses lowercase so
    * the deserialization mechanism needs to accomodate .Net's reluctance to adhere to open source standards. 
    * @return RestAssuredConfig
    */
   public RestAssuredConfig getCustomConfig() {
	   RestAssuredConfig config = RestAssuredConfig.config().objectMapperConfig(objectMapperConfig().gsonObjectMapperFactory(
               new GsonObjectMapperFactory() {
                   public Gson create(Class cls, String charset) {
                       return new GsonBuilder().setFieldNamingPolicy(UPPER_CAMEL_CASE).create();
                   }
               }
       ));
	   RestAssured.config = config;
	   return RestAssured.config;
   }

   public static void setUpRestAssured() {
       // maven Command line example:
       // mvn test -Dserver.port=9000 -Dserver.host=http://example.com
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

       String port = props.getProperty("server.port");
       if (port == null) {
           RestAssured.port = Integer.valueOf(4200);
       }
       else{
           RestAssured.port = Integer.valueOf(port);
       }
       String basePath = System.getProperty("server.base");
       if(basePath==null){
           basePath = "/api/";
       }
       RestAssured.basePath = basePath;

       String baseHost = System.getProperty("application.host");
       if(baseHost==null){
           baseHost = "http://localhost";
       }
       RestAssured.baseURI = baseHost + ":" + port + basePath;
       URI = RestAssured.baseURI;
       System.out.println("URI = " + URI);

   } 
   
   @Step
   public void setTheLink(String link){
	   this.link = link;
   }
   
   @Step
   public void setEntity(Object entity){
	   this.entity = entity;
       getRequest().body(entity);
   }
   
   @Step
   public Object getEntity() {
	   return this.entity;
   }
   
	public String getEntityName() {
		return this.entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
   
   @Step
   public void storeFakeVariableInSession(String variable){
       Serenity.setSessionVariable(variable).to(String.valueOf(1));
       System.out.println("Fake " + variable + " = " + getSessionVariable(variable));
   }
   
   @Step
   public void setParameters(Map<String,String> params) {
	   System.out.println("------------------------------");
	   System.out.println("Setting Parameters:");
	   for( String key : params.keySet())
       {
           if (key.equals("id")) {
        	   String value = Serenity.sessionVariableCalled(key).toString();
        	   System.out.println("Setting " + key + " from session, value = " + value);
        	   if (value != null & !value.equals(""))
        		   this.params.put(key, value);
           } else {
               this.params.put(key, params.get(key));       	   
           }
           System.out.println("Key -> " + key + ", Value -> " + this.params.get(key));
       } 
   }
   
   @Step
   public void performAction(String action) {
		if (action.equals("post")) {
			int variable = getSessionVariable("id");
			setEntity(EntityFactory.getInstance().setId(variable, entityName, getEntity()));
			response = getRequest().when()
        		.post();
			if (link.equals("customer")){
				storeVariableFromResponseInSession(response, "id", "id");
			}		
		} else if (action.equals("put")) {
			int variable = getSessionVariable("id");
			/** set appropriate keys for put operation */
			setEntity(EntityFactory.getInstance().setKey(variable, getEntityName(), getEntity()));
			getRequest().given()
				.body(entity);
			response = getRequest().when()
					.contentType(ContentType.JSON)
					.put(URI + link + "/" + variable);
		} else if (action.equals("get")) {
			RequestSpecification rs = getRequest().params(params);
			rs.body("");
			if(!params.isEmpty()) {
				response = rs.when().get(URI + link + "/" + params.get("id"));
			} else {
				response = rs.when().get(URI + link);
			}
		} else if (action.equals("delete")) {
			getRequest().pathParams(params);
			response = getRequest().given()
				.when().delete("{id}");
		}
   }
   
   @Step
   public void verifyMessage(String message){
	   	System.out.println("Response is " + response.getStatusLine());
		Assert.assertTrue(response.getStatusLine().contains(message));
   }

   @Step
   public void checkTheEntityInTheResponse() {
	   Object entity = getObjectFromResponse(response);
	   Assert.assertNotNull(entity);
	   Assert.assertTrue(this.entity.equals(entity));
   }

   @Step
   public void checkTheListForTheEntityInTheResponse() {
	   List<Object> listOfEntities = getObjectListFromResponse(response);
	   Object entity = null;
	   if (listOfEntities != null && listOfEntities.size() == 1) {
		   System.out.println("Only one entity retrieved...");
		   entity = listOfEntities.get(0);
	   } else {
		   System.out.println("More than one entity retrieved...");
		   entity = EntityFactory.getInstance().findEntityByKey(getSessionVariable("id"), getEntityName(), listOfEntities);
	   }
	   Assert.assertNotNull(entity);
	   Assert.assertTrue(this.entity.equals(entity));
   }   
   
   @Step
   public void fetchEntityForJsonComparison(){
	   	Map<String,String> parameters = new HashMap<String, String>();
	   	parameters.put("id", "id");
	   	setParameters(parameters);
	   	performAction("get");
	   	checkTheJsonMapInTheResponse();
   }
   
   @Step
   public void checkTheJsonListInTheResponse() {
	   Map <String, String> extractedJsonObject= helper.getJsonFromList(response);
	   Object entity = helper.convertJsonToEntity(extractedJsonObject, this.entity);
	   Assert.assertNotNull(entity);
	   Assert.assertTrue(this.entity.equals(entity));
   }
   
   @Step
   public void checkTheJsonMapInTheResponse() {
	   Map<String, String> extractedJsonObject = helper.getJsonFromMap(response);
	   Object entity = helper.convertJsonToEntity(extractedJsonObject, this.entity);
	   Assert.assertNotNull(entity);
	   Assert.assertTrue(this.entity.equals(entity));
   }
   
   /**
    * 
    * @param response The response received after invoking the http request 
    * @param storeName The unique name you would like to use for storing and retrieving the session variable
    * @param jsonVariableName The name of the variable to retrieve in the jsonPath
    * @return boolean Indicates whether or not the jsonVariable was retrieved from the JSonPath+
    */
   public boolean storeVariableFromResponseInSession(Response response, String storeName, String jsonVariableName) {
	   boolean successful = false;
	   JsonPath jsonPath = helper.getJsonPath(response);
	   int variable = helper.getVariableFromJsonPath(jsonPath, jsonVariableName);
	   if (variable > 0) {
		   successful = true;
	   }
	   Serenity.setSessionVariable(storeName).to(String.valueOf(variable)); 
	   System.out.println("Stored " + storeName + " = " + variable  + " as a session variable.");
	   return successful;
   }
   
   public int getSessionVariable(String variableName) {
	   int variable = 0;
	   String s = Serenity.sessionVariableCalled(variableName);
		try {
			variable = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}
	   return variable;
   }
   
   public Object getObjectFromResponse(Response response) {
	   Object object = null;
	   if (response != null) {
		   Response extractedResponse = response.then().extract().response();
		   object = EntityFactory.getInstance().getEntityFromResponse(extractedResponse, this.entityName);
	   }   
	   return object;
   }
   
   public List<Object> getObjectListFromResponse(Response response) {
	   List<Object> objects = null;
	   if (response != null) {
		   Object[] entities = null;
		   Response extractedResponse = response.then().extract().response();
		   entities = EntityFactory.getInstance().getEntitiesFromResponse(extractedResponse, this.entityName);
		   objects = new ArrayList<Object>(Arrays.asList(entities));
	   }   
	   return objects;	   
   }
  
	@After("@After_DeleteCustomer")
	public void afterScenario(Scenario scenario) {
		System.out.println("------------------------------");
		System.out.println(scenario.getName());
		System.out.println("------------------------------");

		String Id = Serenity.sessionVariableCalled("id").toString();
		System.out.println("Deleting customer with Id = " + Id);
		setTheLink("customer");
		getRequest().pathParams("id", Id);
		getRequest().given().when().delete("{id}");
		clear();
	}

	@Before("@Before_InsertCustomer")
	public void beforeScenario(Scenario scenario) {
		System.out.println("------------------------------");
		System.out.println(scenario.getName());
		System.out.println("------------------------------");

		setTheLink("customer");
		Object newEntity = EntityFactory.getInstance().createNewCustomer();
		setEntity(newEntity);
		response = getRequest().when().post();
		boolean storedInSession = storeVariableFromResponseInSession(response, "id", "id");
		System.out.println("Inserted Id stored in session = " + storedInSession);
		System.out.println("Inserted a customer before " + scenario.getName() + ":");
	}
}
