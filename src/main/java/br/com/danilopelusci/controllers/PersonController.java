package br.com.danilopelusci.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.danilopelusci.model.Person;
import br.com.danilopelusci.services.PersonServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "endPoints for managing people")
public class PersonController {

	@Autowired
	private PersonServices service;

	@GetMapping
	@Operation(summary = "finds all people", description = "pega todas as pessoas", tags = { "People" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = @Content(mediaType = "application/json", 
					array = @ArraySchema(schema = @Schema(implementation = Person.class)))),
			
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal error", responseCode = "500", content = @Content)

	})

	public List<Person> findAll() {
		return service.findAll();
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "finds a people", description = "pega  a pessoa", tags = { "People" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = Person.class))),
			
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "no content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal error", responseCode = "500", content = @Content)

	})
	public Person findById(@PathVariable(value = "id") String id) {
		return service.findById(id);
	}

	@PostMapping
	public Person create(@RequestBody Person person) {
		return service.create(person);
	}

	@PutMapping
	public Person update(@RequestBody Person person) {
		return service.update(person);
	}

	@DeleteMapping
	public void delete(@PathVariable(value = "id") String id) {
		service.delete(id);
	}
}