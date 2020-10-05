package com.hibernate.colour.task.model;

import io.swagger.annotations.ApiModelProperty;

public class Person {
	@ApiModelProperty(notes = "FirstName of the person",name="name",required=true,value="test first name")
	private String name;
	@ApiModelProperty(notes = "Surname of the person",name="surName",required=true,value="test surname")
	private String surName;

	public String getName() {
		return name;
	}

	public String getSurName() {
		return surName;
	}
	
	public Person() {
		System.out.println("person def");
	}
	
	public Person(String name, String surName) {
		super();
		System.out.println("person param");
		this.name = name;
		this.surName = surName;
	}
	@Override
	public String toString() {
		return "Person [Name=" + name + ", Surname=" + surName + "]";
	}

}
