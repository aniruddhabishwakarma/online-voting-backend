package com.project.System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "candidates", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "event_id"}))
public class CandidateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private EventsEntity event;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "description")
    private String description;

    @Column(name = "photo")
    private String photo;

    @Column(name = "status")
    private Boolean status;
    public UserEntity getUser() {

        return this.user;
    }

    public void setUser(UserEntity user) {

        this.user = user;
    }

}
