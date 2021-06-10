package org.vito.board.security;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vito.board.entity.Member;
import org.vito.board.entity.MemberRole;
import org.vito.board.repository.MemberRepository;
import org.vito.board.entity.Member;
import org.vito.board.entity.MemberRole;
import org.vito.board.repository.MemberRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberTests {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies() {



        IntStream.rangeClosed(1,10).forEach(i -> {
            Member member = Member.builder()
                    .userid("sitron"+i+"@sitron.com")
                    .username("사용자"+i)
                    .fromSocial(false)
                    .roleSet(new HashSet<MemberRole>())
                    .userpw(  passwordEncoder.encode("1111") )
                    .build();

            //default role
            member.addMemberRole(MemberRole.MEMBER);

            if(i > 80){
                member.addMemberRole(MemberRole.MEMBER);
            }

            if(i > 90){
                member.addMemberRole(MemberRole.ADMIN);
            }

            repository.save(member);

        });

    }

    @Test
    public void testRead() {

        Optional<Member> result = repository.findByUserid("sitron@sitron.org", false);

        Member clubMember = result.get();

        System.out.println(clubMember);

    }


}
