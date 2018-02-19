package com.stanfield.karma.model;

import java.io.Serializable;

public class Customer implements Serializable  {

	private static final long serialVersionUID = 5458196448833220560L;

	private long id;
	
	private String firstName;

	private String lastName;

	private int age;
	
	public Customer(){
	}
	
	public Customer(String firstName, String lastName, int age){
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}
	
	public Customer(long id, String firstName, String lastName, int age){
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public long getId(){
		return this.id;
	}
	
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	public String getFirstName(){
		return this.firstName;
	}
	
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	public String getLastName(){
		return this.lastName;
	}
	
	public void setAge(int age){
		this.age = age;
	}
	
	public int getAge(){
		return this.age;
	}

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Customer other = (Customer) obj;
        if (this.firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!this.firstName.equals(other.firstName))
            return false;
        if (this.age != other.age)
            return false;
        if (this.lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!this.lastName.equals(other.lastName))
            return false;
        return true;
    }
	
	@Override
	public String toString() {
		return String.format("Customer[id=%d, firstName='%s', lastName='%s, age=%d]", id, firstName, lastName, age);
	}
	
	public boolean customerEmpty() {
		boolean empty = false;
		if(getFirstName() == null || getLastName() == null) {
			empty = true;
			
		} else {
			if (getFirstName().trim().equals("") || getLastName().trim().equals("")){
				empty = true;
			}
		}
		return empty;
	}
}
