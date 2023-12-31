package jwt.practice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jwt.practice.domain.Member;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 회원 가입시 사용
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    @NotNull
    @Size(min =3, max =50)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 50)
    private String password;

    @NotNull
    @Size(min =3, max =50)
    private String nickname;

}
