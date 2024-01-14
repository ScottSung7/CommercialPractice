package com.demo.invite;

import com.example.invite.domain.Link;
import com.example.invite.service.TempMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor
class InviteApplicationTests {

	@Test
	void groupEntityTest(){
		Link groupEntity = new Link();
		String a = groupEntity.createLink();
		System.out.println(a);
		String b = groupEntity.createLink();
		System.out.println(b);
		String c = groupEntity.createLink();
		System.out.println(c);

		Link groupEntity2 = new Link();
		String d = groupEntity2.createLink();
		System.out.println(d);


	}
}
