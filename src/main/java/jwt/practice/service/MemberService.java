package jwt.practice.service;

import jwt.practice.domain.Authority;
import jwt.practice.domain.Member;
import jwt.practice.dto.MemberDto;
import jwt.practice.repository.MemberRepository;
import jwt.practice.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;;

    // 회원가입 로직으로, 일반 회원이므로 ROLE_USER 권한
    @Transactional
    public Member signup(MemberDto memberDto){
        if(memberRepository.findOneWithAuthoritiesByUsername(memberDto.getUsername()).orElse(null) != null){
            throw new RuntimeException("이미 가입되어 있는 회원입니다.");
        }
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        Member member = Member.builder()
                .username(memberDto.getUsername())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .nickname(memberDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return memberRepository.save(member);
    }

    // 유저이름으로 유저 객체와 권한정보 가져옴
    public Optional<Member> getUserWithAuthorities(String username){
        return memberRepository.findOneWithAuthoritiesByUsername(username);
    }

    // SecurityContext에 저장된 username의 정보만 가져옴
    public Optional<Member> getMyUserWithAuthorities(){
        return SecurityUtil.getCurrentUsername().flatMap(memberRepository::findOneWithAuthoritiesByUsername);
    }
}
