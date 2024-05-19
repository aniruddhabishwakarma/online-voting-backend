package com.project.System.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateInfo {
    private int id;
    private String fullName;
    private String description;
    private String photo;
    private int eventId;
}
