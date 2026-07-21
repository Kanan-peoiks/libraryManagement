package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.MemberRequestDTO;
import com.example.librarymanagement.dto.MemberResponseDTO;
import com.example.librarymanagement.exception.NotFoundException;
import com.example.librarymanagement.model.Member;
import com.example.librarymanagement.repository.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepo memberRepository;

    public MemberResponseDTO createMember(MemberRequestDTO requestDTO) {

        Member newMember = new Member();
        newMember.setName(requestDTO.getName());
        newMember.setEmail(requestDTO.getEmail());
        newMember.setMembershipDate(LocalDate.now());

        Member savedMember = memberRepository.save(newMember);

        MemberResponseDTO responseDTO = new MemberResponseDTO();
        responseDTO.setMemberId(savedMember.getId());
        responseDTO.setName(savedMember.getName());
        responseDTO.setEmail(savedMember.getEmail());
        responseDTO.setMemberBirthDate(savedMember.getMembershipDate());

        return responseDTO;
    }

    public MemberResponseDTO getMemberById(Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Üzv tapılmadı. ID: " + id));

        MemberResponseDTO responseDTO = new MemberResponseDTO();
        responseDTO.setMemberId(member.getId());
        responseDTO.setName(member.getName());
        responseDTO.setEmail(member.getEmail());
        responseDTO.setMemberBirthDate(member.getMembershipDate());

        return responseDTO;
    }

    //səhifləmə üçün
    public Page<MemberResponseDTO> getAllMembers(Pageable pageable) {

        Page<Member> membersPage = memberRepository.findAll(pageable);

        return membersPage.map(member -> {
            MemberResponseDTO responseDTO = new MemberResponseDTO();
            responseDTO.setMemberId(member.getId());
            responseDTO.setName(member.getName());
            responseDTO.setEmail(member.getEmail());
            responseDTO.setMemberBirthDate(member.getMembershipDate());
            return responseDTO;
        });
    }

    public MemberResponseDTO updateMember(Long id, MemberRequestDTO requestDTO) {

        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Üzv tapılmadı. ID: " + id));

        existingMember.setName(requestDTO.getName());
        existingMember.setEmail(requestDTO.getEmail());

        Member updatedMember = memberRepository.save(existingMember);

        MemberResponseDTO responseDTO = new MemberResponseDTO();
        responseDTO.setMemberId(updatedMember.getId());
        responseDTO.setName(updatedMember.getName());
        responseDTO.setEmail(updatedMember.getEmail());
        responseDTO.setMemberBirthDate(updatedMember.getMembershipDate());

        return responseDTO;
    }

    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("Üzv tapılmadı. ID: " + id);
        }
        memberRepository.deleteById(id);
    }
}