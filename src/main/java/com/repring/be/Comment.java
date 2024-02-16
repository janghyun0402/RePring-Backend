package com.repring.be;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "comment")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false, length = 500)
    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "created_date")
    @CreatedDate
    private LocalDate createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDate modifiedDate;

    @ManyToOne
    @JoinColumn(name = "music_id")
    private Music music;

    public static Comment from(CommentDTO.CreateRequest request, Music music) {
        return Comment.builder()
                .music(music)
                .content(request.getContent())
                .build();
    }

}
