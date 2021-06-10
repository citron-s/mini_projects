package org.vito.board.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long bno;

    private String title;

    private String content;

    private String userid;

    private String username;

    private String imgurl;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    private int replyCount;


    //upload files
    @Builder.Default
    private List<BoardImageDTO> imageDTOList = new ArrayList<>();

}
