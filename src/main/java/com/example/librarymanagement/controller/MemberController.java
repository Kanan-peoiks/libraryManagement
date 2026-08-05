package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.MemberRequestDTO;
import com.example.librarymanagement.dto.MemberResponseDTO;
import com.example.librarymanagement.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Tag(name = "Member Controller", description = "Üzvlərin idarə olunması üçün API-lər")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @Operation(summary = "Yeni üzv qeydiyyatı")
    public ResponseEntity<MemberResponseDTO> createMember(@Valid @RequestBody MemberRequestDTO requestDTO) {
        return new ResponseEntity<>(memberService.createMember(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Bütün üzvləri səhifələmə (pagination) ilə gətirmək")
    public ResponseEntity<Page<MemberResponseDTO>> getAllMembers(Pageable pageable) {
        return ResponseEntity.ok(memberService.getAllMembers(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "ID-yə görə üzvü gətirmək")
    public ResponseEntity<MemberResponseDTO> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "ID-yə görə üzv məlumatlarını yeniləmək")
    public ResponseEntity<MemberResponseDTO> updateMember(@PathVariable Long id, @Valid @RequestBody MemberRequestDTO requestDTO) {
        return ResponseEntity.ok(memberService.updateMember(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "ID-yə görə üzvü silmək")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}