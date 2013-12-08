package com.navid.login.persistence;

import com.navid.login.domain.SsoId;
import com.navid.login.domain.Token;
import com.navid.login.domain.User;
import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations="classpath:META-INF/application-context.xml")
@ContextConfiguration(locations="classpath:conf/config-persistence-test.xml")
public class SsoIdRepositoryTest {

	@Autowired
	SsoIdRepository repository;
        
        @Autowired
        TokenRepository tokenRepo;
        
        @Autowired
        UserRepository userRepo;
	
	@Test
	public void test() {
		SsoId ssoId = new SsoId(new Token(new User("c")));
		
                userRepo.save(ssoId.getToken().getUser());
                tokenRepo.save(ssoId.getToken());
		repository.save(ssoId);
		
		SsoId dbpost = repository.findOne(ssoId.getValue());
		assertNotNull(dbpost);
	}

}
