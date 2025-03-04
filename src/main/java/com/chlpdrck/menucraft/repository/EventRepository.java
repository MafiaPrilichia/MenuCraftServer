package com.chlpdrck.menucraft.repository;

import com.chlpdrck.menucraft.entity.Event;
import com.chlpdrck.menucraft.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByOwner(User owner);
}