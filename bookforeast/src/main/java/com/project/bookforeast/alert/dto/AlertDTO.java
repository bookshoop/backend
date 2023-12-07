package com.project.bookforeast.alert.dto;

import java.time.LocalDateTime;

import com.project.bookforeast.alert.entity.Alert;
import com.project.bookforeast.user.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertDTO {

	private Long alertId;
	private UserDTO userDTO;
	private String category;
	private String message;
	private LocalDateTime registDt;
	
	
	public Alert toEntity() {
		Alert.AlertBuilder alertBuilder = Alert.builder()
											.alertId(alertId)
											.category(category)
											.message(message);
		
		if(userDTO != null) {
			alertBuilder.user(userDTO.toEntity());
		}
		
		return alertBuilder.build();
								
	}
}
