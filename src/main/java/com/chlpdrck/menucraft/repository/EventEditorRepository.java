package com.chlpdrck.menucraft.repository;

import com.chlpdrck.menucraft.entity.EventEditor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventEditorRepository extends JpaRepository<EventEditor, Long> {
    List<EventEditor> findByUserId(Long userId);
}