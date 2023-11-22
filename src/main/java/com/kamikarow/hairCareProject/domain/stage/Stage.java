package com.kamikarow.hairCareProject.domain.stage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kamikarow.hairCareProject.domain.routine.Routine;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    //todo add order

    private String title;
    private String description;
    private LocalDateTime createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id")
    @JsonIgnore
    private Routine routine;

    @Override
    public String toString() {
        return "Stage{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }
}