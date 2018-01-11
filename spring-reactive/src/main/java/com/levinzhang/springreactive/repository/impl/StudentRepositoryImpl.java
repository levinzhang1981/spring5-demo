package com.levinzhang.springreactive.repository.impl;

import com.levinzhang.springreactive.model.Student;
import com.levinzhang.springreactive.repository.StudentRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class StudentRepositoryImpl implements StudentRepository{

    private final Map<Integer, Student> students = new HashMap<>();

    public StudentRepositoryImpl() {
        this.students.put(1, new Student("Li Lei", 15,"Male"));
        this.students.put(2, new Student("Han Meimei", 14,"Female"));
    }

    @Override
    public Mono<Student> getStudent(int id) {
        return Mono.justOrEmpty(this.students.get(id));
    }

    @Override
    public Flux<Student> getAllStudents() {
        return Flux.fromIterable(this.students.values());
    }

    @Override
    public Mono<Void> saveStudent(Mono<Student> personMono) {
        return personMono.doOnNext(person -> {
            int id = students.size() + 1;
            students.put(id, person);
        }).thenEmpty(Mono.empty());
    }

}
