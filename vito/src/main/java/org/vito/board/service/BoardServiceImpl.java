package org.vito.board.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vito.board.dto.BoardDTO;
import org.vito.board.dto.PageRequestDTO;
import org.vito.board.entity.Board;
import org.vito.board.entity.Member;
import org.vito.board.repository.BoardImageRepository;
import org.vito.board.repository.BoardRepository;
import org.vito.board.repository.ReplyRepository;
import org.vito.board.dto.PageResultDTO;
import org.vito.board.entity.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final BoardImageRepository imageRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    @Override
    public Long register(BoardDTO dto) {
        log.info(dto);

        Map<String, Object> entityMap = dtoToEntity(dto);

        Board board = (Board) entityMap.get("board");
        boardRepository.save(board);

        List<BoardImage> boardImageList = (List<BoardImage>) entityMap.get("imgList");
        boardImageList.forEach(movieImage -> {
            imageRepository.save(movieImage);
        });

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        //log.info(pageRequestDTO);

        Page<Object[]> resultSearch = boardRepository.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending())
        );


        Function<Object[], BoardDTO> fn = (en -> entityToDTO(
                (Board)en[0],
                (Arrays.asList((BoardImage)en[1])),
                (Member)en[2],
                (Long)en[4])
        );

        return new PageResultDTO<>(resultSearch, fn);
    }



    @Override
    public BoardDTO get(Long bno) {

        Object result = boardRepository.getBoardByBno(bno);
        Object[] arr = (Object[])result;

        List<Object[]> resultBoard = boardRepository.getBoardWithAll(bno);

        List<BoardImage> boardImageList = new ArrayList<>();

        resultBoard.forEach(arrBoard -> {
            BoardImage  boardImage = (BoardImage)arrBoard[1];
            boardImageList.add(boardImage);
        });


        return entityToDTO((Board)arr[0], boardImageList, (Member)arr[1], (Long)arr[2]);
    }



    @Transactional
    @Override
    public void removeWithReplies(Long bno) {

        replyRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);
    }



    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {

        Board board = boardRepository.getOne(boardDTO.getBno());

        if(board != null) {
            board.changeTitle(boardDTO.getTitle());
            board.changeContent(boardDTO.getContent());
            boardRepository.save(board);
        }
    }
}
