package com.example.invite.controller;

import com.example.invite.dto.TempMemberDTO;
import com.example.invite.service.TempMemberService;
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
    public TempMemberDTO createGroup(@RequestBody @Validated TempMemberDTO tempMemberDTO, HttpSession session){

        System.out.println(tempMemberDTO);
        TempMemberDTO adminDTO = tempMemberService.saveAdmin(tempMemberDTO);
        session.setAttribute("myId", tempMemberDTO.getId());
        return adminDTO;
    }



    @PostMapping("/temp/invite")
    public TempMemberDTO invite(@ModelAttribute TempMemberDTO inviteeDTO, HttpSession session){
        //테스트용
        session.setAttribute("myId", "1");

        String adminId = (String)session.getAttribute("myId");
        TempMemberDTO adminDTO = tempMemberService.adminCheck(adminId);

        if(adminDTO != null){
            return tempMemberService.invite(inviteeDTO, adminDTO);
        }else{
            return null;
        }
    }

    @GetMapping("/temp/{invitationLink}/{id}")
    public TempMemberDTO enter(@PathVariable("invitationLink") String invitationLink,
                               @PathVariable("id") Long inviteeId,
                               HttpSession session){
        TempMemberDTO inviteeDTO = tempMemberService.activeCheck(inviteeId);
        session.setAttribute("myId", inviteeDTO.getId());

        return tempMemberService.activeCheck(inviteeId);
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
