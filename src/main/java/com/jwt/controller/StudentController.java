package com.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<String> getStudent() {
        return ResponseEntity.ok("Get Student");
    }

    @GetMapping("/all")
    @PreAuthorize("(hasRole('ROLE_STUDENT') and hasAuthority('READ')) or hasAuthority('WRITE')")
    public ResponseEntity<String> getAllStudents() {
        return ResponseEntity.ok("Get All Students");
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_STUDENT') and hasAuthority('WRITE')")
    public ResponseEntity<String> createStudent() {
        return ResponseEntity.accepted().body("Student Created");
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_STUDENT') and hasAuthority('UPDATE')")
    public ResponseEntity<String> updateStudent() {
        return ResponseEntity.ok("Student Updated");
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_STUDENT') and hasAuthority('DELETE')")
    public ResponseEntity<String> deleteStudent() {
        // return new ResponseEntity<>("Deleted Student Successfully !!!",
        // HttpStatus.NO_CONTENT);
        return ResponseEntity.noContent().build();
    }

}
