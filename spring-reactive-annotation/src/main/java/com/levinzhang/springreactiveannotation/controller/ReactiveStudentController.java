package com.levinzhang.springreactiveannotation.controller;

import com.levinzhang.springreactiveannotation.model.Student;
import com.levinzhang.springreactiveannotation.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ReactiveStudentController {

    @Autowired
    private StudentRepository repository;

    @GetMapping("/student/{id}")
    public Mono<Student> getStudentById(@PathVariable("id") int id) {
        return repository.getStudent(id);
    }

    @GetMapping(value = "/student")
    public Flux<Student> getAllStudent() {

        return repository.getAllStudents();
    }

    @PostMapping(value = "/student")
    public Mono<Void> createStudent(@RequestBody  Student student) {
        Mono<Student> studentMono = Mono.just(student);
        return repository.saveStudent(studentMono);
    }
}
