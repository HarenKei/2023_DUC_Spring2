package com.example.ch09.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.ch09.JwtProperties;
import com.example.ch09.domain.User;
import com.example.ch09.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import java.time.Duration;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("generatoeToken() : 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void generateToken() {
        //given
        User testUser = userRepository.save(User.builder().email("user@gmail.com").password("test").build());

        //when
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));
        System.out.println(token);

        //then
        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        System.out.println("testUser.getId() : "+ testUser.getId());
        System.out.println("userId : "+ userId);

        assertThat(userId).isEqualTo(testUser.getId());
    }

    @DisplayName("validToken() : 만료된 토큰인 때에 유효성 검증에 실패한다.")
    @Test
    void validToken_invalidToken() {
        //given
        String token = JwtFactory.builder().expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis())).build().createToken(jwtProperties);
        System.out.println(token);
        //when
        boolean result = tokenProvider.validToken(token);
        //then
        assertThat(result).isFalse();
    }

    @DisplayName("validToken() : 만료된 토큰인 때에 유효성 검증에 성공한다.")
    @Test
    void validToken_validToken() {
        //given
        String token = JwtFactory.withDefaultValues().createToken(jwtProperties);
        System.out.println(token);
        //when
        boolean result = tokenProvider.validToken(token);
        //then
        assertThat(result).isTrue();
    }

    @DisplayName("getAuthentication() : 토큰 기반으로 이증 정보를 가져올 수 있다.")
    @Test
    void getAuthentication() {

        //given
        String userEmail = "user@gmail.com";
        String token = JwtFactory.builder().subject(userEmail).build().createToken(jwtProperties);
        //when
        Authentication authentication = tokenProvider.getAuthentication(token);
        //then
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        assertThat(userDetails.getUsername()).isEqualTo(userEmail);
    }
}