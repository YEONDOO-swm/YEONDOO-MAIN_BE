package com.example.yeondodemo.service;

import com.example.yeondodemo.dto.LibraryDTO;
import com.example.yeondodemo.repository.etc.batis.BatisLibraryRepository;
import com.example.yeondodemo.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibraryService {
    private final BatisLibraryRepository libraryRepository;
    private final JwtTokenProvider jwtTokenProvider;
    public List<LibraryDTO> getLibrary(String jwt){
        String username = jwtTokenProvider.getUserName(jwt);
        List<LibraryDTO> libraryDTOS = libraryRepository.findByUserName(username);
        libraryDTOS.forEach(
                library -> {
                    List<String> categoriesList = library.getCategoryString() != null
                            ? Arrays.asList(library.getCategoryString().split(" "))
                            : new ArrayList<>();
                    library.setSubject(categoriesList);
                }
        );
        return libraryDTOS;
    }
}
