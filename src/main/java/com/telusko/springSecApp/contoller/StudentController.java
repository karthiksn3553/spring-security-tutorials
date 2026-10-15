package com.telusko.springSecApp.contoller;

import com.telusko.springSecApp.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    List<Student> students = new ArrayList<>(List.of(
            new Student(127,"karthik",97),
            new Student(128,"tushar",98)
    ));

    @GetMapping("/students")
    public List<Student> getAll(){
        return students;
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @PostMapping("/addstudents")
    public Student addStudent(@RequestBody Student student){
        students.add(student);
        return student;
    }
}
