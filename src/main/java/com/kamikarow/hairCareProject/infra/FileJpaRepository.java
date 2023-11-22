package com.kamikarow.hairCareProject.infra;

import com.kamikarow.hairCareProject.domain.file.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileJpaRepository extends JpaRepository<File,Long> {

}
