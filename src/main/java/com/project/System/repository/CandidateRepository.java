package com.project.System.repository;

import com.project.System.entity.CandidateEntity;
import com.project.System.model.CandidateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<CandidateEntity,Integer> {

@Query("SELECT new com.project.System.model.CandidateInfo(c.id, u.fullName, c.description, c.photo, c.event.id) FROM CandidateEntity AS c INNER JOIN c.user AS u WHERE c.status = 0")
List<CandidateInfo> getUnApprovedCandidate();

@Query("SELECT new com.project.System.model.CandidateInfo(c.id, u.fullName, c.description, c.photo, c.event.id) " +
        "FROM CandidateEntity AS c INNER JOIN c.user AS u " +
        "WHERE c.status = 1 AND c.event.id = :id"
)
List<CandidateInfo> getApprovedCandidate(@Param("id") int id);
}




