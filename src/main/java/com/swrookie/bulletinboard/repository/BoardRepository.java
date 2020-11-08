package com.swrookie.bulletinboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swrookie.bulletinboard.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>
{
	List<Board> findByTitleContaining(String keyword);
}
