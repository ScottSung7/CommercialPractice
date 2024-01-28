package com.example.invite.service;

import com.example.invite.domain.GroupEntity;
import com.example.invite.domain.MemberStatus;
import com.example.invite.domain.TempMemberEntity;
import com.example.invite.dto.TempMemberDTO;
import com.example.invite.repository.GroupRepository;
import com.example.invite.repository.TempMemberRepository;
import com.example.invite.web.form.TempMemberAdminForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TempMemberService {

    private final TempMemberRepository tempMemberRepository;

    private final GroupRepository groupRepository;

    public void check(TempMemberDTO dto){
        System.out.println(dto);
    }
    public TempMemberDTO saveAdmin(TempMemberEntity tempMemberEntity) {

        tempMemberEntity = tempMemberRepository.save(tempMemberEntity);

        GroupEntity groupEntity = GroupEntity.createGroupEntity(tempMemberEntity);
        groupRepository.save(groupEntity);


        tempMemberEntity = tempMemberEntity.addGroupId(groupEntity);
        tempMemberEntity = tempMemberRepository.save(tempMemberEntity);

        return TempMemberDTO.toMemberDTO(tempMemberEntity);
    }

    public GroupEntity adminCheckAndGetGroupId(String adminId) {
        Optional<TempMemberEntity> optionalAdminEntity = tempMemberRepository.findById(Long.parseLong(adminId));
        TempMemberEntity temp = (TempMemberEntity)checkOptionalPresent(optionalAdminEntity);
        return temp.getGroupId();
    }

   public TempMemberDTO invite(TempMemberEntity inviteeEntity) {
       return   TempMemberDTO.toMemberDTO(
                    tempMemberRepository.save(inviteeEntity)
                );
   }

    public TempMemberDTO activeCheck(Long inviteeId) {
        Optional<TempMemberEntity> optionalInviteEntity = tempMemberRepository.findById(inviteeId);
        TempMemberEntity inviteeEntity = (TempMemberEntity) checkOptionalPresent(optionalInviteEntity);

        inviteeEntity = TempMemberEntity.activateMember(inviteeEntity);
        tempMemberRepository.save(inviteeEntity);
        return TempMemberDTO.toMemberDTO(inviteeEntity);
    }

    public Object checkOptionalPresent(Optional<?> optionalObject){
        if(optionalObject.isPresent()){
            return optionalObject.get();
        }else{
            throw new NullPointerException();
        }
    }

    public List<TempMemberDTO> findGroupMembers(String myId) {
        Optional<TempMemberEntity> optionalTempMemberEntity = tempMemberRepository.findById(Long.parseLong(myId));
        List<TempMemberEntity> tempMemberEntityList = tempMemberRepository.findByGroupId(optionalTempMemberEntity.get().getGroupId());

        List<TempMemberDTO> tempMemberDTOList = new ArrayList<>();
        for(TempMemberEntity tempMemberEntity : tempMemberEntityList){
            tempMemberDTOList.add(TempMemberDTO.toMemberDTO(tempMemberEntity));
        }

        return tempMemberDTOList;
    }

    public void Test(){
        List<?> hi = new ArrayList<>();
    }
}
