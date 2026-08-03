package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.MemberRequestDTO;
import com.example.librarymanagement.dto.MemberResponseDTO;
import com.example.librarymanagement.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponseDTO> createMember(@Valid @RequestBody MemberRequestDTO requestDTO) {
        MemberResponseDTO createdMember = memberService.createMember(requestDTO);
        return new ResponseEntity<>(createdMember, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<MemberResponseDTO>> getAllMembers(Pageable pageable) {
        return ResponseEntity.ok(memberService.getAllMembers(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> updateMember(
            @PathVariable Long id,
            @Valid @RequestBody MemberRequestDTO requestDTO) {
        return ResponseEntity.ok(memberService.updateMember(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
