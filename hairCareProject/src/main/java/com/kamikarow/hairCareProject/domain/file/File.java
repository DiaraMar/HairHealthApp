package com.kamikarow.hairCareProject.domain.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kamikarow.hairCareProject.domain.diagnostic.Diagnostic;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnostic", referencedColumnName = "id")
    @JsonIgnore
    private Diagnostic diagnostic;

   /* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", referencedColumnName = "id")
    private User owner;*/

    @Column(name = "title")
    private String title;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "document")
    private byte[] document;

    @Column(name = "created_on")
    private LocalDateTime createdOn;
}
