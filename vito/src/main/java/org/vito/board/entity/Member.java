package org.vito.board.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "tbl_member")
public class Member extends BaseEntity {

    @Id
    private String userid;

    private String userpw;

    private String username;


    //upload
    private String charName;   //nickname


    //security
    private boolean fromSocial;

    @ElementCollection(fetch = FetchType.LAZY)
    private Set<MemberRole> roleSet;

    public void addMemberRole(MemberRole clubMemberRole){
        roleSet.add(clubMemberRole);
    }

}
