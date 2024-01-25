package com.example.invite.controller;

import com.example.invite.domain.TempMemberEntity;
import com.example.invite.dto.TempMemberDTO;
import com.example.invite.service.TempMemberService;
import com.example.invite.web.form.TempMemberAdminForm;
import com.example.invite.web.form.TempMemberInviteeForm;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GroupRequestController {

    private final TempMemberService tempMemberService;

    @ResponseBody
    @PostMapping("/temp/")
    public TempMemberDTO createGroup(@RequestBody @Validated TempMemberAdminForm tempMemberAdminForm, HttpSession session){

        TempMemberDTO adminDTO =
                tempMemberService.saveAdmin(
                        TempMemberEntity.buildAdminEntity(tempMemberAdminForm)
                );
        session.setAttribute("myId", adminDTO.getId());

        return adminDTO;
    }


    @PostMapping("/temp/invite")
    public TempMemberDTO invite(@ModelAttribute TempMemberInviteeForm inviteeForm, HttpSession session){
        //한명만 초대 가능.
        //테스트용
        session.setAttribute("myId", "1");
        String adminId = (String)session.getAttribute("myId");

        try {
            inviteeForm.setGroupId(tempMemberService.adminCheckAndGetGroupId(adminId));
            return tempMemberService.invite(
                    TempMemberEntity.buildInviteeEntity(inviteeForm)
            );
        }catch(Exception e){
            throw new NullPointerException();
        }
    }

    @GetMapping("/temp/{invitationLink}/{id}")
    public TempMemberDTO enter(@PathVariable("invitationLink") String invitationLink,
                               @PathVariable("id") Long inviteeId,
                               HttpSession session){
        TempMemberDTO inviteeDTO = tempMemberService.activeCheck(inviteeId);
        session.setAttribute("myId", inviteeDTO.getId());
        return inviteeDTO;
    }

    @GetMapping("temp/list")
    public List<TempMemberDTO> list(HttpSession session){
        //테스트용
        session.setAttribute("myId", "2");
        String myId = (String)session.getAttribute("myId");
        List<TempMemberDTO> groupList = tempMemberService.findGroupMembers(myId);
        return groupList;
    }

}
