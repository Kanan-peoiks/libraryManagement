package com.example.librarymanagement.mapper;


import com.example.librarymanagement.dto.MemberRequestDTO;
import com.example.librarymanagement.dto.MemberResponseDTO;
import com.example.librarymanagement.model.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toEntity(MemberRequestDTO dto) {
        Member member = new Member();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        return member;
    }

    public void updateEntityFromDto(MemberRequestDTO dto, Member member) {
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
    }

    public MemberResponseDTO toResponseDTO(Member member) {
        MemberResponseDTO responseDTO = new MemberResponseDTO();
        responseDTO.setMemberId(member.getId());
        responseDTO.setName(member.getName());
        responseDTO.setEmail(member.getEmail());
        responseDTO.setMemberBirthDate(member.getMembershipDate());
        return responseDTO;
    }
}