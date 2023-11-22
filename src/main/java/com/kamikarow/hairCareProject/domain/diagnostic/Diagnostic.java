package com.kamikarow.hairCareProject.domain.diagnostic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kamikarow.hairCareProject.domain.file.File;
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
    @JoinColumn(name = "owner", referencedColumnName = "id")
    @JsonIgnore
    private User owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @JsonIgnore
    private User createdBy;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "diagnostic", cascade = CascadeType.ALL)
    private File file;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    private String report;
    private String title;

    private String conclusion;

    @Override
    public String toString() {
        return "Diagnostic{" +
                "id=" + id +
                ", createdOn=" + createdOn +
                ", report='" + report + '\'' +
                ", title='" + title + '\'' +
                ", conclusion='" + conclusion + '\'' +
                '}';
    }
}