package com.josky.jpawithconfigurationclass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBootTest
@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JpaWithConfigurationClassApplicationTests {
	
	@Autowired
	private PersonRepository personRepository;

	@BeforeAll
	public void setUp() {
		// Save 4 Person objects into the database
		personRepository.save(new Person("Sheldon", "Cooper", "123456"));
		personRepository.save(new Person("Missy", "Cooper", "234567"));
		personRepository.save(new Person("Leonard", "Hofstadter", "345678"));
		personRepository.save(new Person("Leonard", "Nimoy", "456789"));
	}

	@AfterAll
	public void cleanUp() {
		// Delete all test data
		personRepository.deleteAll();
	}

	@Test
	public void testCRUD() {
		// Create
		Person originalPerson = new Person("Howard", "Wolowitz", "654321");
		personRepository.save(originalPerson);
		Person createdPerson = personRepository.findBySsn("654321");

		assertEquals(originalPerson.getFirstName(), createdPerson.getFirstName());
		assertEquals(originalPerson.getLastName(), createdPerson.getLastName());
		assertEquals(originalPerson.getSsn(), createdPerson.getSsn());

		// Update
		createdPerson.setFirstName("Fruit Loops");
		personRepository.save(createdPerson);
		Person updatedPerson = personRepository.findBySsn("654321");

		assertEquals(createdPerson.getFirstName(), updatedPerson.getFirstName());

		// Delete
		personRepository.delete(updatedPerson);

		assertNull(personRepository.findBySsn("654321"));
	}

	@Test
	public void testFindAll() {
		// Get all the people
		List<Person> people = personRepository.findAll();

		assertEquals(4, people.size());
	}

	@Test
	public void testFindByFirstName() {
		// Get all the people with a specific first name
		List<Person> people = personRepository.findByFirstName("Leonard");

		assertEquals(2, people.size());
	}

	@Test
	public void testFindByLastName() {
		// Get all the people with a specific last name
		List<Person> people = personRepository.findByLastName("Cooper");

		assertEquals(2, people.size());
	}

}
