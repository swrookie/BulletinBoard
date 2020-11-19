package com.swrookie.bulletinboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swrookie.bulletinboard.entity.File;

public interface FileRepository extends JpaRepository<File, Long>
{
	List<File> findByBoardNo(Long boardNo);
}
