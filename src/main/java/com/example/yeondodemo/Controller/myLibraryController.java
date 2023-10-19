package com.example.yeondodemo.Controller;

import com.example.yeondodemo.filter.JwtValidation;
import com.example.yeondodemo.service.LibraryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/library")
public class myLibraryController {
    private final LibraryService libraryService;
    @GetMapping@JwtValidation
    public ResponseEntity getLibrary(@RequestHeader("Gauth") String jwt){
        return new ResponseEntity(libraryService.getLibrary(jwt), HttpStatus.OK);
    }
}
