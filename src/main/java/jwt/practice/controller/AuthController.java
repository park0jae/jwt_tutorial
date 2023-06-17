package jwt.practice.controller;

import jwt.practice.dto.LoginDto;
import jwt.practice.dto.TokenDto;
import jwt.practice.jwt.JwtFilter;
import jwt.practice.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/authentication")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto){
        // loginDto의 username, password를 파라미터로 받고 이를 이용해 UsernamePasswordAuthenticationToken을 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        // authenticationToken을 이용해 Authentication 객체를 생성하기 위해 authenticate 메소드가 실행 될 때 loadUserByUsername 메서드가 실행됨
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // authentication 객체를 SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        // Authentication 객체를 createToken 메서드를 통해 JWT Token 을 생성
        String jwt = tokenProvider.createToken(authenticate);

        // JWT Token을 Response Header에도 넣어주고, TokenDto를 이용해서 Response Body에도 넣어서 리턴
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }
}
