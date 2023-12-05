package com.project.bookforeast.user.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bookforeast.user.entity.User;
import com.project.bookforeast.user.repository.UserRepository;

@Service
public class NicknameServiceImpl implements NicknameService {
	
	private final UserRepository userRepository;
	
	@Autowired
	public NicknameServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	private String getUniqueAdjectives() {
		List<String> adjectives = List.of(
			"밝은", "신속한", "활기찬", "따뜻한", "창조적인",
            "낙관적인", "도전적인", "풍부한", "자유로운", "활발한",
            "꾸준한", "신뢰성있는", "쾌활한", "발전하는", "용기있는",
            "영감을주는", "창의적인", "끈기있는", "포근한", "다양한",
            "흥미로운", "긍정적인", "확신하는", "자율적인", "열린",
            "예술적인", "도움이되는", "효과적인", "자기주장하는", "매력적인",
            "책읽는", "활동적인", "재치있는"
		);
		
		Random random = new Random();
		return adjectives.get(random.nextInt(adjectives.size()));
	}
	
	
	
	private String getUniqueNoun() {
		List<String> nouns = List.of(
			"애서가", "독서가", "수집가",
			"멋쟁이", "귀요미", "토론가", "작가", "재주꾼",
			"이야기꾼"
		);
		
		Random random = new Random();
		return nouns.get(random.nextInt(nouns.size()));
	}
	
	
	public String createNickname() {
		String nickname = getUniqueAdjectives() + " " + getUniqueNoun();
		
		List<User> sameNicknameUsers = userRepository.findByNicknameContaining(nickname);
		
		if(sameNicknameUsers.size() == 0) {
			return nickname;
		} else {
			return nickname + sameNicknameUsers.size();
		}
	}
}
