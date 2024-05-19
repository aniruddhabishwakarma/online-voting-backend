package com.project.System.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Events {
    private int id;
    private String eventName;
    private String eventDescription;
    private String eventPhoto;
    private Long startDate;
    private Long endDate;
}
