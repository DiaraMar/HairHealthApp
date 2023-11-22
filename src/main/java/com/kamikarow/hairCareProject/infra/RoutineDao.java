package com.kamikarow.hairCareProject.infra;

import com.kamikarow.hairCareProject.domain.routine.Routine;
import com.kamikarow.hairCareProject.domain.stage.Stage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoutineDao {

    private final RoutineJpaRepository routineJpaRepository;

    public Routine save (Routine routine){
        return this.routineJpaRepository.save(routine);
    }

    public List<Routine> retrieveAllBy(Long userId){
        return this.routineJpaRepository.findAllByUserId(userId);
    }

    public Optional<Routine> findBy(Long routineId){
        System.out.println("debbug E + ID " + routineId);
        return this.routineJpaRepository.findById(routineId);
    }

    public void update(Routine routine){
        save(routine);
    }

    public void delete(Routine routine){
        this.routineJpaRepository.delete(routine);
    }
}
