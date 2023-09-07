package com.kamikarow.hairCareProject.infra;

import com.kamikarow.hairCareProject.domain.diagnostic.Diagnostic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DiagnosticJpaRepository extends JpaRepository<Diagnostic,Long> {


    @Query(value = "select * from kkarowdb.diagnostic a WHERE a.owner = :id", nativeQuery = true)
    List<Diagnostic> findAllDiagnosticsBy(@Param("id") Long userID);


}
