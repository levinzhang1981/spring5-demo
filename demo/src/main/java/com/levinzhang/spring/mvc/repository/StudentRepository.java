package com.levinzhang.spring.mvc.repository;

import com.levinzhang.spring.mvc.model.Student;

import java.util.List;

/**
 * 获取学生的接口
 */
public interface StudentRepository {

    /**
     * 根据id获取学生
     * @param id
     * @return
     */
    Student getStudent(int id);


    /**
     * 保存
     * @param student
     * @return
     */
    Student saveStudent(Student student);

    /**
     * 得到所有的学生列表
     * @return
     */
   List<Student> getAllStudent();
}
