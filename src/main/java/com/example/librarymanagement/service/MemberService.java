package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.MemberRequestDTO;
import com.example.librarymanagement.dto.MemberResponseDTO;
import com.example.librarymanagement.exception.NotFoundException;
import com.example.librarymanagement.mapper.MemberMapper;
import com.example.librarymanagement.model.Member;
import com.example.librarymanagement.repository.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepo memberRepository;
    private final MemberMapper memberMapper;

    @Transactional
    public MemberResponseDTO createMember(MemberRequestDTO requestDTO) {
        Member member = memberMapper.toEntity(requestDTO);
        member.setMembershipDate(LocalDate.now());

        Member savedMember = memberRepository.save(member);
        return memberMapper.toResponseDTO(savedMember);
    }

    @Transactional(readOnly = true)
    public Page<MemberResponseDTO> getAllMembers(Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(memberMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public MemberResponseDTO getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Üzv tapılmadı. ID: " + id));
        return memberMapper.toResponseDTO(member);
    }

    @Transactional
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new NotFoundException("Silinəcək üzv tapılmadı. ID: " + id);
        }
        memberRepository.deleteById(id);
    }
}