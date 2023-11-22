package com.kamikarow.hairCareProject.domain.routine;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kamikarow.hairCareProject.domain.comment.Comment;
import com.kamikarow.hairCareProject.domain.user.User;
import com.kamikarow.hairCareProject.domain.stage.Stage;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private User createdBy;
    private LocalDateTime createdOn;
    private boolean isVisible;
    private boolean isFavorite;


    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Stage> stages = new ArrayList<>();

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();


    public void addStage(Stage stage) {
        stages.add(stage);
        stage.setRoutine(this);
    }

    @Override
    public String toString() {
        return "Routine{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdOn=" + createdOn +
                ", isVisible=" + isVisible +
                ", isFavorite=" + isFavorite +
                '}';
    }
}

