package org.vito.board.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.vito.board.security.dto.AuthMemberDTO;



@Controller
@Log4j2
@RequestMapping("/security/")
public class SecurityController {

    @GetMapping("/all")
    public void exAll(){
        log.info("All..........");
    }


    @GetMapping("/admin")
    public void exAdmin(){
        log.info("Admin..........");
    }

    @GetMapping("/member")
    public void exMember(@AuthenticationPrincipal AuthMemberDTO authMember){

        log.info("Member..........");

        log.info("-------------------------------");
        log.info(authMember);

    }

}
