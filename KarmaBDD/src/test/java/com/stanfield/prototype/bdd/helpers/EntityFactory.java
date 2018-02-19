package com.stanfield.prototype.bdd.helpers;

import java.util.List;

import cucumber.api.DataTable;

import io.restassured.response.Response;

import com.stanfield.prototype.model.Customer;

public class EntityFactory {
	
	private static EntityFactory instance;
	private EntityFactory() {
		
	}
	
	public static EntityFactory getInstance() {
		if (instance == null) {
			instance = new EntityFactory();
		}
		return instance;
	}
	
	public Object setKey(int Id, String entityName, Object parent) {
		if(entityName != null && entityName.equals("Customer") && Id > 0){
			parent = setId(Id, entityName, parent);
		} 
		return parent;
	}
	
	public Object setId(int Id, String entityName, Object parent) {
		if(entityName != null && entityName.equals("Customer") && Id > 0){
			Customer customer = (Customer)parent;
			customer.setId(Id);
			parent = customer;
		} 
		return parent;
	}
	
	public Object findEntityByKey(int Id, String entityName, List<Object> entities) {
		Object foundEntity = null;
		if (entityName != null && entityName.equals("Customer")) {
			for(Object o: entities) {
				Customer c  = (Customer)o;
				if (c.getId() == Id) {
					foundEntity = c;
					break;
				}
			}
		}
		return foundEntity;
		
	}
	
	public Object getEntityFromTable(DataTable table, String entityName){
		Object entity = new Object(); 
		if(entityName != null && entityName.equals("Customer")){
			List<Customer> customers = table.asList(Customer.class);
			Customer customer = customers.get(0);
			entity = customer;
		} 
		return entity;
	}
	
	public Object getEntityFromResponse(Response response, String entityName) {
		Object entity = null;
		if(entityName != null && entityName.equals("Customer")) {
			entity = response.as(Customer.class);
		}	
		return entity;
	}
	
	public Object[] getEntitiesFromResponse(Response response, String entityName) {
		Object[] entities = null;
		if(entityName != null && entityName.equals("Customer")) {
			entities = response.as(Customer[].class);
		}	
	    return entities;	
	}
	
	public Object createNewCustomer() {
		Object entity = null;
		entity = new Customer("Patrick", "Willis", 44);
		return entity;
	}
	
	public Object createACustomer(String firstName, String lastName, int age) {
		Object entity = null;
		entity = new Customer(firstName, lastName, age);
		return entity;
	}

}
