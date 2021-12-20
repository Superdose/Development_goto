package com.amigoscode.course.mongodb;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootApplication
public class MongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongodbApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(
			MongoTemplate mongoTemplate, // insert via dependency injection
			StudentRepository studentRepository
	) {
		return args -> {
			Address address = new Address(
					"England",
					"NE9",
					"London"
			);

			String email = "jamila.ahmed@gmail.com";

			Student student = new Student(
					"Jamila",
					"Ahmed",
					email,
					Gender.FEMALE,
					address,
					List.of("Computer science", "Math"),
					BigDecimal.TEN,
					LocalDateTime.now()
			);

			// for reference, one way of doing it:
			// usingMongoTemplateAndQuery(mongoTemplate, studentRepository, email, student);

			studentRepository.findStudentByEmail(email).ifPresentOrElse(s -> {
				System.out.println(s + " already exists");
			}, () -> {
				System.out.println("Inserting student " + student);

				// use the repository to insert a student
				studentRepository.insert(student);
			});
		};
	}

	private void usingMongoTemplateAndQuery(
			MongoTemplate mongoTemplate,
			StudentRepository studentRepository,
			String email,
			Student student
	) {
		// use a mongo query to perform a query
		Query query = new Query();

		// add a criteria to the query, to specify, what to fetch
		query.addCriteria(Criteria.where("email").is(email));

		// use the mongoTemplate to find a/the student(s) via the created query
		List<Student> students = mongoTemplate.find(query, Student.class);

		if(students.size() > 1) {
			throw new IllegalStateException("found many students with the specified email| "
					+ email + " |inside the database, but student.email is unique");
		}

		if(students.isEmpty()) {
			System.out.println("Inserting student " + student);

			// use the repository to insert a student
			studentRepository.insert(student);
		} else {
			System.out.println(student + " already exists");
		}
	}
}
