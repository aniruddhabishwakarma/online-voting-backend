package com.project.System.repository;

import com.project.System.entity.VoteEntity;
import com.project.System.model.Results;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, Integer> {



    @Query("SELECT new com.project.System.model.Results(c.id, c.description, u.fullName, c.photo, COUNT(v)) " +
            "FROM VoteEntity v " +
            "JOIN v.candidate c " +
            "JOIN c.user u " +
            "WHERE c.event.id = :id " +
            "GROUP BY c.id, c.description, u.fullName " +
            "ORDER BY COUNT(v) DESC")
    List<Results> getResults(@Param("id") Integer eventId);
}
