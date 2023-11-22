package com.kamikarow.hairCareProject.infra;

import com.kamikarow.hairCareProject.domain.routine.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoutineJpaRepository extends JpaRepository<Routine,Long> {
    @Override
    <S extends Routine> S save(S entity);

    @Override
    void delete(Routine entity);

    @Query(value="select * from kkarowdb.routine a WHERE a.owner_id = :id",  nativeQuery = true)
    List<Routine> findAllByUserId(@Param("id") Long userID);

    @Query(value="select * from kkarowdb.routine a WHERE a.id = :id",  nativeQuery = true)
    Optional<Routine> findById(@Param("id") Long aLong);
}
