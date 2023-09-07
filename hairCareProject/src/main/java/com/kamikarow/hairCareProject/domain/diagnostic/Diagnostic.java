package com.kamikarow.hairCareProject.domain.diagnostic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kamikarow.hairCareProject.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "diagnostic")
public class Diagnostic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @Column(name = "created_by")
    @JsonIgnore
    private User createdBy;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "report")
    private String report;

    @Column(name = "conclusion")
    private String conclusion;

}