package com.levinzhang.spring.mvc.controller;

import com.levinzhang.spring.mvc.model.Student;
import com.levinzhang.spring.mvc.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/student")
public class TestController {

    @Autowired
    private StudentRepository repository;

    @RequestMapping
    public String indexPage(){
        return "index";
    }

    @RequestMapping("/search")
    @ResponseBody
    public Student getStudentByIdParam(@RequestParam("id") int id){
        return repository.getStudent(id);
    }


    @RequestMapping("/{id}")
    @ResponseBody
    public Student getStudentByIdPath(@PathVariable("id") int id){
        return repository.getStudent(id);
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<Student> getStudentByIdParam(){
        return repository.getAllStudent();
    }






    @RequestMapping("/create")
    public ResponseEntity<Student> saveStudent(@RequestBody  Student student,
                                               UriComponentsBuilder ucb){
        repository.saveStudent(student);
        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/student/")
                            .path(String.valueOf(student.getId())).build().toUri();

        headers.setLocation(locationUri);

        return new ResponseEntity<>(student,headers, HttpStatus.CREATED);
    }

}
