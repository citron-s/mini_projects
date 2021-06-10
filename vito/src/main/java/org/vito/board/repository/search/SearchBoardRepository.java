package org.vito.board.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vito.board.entity.Board;

public interface SearchBoardRepository {

    Board searchBoard();

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);

}
