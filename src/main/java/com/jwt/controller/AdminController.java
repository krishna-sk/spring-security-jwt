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
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> getAdmin() {
        return ResponseEntity.ok("Get Admin");
    }

    @GetMapping("/all")
    @PreAuthorize("(hasRole('ROLE_ADMIN') and hasAuthority('READ')) or hasAuthority('WRITE')")
    public ResponseEntity<String> getAllAdmins() {
        return ResponseEntity.ok("Get All Admins");
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('WRITE')")
    public ResponseEntity<String> createAdmin() {
        return ResponseEntity.accepted().body("Admin Created");
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('UPDATE')")
    public ResponseEntity<String> updateAdmin() {
        return ResponseEntity.ok("Admin Updated");
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('DELETE')")
    public ResponseEntity<String> deleteAdmin() {
        return ResponseEntity.noContent().build();
    }

}
