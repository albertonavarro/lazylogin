package com.navid.lazylogin.persistence.jpa.converter;

import com.navid.lazylogin.domain.SessionId;
import com.navid.lazylogin.domain.Token;
import com.navid.lazylogin.domain.User;
import com.navid.lazylogin.domain.UserId;
import com.navid.lazylogin.domain.ValidationKey;
import com.navid.lazylogin.persistence.jpa.domain.SessionIdHb;
import com.navid.lazylogin.persistence.jpa.domain.TokenHb;
import com.navid.lazylogin.persistence.jpa.domain.UserHb;
import com.navid.lazylogin.persistence.jpa.domain.ValidationKeyHb;
import org.jdto.DTOBinderFactory;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class JdtoConverterTest {

    private Converter converter;

    @BeforeClass
    public void setUp() {
        converter = new JdtoConverter(DTOBinderFactory.buildBinder("/conf/jdto-mapping-persistence-hibernate.xml"));
    }

    @Test
    public void shouldTransformUser() {
        User originalUser = new User("email", new UserId(3L));
        originalUser.setName("username");
        UserHb userHb = converter.convert(originalUser);
        assertEquals(userHb.getEmail(), "email");
        assertEquals(userHb.getUserId(), (Long) 3L);
        assertEquals(userHb.getUsername(), "username");
        User newUser = converter.convert(userHb);
        assertEquals(newUser.getEmail(), "email");
        assertEquals(newUser.getUserId().getValue(), (Long) 3L);
        assertEquals(newUser.getName(), "username");
    }

    @Test
    public void shouldTransformToken() {
        User user = new User("email", new UserId(1L));
        user.setName("name");
        Token originalToken = new Token(user, "tokenvalue", true);
        TokenHb tokenHb = converter.convert(originalToken);
        assertNotNull(tokenHb.getUser());
        assertEquals(tokenHb.getUser().getEmail(), "email");
        assertEquals(tokenHb.getUser().getUserId(), (Long) 1L);
        assertEquals(tokenHb.getUser().getUsername(), "name");

        assertEquals(tokenHb.getValidated(), (Boolean) true);
        assertEquals(tokenHb.getValue(), "tokenvalue");
        Token newToken = converter.convert(tokenHb);
        assertNotNull(newToken.getUser());
        assertEquals(newToken.getUser().getEmail(), "email");
        assertEquals(newToken.getUser().getUserId().getValue(), (Long) 1L);
        assertEquals(newToken.getUser().getName(), "name");
        assertEquals(newToken.getValidated(), (Boolean) true);
        assertEquals(newToken.getValue(), "tokenvalue");
    }

    @Test
    public void shouldTransformSessionId() {
        SessionId originalSsoId = new SessionId(new Token(null, null, Boolean.TRUE), "ssoid-value");
        SessionIdHb ssoIdHb = converter.convert(originalSsoId);
        assertNotNull(ssoIdHb.getToken());
        assertEquals(ssoIdHb.getValue(), "ssoid-value");
        SessionId newSsoId = converter.convert(ssoIdHb);
        assertNotNull(newSsoId.getToken());
        assertEquals(newSsoId.getValue(), "ssoid-value");
    }

    @Test
    public void shouldTransformValidationKey() {
        ValidationKey originalValidationKey = new ValidationKey(new Token(null, null, Boolean.TRUE), "validationkey-value");
        ValidationKeyHb validationKeyHb = converter.convert(originalValidationKey);
        assertNotNull(validationKeyHb.getToken());
        assertEquals(validationKeyHb.getValidationKey(), "validationkey-value");
        ValidationKey newValidationKey = converter.convert(validationKeyHb);
        assertNotNull(newValidationKey.getToken());
        assertEquals(newValidationKey.getValidationKey(), "validationkey-value");
    }
}
