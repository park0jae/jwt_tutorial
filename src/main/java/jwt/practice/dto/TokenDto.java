package jwt.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 토큰 정보 Response 받을 때 사용
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {

    private String token;
}
