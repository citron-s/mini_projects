package org.vito.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vito.board.entity.Board;
import org.vito.board.repository.search.SearchBoardRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {


    //board list------------------------------------------------------------------------------------------------

    @Query("select b, w from Board b left join b.writer w where b.bno =:bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    @Query("SELECT b, r FROM Board b LEFT JOIN Reply r ON r.rno = b WHERE b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    @Query(value ="SELECT b, w, count(r) " +
            " FROM Board b " +
            " LEFT JOIN b.writer w " +
            " LEFT JOIN Reply r ON r.rno = b " +
            " GROUP BY b",
            countQuery ="SELECT count(b) FROM Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);


    @Query("SELECT b, w, count(r) " +
            " FROM Board b LEFT JOIN b.writer w " +
            " LEFT OUTER JOIN Reply r ON r.rno = b" +
            " WHERE b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);




    //upload data list ------------------------------------------------------------------------------------------------

    @Query("select b, bi from Board b " +
            "left outer join BoardImage bi on bi.board = b" +
            " where b.bno = :bno group by bi")
    List<Object[]> getBoardWithAll(Long bno);


    @Query("select b, bi from Board b " +
            "left outer join BoardImage bi on bi.board = b "
          )
    Page<Object[]> getListPage(Pageable pageable);
}
