package com.kamikarow.hairCareProject.infra;

import com.kamikarow.hairCareProject.domain.stage.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface StageJpaRepository extends JpaRepository<Stage,Long>  {

    @Override
    <S extends Stage> S save(S entity);

    @Override
    Optional<Stage> findById(Long aLong);

    @Override
    void delete(Stage entity);

    @Query(value="select * from kkarowdb.stage a WHERE a.routine_id = :id",  nativeQuery = true)
    List<Stage> findAllByRoutineId(@RequestParam("id") Long id);


}
