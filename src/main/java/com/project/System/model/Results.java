package com.project.System.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Results {
    private Integer candidateId;
    private String candidateDescription;
    private String fullName;
    private String photo;
    private Long count;
}
