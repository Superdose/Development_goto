package com.amigoscode.course.mongodb;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

// repository for mongo-operations
public interface StudentRepository extends MongoRepository<Student, String> {

  Optional<Student> findStudentByEmail(String email);

}
