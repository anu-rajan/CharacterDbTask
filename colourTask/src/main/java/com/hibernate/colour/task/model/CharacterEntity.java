package com.hibernate.colour.task.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name = "Character", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID"),
		@UniqueConstraint(columnNames = "NAME") })
public class CharacterEntity  implements Serializable {
	
	private static final long serialVersionUID = -1798070786993154676L;
	
	@Id
	@Column(name = "ID", unique = true, nullable = false,insertable=true, updatable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@ApiModelProperty(notes = "Id of the character",name="id",required=true,value="0",hidden=false)
	private Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NAME", unique = true, nullable = false, length = 100)
	@ApiModelProperty(notes = "Name of the character",name="name",required=true,value="test name")
	private String name;
	
	@Column(name = "PARENT_ID", unique = false,nullable = false, length = 100)
	@ApiModelProperty(notes = "Id of the parent character",name="name",required=false,value="0")
	private Integer parent_id=0;
	
	@Column(name = "COLOUR", unique = false, nullable = false, length = 100)
	@ApiModelProperty(notes = "Colour of the  character",name="colour",required=false,value="test colour")
	private String colour;

	
	public CharacterEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public CharacterEntity(Integer id,String name,Integer parentId, String colour) {
		super();
		this.id=id;
		this.name = name;
		this.parent_id = parentId;
		this.colour=colour;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	
}