package org.vito.board.service;


import org.vito.board.dto.BoardDTO;
import org.vito.board.dto.BoardImageDTO;
import org.vito.board.dto.PageRequestDTO;
import org.vito.board.entity.Board;
import org.vito.board.entity.BoardImage;
import org.vito.board.entity.Member;
import org.vito.board.dto.PageResultDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface BoardService {

    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    BoardDTO get(Long bno);

    void removeWithReplies(Long bno);

    void modify(BoardDTO boardDTO);



//    default Board dtoToEntity(BoardDTO dto){
    default Map<String, Object> dtoToEntity(BoardDTO dto){

        Map<String, Object> entityMap = new HashMap<>();
        Member member = Member.builder().userid(dto.getUserid()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .imgurl(dto.getImgurl())
                .writer(member)
                .build();

        entityMap.put("board", board);


        List<BoardImageDTO> imageDTOList = dto.getImageDTOList();

        if(imageDTOList != null && imageDTOList.size() > 0 ) {

            List<BoardImage> boardImageList = imageDTOList.stream().map(movieImageDTO ->{

                BoardImage boardImage = BoardImage.builder()
                                        .path(movieImageDTO.getPath())
                                        .imgName(movieImageDTO.getImgName())
                                        .uuid(movieImageDTO.getUuid())
                                        .board(board)
                                        .build();
                return boardImage;

            }).collect(Collectors.toList());

            entityMap.put("imgList", boardImageList);
        }

        return entityMap;
    }


    default BoardDTO entityToDTO(Board board, List<BoardImage> boardImages, Member member, Long replyCount) {
        /*
        System.out.println("========================================");
        System.out.println(board);
        System.out.println(member);
        System.out.println(replyCount);
        */

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .imgurl(board.getImgurl())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .userid(member.getUserid())
                .username(member.getUsername())
                .replyCount(replyCount.intValue()) //int value.....
                .build();


        List<BoardImageDTO> boardImageDTOList = boardImages.stream().map(boardImage -> {
            return BoardImageDTO.builder().imgName(boardImage.getImgName())
                    .path(boardImage.getPath())
                    .uuid(boardImage.getUuid())
                    .build();
        }).collect(Collectors.toList());


        boardDTO.setImageDTOList(boardImageDTOList);
        //boardDTO.setReviewCnt(reviewCnt.intValue());memberRepository


        return boardDTO;

    }
}
