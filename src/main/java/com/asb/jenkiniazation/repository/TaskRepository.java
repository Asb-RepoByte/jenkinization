package com.asb.jenkiniazation.repository;

import com.asb.jenkiniazation.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
