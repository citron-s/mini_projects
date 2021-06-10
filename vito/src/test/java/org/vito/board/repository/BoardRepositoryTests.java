package org.vito.board.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.vito.board.entity.Board;
import org.vito.board.entity.Member;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard() {

        IntStream.rangeClosed(1,10).forEach(i -> {

            Member member = Member.builder().userid("userId"+i ).build();

            Board board = Board.builder()
                                .title("Title..."+i)
                                .content("Content...." + i)
                                .writer(member)
                                .build();

            boardRepository.save(board);

        });

    }


    @Transactional
    @Test
    public void testRead1() {

        Optional<Board> result = boardRepository.findById(10L); //this num must exist in db

        Board board = result.get();

       // System.out.println(board);
       // System.out.println(board.getWriter());

    }

    @Test
    public void testReadWithWriter() {

        Object result = boardRepository.getBoardWithWriter(10L);

        Object[] arr = (Object[])result;

        //System.out.println("-------------------------------");
        ///System.out.println(Arrays.toString(arr));

    }

    @Test
    public void testGetBoardWithReply() {

        List<Object[]> result = boardRepository.getBoardWithReply(10L);

        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }


    @Test
    public void testWithReplyCount(){

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {

            Object[] arr = (Object[])row;

            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    public void testRead3() {

        Object result = boardRepository.getBoardByBno(10L);

        Object[] arr = (Object[])result;

        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testSearchBoard() {

        boardRepository.searchBoard();

    }

    @Test
    public void testSearchPage() {

        Pageable pageable =
                PageRequest.of(0,10,
                        Sort.by("bno").descending()
                                .and(Sort.by("title").ascending()));

        Page<Object[]> result = boardRepository.searchPage("t", "1", pageable);

    }




}
