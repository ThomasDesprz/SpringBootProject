package com.example.demo.Repositories;

import com.example.demo.Entities.Player;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
	@EntityGraph(attributePaths = {"team"})
    List<Player> findAll();
}
