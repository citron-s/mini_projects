package org.vito.board.repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.vito.board.entity.Board;
import org.vito.board.entity.Member;
import org.vito.board.entity.Reply;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply() {

        IntStream.rangeClosed(1, 10).forEach(i -> {

            long bno  = (long)(Math.random() * 10) + 1;

            Member member = Member.builder().userid("userId"+i).build();
            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .text("Reply......." +i)
                    .bno(board)
                    .replyer(member)
                    .build();

            replyRepository.save(reply);


        });

    }

    @Test
    public void readReply1() {

        Optional<Reply> result = replyRepository.findById(1L);

        Reply reply = result.get();

        System.out.println(reply);
        System.out.println(reply.getBno());

    }


}
