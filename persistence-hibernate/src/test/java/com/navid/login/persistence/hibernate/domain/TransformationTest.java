package com.navid.login.persistence.hibernate.domain;

import com.navid.login.domain.SsoId;
import com.navid.login.domain.Token;
import com.navid.login.domain.User;
import com.navid.login.domain.UserId;
import com.navid.login.domain.ValidationKey;
import org.jdto.DTOBinder;
import org.jdto.DTOBinderFactory;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class TransformationTest {
    
    private DTOBinder binder;
    
    @BeforeClass
    public void setUp(){
        binder = DTOBinderFactory.buildBinder("/conf/jdto-mapping-persistence-hibernate.xml");
    }
    
    @Test
    public void shouldTransformUser() {
        User originalUser = new User("email", "username", new UserId(3L));
        
        UserHb userHb = binder.bindFromBusinessObject(UserHb.class, originalUser);
        
        assertEquals(userHb.getEmail(), "email");
        assertEquals(userHb.getUserId(), (Long) 3L);
        assertEquals(userHb.getUsername(), "username");
        
        User newUser = binder.bindFromBusinessObject(User.class, userHb);
        
        assertEquals(newUser.getEmail(), "email");
        assertEquals(newUser.getUserId().getValue(), (Long) 3L);
        assertEquals(newUser.getName(), "username");
    }
    
    @Test
    public void shouldTransformToken() {
        Token originalToken = new Token(new User(null, null, null), 4L, true);
        
        TokenHb tokenHb = binder.bindFromBusinessObject(TokenHb.class, originalToken);
        assertNotNull(tokenHb.getUser());
        assertEquals(tokenHb.getValidated(), (Boolean) true);
        assertEquals(tokenHb.getValue(), (Long) 4L);
        
        Token newToken = binder.bindFromBusinessObject(Token.class, tokenHb);
        assertNotNull(newToken.getUser());
        assertEquals(newToken.getValidated(), (Boolean) true);
        assertEquals(newToken.getValue(), (Long) 4L);
    }
    
    @Test
    public void shouldTransformSessionId() {
        SsoId originalSsoId = new SsoId(new Token(null, Long.MIN_VALUE, Boolean.TRUE), "ssoid-value");
        
        SsoIdHb ssoIdHb = binder.bindFromBusinessObject(SsoIdHb.class, originalSsoId);
        assertNotNull(ssoIdHb.getToken());
        assertEquals(ssoIdHb.getValue(), "ssoid-value");
        
        SsoId newSsoId = binder.bindFromBusinessObject(SsoId.class, ssoIdHb);
        assertNotNull(newSsoId.getToken());
        assertEquals(newSsoId.getValue(), "ssoid-value");
    }
    
    @Test
    public void shouldTransformValidationKey(){
        ValidationKey originalValidationKey = new ValidationKey(new Token(null, Long.MIN_VALUE, Boolean.TRUE), "validationkey-value");
        
        ValidationKeyHb validationKeyHb = binder.bindFromBusinessObject(ValidationKeyHb.class, originalValidationKey);
        assertNotNull(validationKeyHb.getToken());
        assertEquals(validationKeyHb.getValidationKey(), "validationkey-value");
        
        ValidationKey newValidationKey = binder.bindFromBusinessObject(ValidationKey.class, validationKeyHb);
        assertNotNull(newValidationKey.getToken());
        assertEquals(newValidationKey.getValidationKey(), "validationkey-value");
    }
    
}
