package com.levinzhang.springreactiveannotation.repository;

import com.levinzhang.springreactiveannotation.model.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 获取学生的接口
 */
public interface StudentRepository {

    /**
     * 根据id获取学生
     * @param id
     * @return
     */
    Mono<Student> getStudent(int id);

    /**
     * 得到所有的学生
     * @return
     */
    Flux<Student> getAllStudents();

    /**
     * 保存
     * @param student
     * @return
     */
    Mono<Void> saveStudent(Mono<Student> student);

}
