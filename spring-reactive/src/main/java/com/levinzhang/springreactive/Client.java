package com.levinzhang.springreactive;

import com.levinzhang.springreactive.model.Student;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Client {

    public static void main(String[] args){
        WebClient client = WebClient.create("http://127.0.0.1:8089");

        String id = "1";
        Student result = client.get()
                .uri("/student/{id}", id).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Student.class)
                .block();
        System.out.println(result.getName());



        Mono<Student> studentMonoToSave = Mono.just(new Student("Jim",14,"Male"));

        client.post()
                .uri("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentMonoToSave, Student.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();



        Flux<Student> results = client.get()
                .uri("/student").accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Student.class);

        results
                .map(student -> student.getName())
                .log()
                .buffer()
                .doOnNext(System.out::println)
                .blockFirst();


    }


}
