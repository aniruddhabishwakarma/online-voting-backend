package com.project.System.repository;

import com.project.System.entity.EventsEntity;
import com.project.System.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsRepository extends JpaRepository<EventsEntity,Integer> {
}
