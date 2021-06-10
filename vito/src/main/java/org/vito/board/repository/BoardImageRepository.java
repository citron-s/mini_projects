package org.vito.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vito.board.entity.BoardImage;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long>{


}
