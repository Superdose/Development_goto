package com.amigoscode.course.mongodb;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

  private final StudentRepository studentRepository;

  @Override
  public List<Student> findAllStudents() {
    return this.studentRepository.findAll();
  }
}
