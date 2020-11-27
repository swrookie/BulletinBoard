package com.swrookie.bulletinboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swrookie.bulletinboard.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>
{
	Page<Board> findByTitleContaining(String keyword, Pageable pageable);
	Page<Board> findByContentContaining(String keyword, Pageable pageable);
}
