package com.example.invite.service;

import com.example.invite.domain.GroupEntity;
import com.example.invite.domain.MemberStatus;
import com.example.invite.domain.TempMemberEntity;
import com.example.invite.dto.TempMemberDTO;
import com.example.invite.repository.GroupRepository;
import com.example.invite.repository.TempMemberRepository;
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
    public TempMemberDTO saveAdmin(TempMemberDTO tempMemberDTO) {
        TempMemberEntity tempMemberEntity = TempMemberEntity.initialAdminEntity(tempMemberDTO);

        tempMemberEntity = tempMemberRepository.save(tempMemberEntity);

        GroupEntity groupEntity = GroupEntity.createGroupEntity(tempMemberEntity);
        groupRepository.save(groupEntity);

        tempMemberEntity = tempMemberEntity.addGroupId(tempMemberEntity, groupEntity);
        tempMemberEntity = tempMemberRepository.save(tempMemberEntity);

        return TempMemberDTO.toMemberDTO(tempMemberEntity);
    }

    public TempMemberDTO adminCheck(String adminId) {
        Optional<TempMemberEntity> optionalAdminEntity = tempMemberRepository.findById(Long.parseLong(adminId));
        if(optionalAdminEntity.isPresent()){
            TempMemberEntity adminEntity = optionalAdminEntity.get();
            System.out.println(adminEntity);
            return TempMemberDTO.toMemberDTO(adminEntity);
        }else{
            return null;
        }
    }

   public TempMemberDTO invite(TempMemberDTO inviteeDTO, TempMemberDTO adminDTO) {
       Optional<TempMemberEntity> optionalAdminEntity = tempMemberRepository.findById(adminDTO.getId());

       TempMemberEntity inviteeEntity = TempMemberEntity.toInviteeEntity(inviteeDTO, optionalAdminEntity.get());
       inviteeEntity = tempMemberRepository.save(inviteeEntity);
       return   TempMemberDTO.toMemberDTO(inviteeEntity);
   }

    public TempMemberDTO activeCheck(Long inviteeId) {
        Optional<TempMemberEntity> optionalInviteEntity = tempMemberRepository.findById(inviteeId);
        if(optionalInviteEntity.isPresent()){
            TempMemberEntity inviteeEntity = optionalInviteEntity.get();
            if(inviteeEntity.getMemberStatus() == MemberStatus.FALSE){
                inviteeEntity = TempMemberEntity.activateMember(inviteeEntity);
                tempMemberRepository.save(inviteeEntity);
                return TempMemberDTO.toMemberDTO(inviteeEntity);
            }else {
                System.err.println("이미 사용된 코드 입니다.");
                return null;
            }
        }else{
            System.err.println("존재하지 않는 코드입니다.");
            return null;
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
}
