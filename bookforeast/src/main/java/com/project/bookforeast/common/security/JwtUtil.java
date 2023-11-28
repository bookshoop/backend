package com.project.bookforeast.common.security;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.project.bookforeast.dto.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtUtil {

	@Value("${jwt.secret-key}")
	private String SECRETKEY;
	
	@Value("${jwt.access-token-expiration-period}")
	private Long ACCESS_TOKEN_EXPIRATION_PERIOD;
	
	@Value("${jwt.refresh-token-expiration-period}")
	private Long REFRESH_TOKEN_EXPIRATION_PERIOD;


	public String generateAccessToken(UserDTO userDTO) {
		return createToken(ACCESS_TOKEN_EXPIRATION_PERIOD, userDTO);
	}

	
	public String generateRefreshToken(UserDTO userDTO) {
		return createToken(REFRESH_TOKEN_EXPIRATION_PERIOD, userDTO);
	}
	
	
	private String createToken(Long expirationPeriod, UserDTO userDTO) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", userDTO.getSocialId());
		claims.put("iss", userDTO.getSocialProvider());
		
		
		return Jwts.builder()
				.addClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expirationPeriod))
				.signWith(SignatureAlgorithm.HS256, SECRETKEY)
				.compact();
	}


	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();
		return claimsResolver.apply(claims);
	}
	
	
	public Boolean validateToken(String token, String socialId, String socialProvider) {
		final String socialIdInToken = extractClaim(token, Claims::getSubject);
		final String socialProviderInToken = extractClaim(token, Claims::getIssuer);
		boolean isUser = socialIdInToken.equals(socialId)  && socialProviderInToken.equals(socialProvider);

		Date expirationDate = extractClaim(token, Claims::getExpiration);
		boolean isTokenExpired = expirationDate.before(new Date());
		
		return isUser && !isTokenExpired;
	}
	
	
	public Map<String, String> initToken(UserDTO userDTO) {
		Map<String, String> tokenMap = new HashMap<>();
		String accessToken = generateAccessToken(userDTO);
		String refreshToken = generateRefreshToken(userDTO);
		
		tokenMap.put("accessToken", accessToken);
		tokenMap.put("refreshToken", refreshToken);
		
		return tokenMap;
	}
	
	public Map<String, String> refreshingToken(UserDTO userDTO, String refreshToken) {
		Map<String, String> tokenMap = new HashMap<>();
		String accessToken = generateAccessToken(userDTO);
		
		tokenMap.put("accessToken", accessToken);
		tokenMap.put("refreshToken", refreshToken);
		
		return tokenMap;
	}
	
	public String extractTokenFromHeader(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if(StringUtils.hasText(header) && header.startsWith("Bearer ")) {
			return header.substring(7);
		}
		return null;
	}
}
