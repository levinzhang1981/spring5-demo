package com.levinzhang.spring.mvc.repository.impl;

import com.google.common.collect.Maps;
import com.levinzhang.spring.mvc.model.Student;
import com.levinzhang.spring.mvc.repository.StudentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private final Map<Integer, Student> students = Maps.newHashMap();

    public StudentRepositoryImpl() {
        this.students.put(1, new Student(1,"Li Lei", 15,"Male"));
        this.students.put(2, new Student(2,"Han Meimei", 14,"Female"));
    }

    @Override
    public Student getStudent(int id) {
        return this.students.get(id);
    }

    @Override
    public Student saveStudent(Student student) {
        int id = students.size() + 1;
        student.setId(id);
        students.put(id, student);
        return student;
    }

    @Override
    public List<Student> getAllStudent() {
        List<Student> collect = students.values().stream().collect(Collectors.toList());
        return collect;
    }


}
