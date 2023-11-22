package com.kamikarow.hairCareProject.infra;

import com.kamikarow.hairCareProject.domain.file.File;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FileDao {

    private final FileJpaRepository fileJpaRepository;

    public File save(File file){
        return fileJpaRepository.save(file);
    }
}
