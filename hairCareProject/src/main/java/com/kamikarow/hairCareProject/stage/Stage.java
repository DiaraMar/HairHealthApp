package com.kamikarow.hairCareProject.stage;
import com.kamikarow.hairCareProject.domain.routine.Routine;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stage")
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime createdOn;

    @ManyToMany(mappedBy = "stages")
    private List<Routine> routines;
}


