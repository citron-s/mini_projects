package org.vito.board.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.vito.board.entity.Member;
import org.vito.board.repository.MemberRepository;
import org.vito.board.security.dto.AuthMemberDTO;
import org.vito.board.repository.MemberRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubUserDetailsService  implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       // log.info("ClubUserDetailsService loadUserByUsername " + username);
        Optional<Member> result = memberRepository.findByUserid(username, false);

        if(result.isEmpty()){
            throw new UsernameNotFoundException("Check User Email or from Social ");
        }

        Member clubMember = result.get();

      //  log.info("-----------------------------");
      //  log.info(clubMember);

        AuthMemberDTO clubAuthMember = new AuthMemberDTO(
                clubMember.getUserid(),
                clubMember.getUserpw(),
                clubMember.isFromSocial(),
                clubMember.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
                        .collect(Collectors.toSet())
        );

        clubAuthMember.setUsername(clubMember.getUsername());
        clubAuthMember.setFromSocial(clubMember.isFromSocial());

        return clubAuthMember;
    }
}
