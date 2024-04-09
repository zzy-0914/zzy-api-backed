package com.zzy.zzyapiinterface;

import com.example.zzyclientsdk.client.ZzyApiClient;
import com.example.zzyclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ZzyapiInterfaceApplicationTests {

	@Resource
	private  ZzyApiClient zzyApiClient;

	@Test
	void contextLoads() {
		String result = zzyApiClient.getNameByGet("zzy");
		User user = new User();
		user.setUsername("zzy");
		String nameByPost = zzyApiClient.getUserNameByPost(user);
		System.out.println(result);
		System.out.println(nameByPost);
	}

	public static void main(String[] args) {
		ZzyApiClient zzyApiClient1 = new ZzyApiClient("zzy", "nb");
	}
}
