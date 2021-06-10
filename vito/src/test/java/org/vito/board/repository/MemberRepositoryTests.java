package org.vito.board.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.vito.board.entity.Member;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMembers() {

        IntStream.rangeClosed(1, 10).forEach(i -> {

            Member member = Member.builder()
                    .userid("userId" + i)
                    .userpw("1111")
                    .username("userName" + i)
                    .build();

            memberRepository.save(member);
        });
    }
}