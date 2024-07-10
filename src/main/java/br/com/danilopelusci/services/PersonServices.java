package br.com.danilopelusci.services;


import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.danilopelusci.exceptions.ResourceNotFoundException;
import br.com.danilopelusci.model.Person;
import br.com.danilopelusci.repository.PersonRepository;

@Service
public class PersonServices {
	@Autowired
	PersonRepository repository;
	
 
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	public List<Person> findAll() {

		logger.info("Finding all people!");
		
		
		return repository.findAll();
	}

	public Person findById(Long id) {
		
		logger.info("Finding one person!");
		
		return repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("noRecords found for this ID!"));
	}
	
	public Person create(Person person) {

		logger.info("creating one person!");
		
		return repository.save(person);
	}
	
	public Person update(Person person) {
		
		logger.info("Updating one person!");
		Person entity = repository.findById(person.getId()).orElseThrow(()-> new ResourceNotFoundException("noRecords found for this ID!"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		return repository.save(person);
	}
	
	public void delete(Long id) {
		
		repository.deleteById(id);;
	}
	
	
}
