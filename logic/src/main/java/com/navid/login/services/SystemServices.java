package com.navid.login.services;

import com.navid.login.domain.SsoId;
import com.navid.login.persistence.Persistence;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author alberto
 */
@Service
@Transactional
public class SystemServices {
    
    @Resource
    private Persistence persistence;
    
    public SsoId getUserInfo( String sessionId ) {
        SsoId ssoId = persistence.findOneSessionId( sessionId );
        
        return ssoId;
    }
    
}
