package com.kamikarow.hairCareProject.domain.routine;


import com.kamikarow.hairCareProject.domain.user.User;
import com.kamikarow.hairCareProject.stage.Stage;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "routine")
public class Routine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User createdBy;

    private LocalDateTime createdOn;

    @ManyToMany
    @JoinTable(
            name = "routine_stage",
            joinColumns = @JoinColumn(name = "routine_id"),
            inverseJoinColumns = @JoinColumn(name = "stage_id")
    )
    private List<Stage> stages;
}

