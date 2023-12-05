package com.project.bookforeast.common.security.service;


import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.project.bookforeast.common.security.error.TokenErrorResult;
import com.project.bookforeast.common.security.error.TokenException;
import com.project.bookforeast.user.dto.UserDTO;
import com.project.bookforeast.user.entity.User;
import com.project.bookforeast.user.error.UserErrorResult;
import com.project.bookforeast.user.error.UserException;
import com.project.bookforeast.user.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtUtil {

	
	private final UserRepository userRepository;
	
	@Autowired
	public JwtUtil(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	@Value("${jwt.secret-key}")
	private String SECRETKEY;
	
	
	private Long ACCESS_TOKEN_EXPIRATION_PERIOD = 600000L;

	
	private Long REFRESH_TOKEN_EXPIRATION_PERIOD = 3600000L;


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
		
		String encodedSecretkey = Base64.getEncoder().encodeToString(SECRETKEY.getBytes());
		return Jwts.builder()
				.addClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expirationPeriod))
				.signWith(SignatureAlgorithm.HS256, encodedSecretkey)
				.compact();
	}


	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();
		return claimsResolver.apply(claims);
	}
	
	
	public Boolean validateAccessToken(String token, String socialId, String socialProvider) {
		final String socialIdInToken = extractClaim(token, Claims::getSubject);
		final String socialProviderInToken = extractClaim(token, Claims::getIssuer);
		boolean isUser = socialIdInToken.equals(socialId)  && socialProviderInToken.equals(socialProvider);
		boolean isTokenExpired = checkTokenExpired(token);
		
		return isUser && !isTokenExpired;
	}
	
	
	public Boolean validateRefreshToken(String refreshToken) {
		User user = userRepository.findByRefreshToken(refreshToken);
		
		if(user == null) {
			throw new TokenException(TokenErrorResult.TOKEN_EXPIRED);
		}
		
		String refreshTokenInDB = user.getRefreshToken(); 
		if(!refreshToken.equals(refreshTokenInDB) || !checkTokenExpired(refreshTokenInDB)) {
			throw new TokenException(TokenErrorResult.TOKEN_EXPIRED);
		}
		
		return true;
	}
	
	
	private boolean checkTokenExpired(String token) {
		Date expirationDate = extractClaim(token, Claims::getExpiration);
		boolean isTokenExpired = expirationDate.before(new Date());
		return isTokenExpired;
	}

	
	public Map<String, String> initToken(UserDTO savedOrFindUser) {
		Map<String, String> tokenMap = new HashMap<>();
		String accessToken = generateAccessToken(savedOrFindUser);
		String refreshToken = generateRefreshToken(savedOrFindUser);
		
		tokenMap.put("accessToken", accessToken);
		tokenMap.put("refreshToken", refreshToken);
		
		updRefreshTokenInDB(refreshToken, savedOrFindUser);
		
		return tokenMap;
	}
	

	public Map<String, String> refreshingAccessToken(UserDTO userDTO, String refreshToken) {
		Map<String, String> tokenMap = new HashMap<>();
		String accessToken = generateAccessToken(userDTO);
		
		tokenMap.put("accessToken", accessToken);
		tokenMap.put("refreshToken", refreshToken);
		
		return tokenMap;
	}
	
	
	private void updRefreshTokenInDB(String refreshToken, UserDTO savedOrFindUser) {
		savedOrFindUser.setRefreshToken(refreshToken);
		userRepository.save(savedOrFindUser.toEntity());
	}

	
	public String extractTokenFromHeader(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if(StringUtils.hasText(header) && header.startsWith("Bearer ")) {
			return header.substring(7);
		}
		return null;
	}
}
