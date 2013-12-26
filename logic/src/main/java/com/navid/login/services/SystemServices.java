/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.navid.login.services;

import com.navid.login.domain.SsoId;
import com.navid.login.domain.UserId;
import com.navid.login.domain.UserInfo;
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
    
    public UserInfo getUserInfo( String sessionId ) {
        SsoId ssoId = persistence.findOneSessionId( sessionId );
        
        UserInfo userInfo = new UserInfo(new UserId("1"), ssoId.getToken().getUser().getEmail());
        userInfo.setValidated(ssoId.getToken().getVerified());
        
        return userInfo;
    }
    
}
