package com.project.bookforeast.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@ExtendWith(MockitoExtension.class)
public class NetworkServiceTest {

	
	private final NetworkService networkService;
	
	@Autowired
	private NetworkServiceTest(NetworkService networkService) {
		this.networkService = networkService;
	}
	
	@Test
	public void testIsLocal_Localhost() {
		assertTrue(networkService.isLocal());
	}
	

	public void testIsLocal_NonLocalhost() {
		assertFalse(networkService.isLocal());
	}
}
