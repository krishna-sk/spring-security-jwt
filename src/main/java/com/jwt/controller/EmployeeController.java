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
@RequestMapping("/employee")
public class EmployeeController {

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<String> getEmployee() {
        return ResponseEntity.ok("Get Employee");
    }

    @GetMapping("/all")
    @PreAuthorize("(hasRole('ROLE_EMPLOYEE') and hasAuthority('READ')) or hasAuthority('WRITE')")
    public ResponseEntity<String> getAllEmployees() {
        return ResponseEntity.ok("Get All Employees");
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') and hasAuthority('WRITE')")
    public ResponseEntity<String> createEmployee() {
        return ResponseEntity.accepted().body("Employee Created");
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') and hasAuthority('UPDATE')")
    public ResponseEntity<String> updateEmployee() {
        return ResponseEntity.ok("Employee Updated");
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') and hasAuthority('DELETE')")
    public ResponseEntity<String> deleteEmployee() {
        return ResponseEntity.noContent().build();
    }

}
