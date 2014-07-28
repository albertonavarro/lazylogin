package com.navid.lazylogin.services;

import com.navid.lazylogin.domain.SessionId;
import com.navid.lazylogin.persistence.Persistence;
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
    
    public SessionId getUserInfo( String sessionId ) {
        SessionId ssoId = persistence.findOneSessionId( sessionId );
        
        return ssoId;
    }
    
}
