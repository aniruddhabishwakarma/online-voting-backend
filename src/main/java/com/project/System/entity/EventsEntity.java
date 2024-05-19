package com.project.System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "events")
public class EventsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "event_name")
    private String eventName;
    @Column(name = "event_desc")
    private String eventDescription;
    @Column(name= "photo")
    private String eventPhoto;
    @Column(name= "start_date")
    private Timestamp startDate;
    @Column(name = "end_date")
    private Timestamp endDate;
}
