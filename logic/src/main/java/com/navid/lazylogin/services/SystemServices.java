package com.navid.lazylogin.services;

import com.navid.lazylogin.domain.SessionId;
import com.navid.lazylogin.persistence.Persistence;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author alberto
 */
@Service
@Transactional
public class SystemServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemServices.class);

    @Resource
    private Persistence persistence;

    public SessionId getUserInfo(String sessionId) {
        LOGGER.info("Requesting user info for sessionId {}", sessionId);

        SessionId ssoId = persistence.findOneSessionId(sessionId);

        LOGGER.debug("Response {}", ssoId);

        return ssoId;
    }

}
