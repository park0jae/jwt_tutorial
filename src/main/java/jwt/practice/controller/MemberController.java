package jwt.practice.controller;

import jwt.practice.domain.Member;
import jwt.practice.dto.MemberDto;
import jwt.practice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Member> signup(@Valid @RequestBody MemberDto memberDto){
        return ResponseEntity.ok(memberService.signup(memberDto));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Member> getMyMemberInfo(){
        return ResponseEntity.ok(memberService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Member> getMemberInfo(@PathVariable String username){
        return ResponseEntity.ok(memberService.getUserWithAuthorities(username).get());
    }
}
