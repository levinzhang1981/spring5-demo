package com.levinzhang.springreactive.handler;

import com.levinzhang.springreactive.model.Student;
import com.levinzhang.springreactive.repository.StudentRepository;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class StudentHandler {

    private final StudentRepository repository;

    public StudentHandler(StudentRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getStudentById(ServerRequest request) {
        int personId = Integer.valueOf(request.pathVariable("id"));
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<Student> studentMono = this.repository.getStudent(personId);
        return studentMono
                .flatMap(person -> ServerResponse.ok().contentType(APPLICATION_JSON)
                        .body(fromObject(person)))
                .switchIfEmpty(notFound);
    }


    public Mono<ServerResponse> createStudent(ServerRequest request) {
        Mono<Student> student = request.bodyToMono(Student.class);
        return ServerResponse.ok().build(this.repository.saveStudent(student));
    }

    public Mono<ServerResponse> getAllStudent(ServerRequest request) {
        Flux<Student> student = this.repository.getAllStudents();
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(student, Student.class);
    }
}
