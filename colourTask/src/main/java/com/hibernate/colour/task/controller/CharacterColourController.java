package com.hibernate.colour.task.controller;



import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hibernate.colour.task.annotations.LogMethodParams;
import com.hibernate.colour.task.model.CharacterEntity;
import com.hibernate.db.colour.task.DataUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Swagger2DemoRestController", description="Api's of microservice one")
@RestController
public class CharacterColourController {
	
	
	@ApiOperation(value = "Get list of all characters", response = Iterable.class, tags = "getCharacters")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 500, message = "Service Down"),
			@ApiResponse(code = 404, message = "not found!!!") })

	@RequestMapping(value = "/characters", method = RequestMethod.GET,produces="application/json")
	public ResponseEntity<String> getCharacters() {
		JSONArray finalResult = DataUtil.getAllCharacters();
		System.out.println(finalResult);
		ResponseEntity<String> response= new ResponseEntity<String>(finalResult.toString(), HttpStatus.OK);
		return response;
	}
	
	
	@ApiOperation(value = "Get specific character by id ", response = CharacterEntity.class, tags = "getCharacterById")
	@RequestMapping(value = "/character/{id}",method=RequestMethod.GET)
	@LogMethodParams(paramNames="id" , methodName = "getCharacterById")
	public CharacterEntity getCharacterById(@PathVariable(value = "id") Integer id) {
		return DataUtil.getCharacterById(id);
	}

	@ApiOperation(value = "Creates a new entry for a character ", tags = "createCharacterEntry")
	@RequestMapping(value= "/characterEntry", method = RequestMethod.POST, consumes="application/json")
	public ResponseEntity<ObjectNode> createCharacter(@RequestBody CharacterEntity character) {
		DataUtil.createCharacter(character);
		ObjectNode node = JsonNodeFactory.instance.objectNode(); 
		node.put("message", "Success"); 
		node.put("code", HttpStatus.OK.value()); 
	    ResponseEntity<ObjectNode> response= new ResponseEntity<ObjectNode>(node, HttpStatus.OK);
	    return response;
	}
	
	@ExceptionHandler(JsonParseException.class)
	public ResponseEntity<ObjectNode> handleException(JsonParseException ex)
	{
		ObjectNode node = JsonNodeFactory.instance.objectNode(); // initializing
		node.put("message", "Invalid input"); 
		node.put("error", ex.getMessage()); 
		node.put("code", HttpStatus.UNPROCESSABLE_ENTITY.value()); 
		
	    ResponseEntity<ObjectNode> response= new ResponseEntity<ObjectNode>(node, HttpStatus.UNPROCESSABLE_ENTITY);
	    return response;
	}

}
