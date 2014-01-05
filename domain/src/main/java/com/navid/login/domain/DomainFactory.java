/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.navid.login.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

/**
 *
 * @author alberto
 */
public class DomainFactory {
    
    private static final int VERSION = 1;
    
    private static final Random RANDOM = new Random();
    
    
    public static String sessionIdGenerator ( String userId, Long token ) {
        String firstComponent = String.valueOf(userId.hashCode()).substring(1, 6);
        
        String secondComponent = String.valueOf(RANDOM.nextInt());
        
        String thirdComponent = String.valueOf((String.valueOf(token.hashCode())+secondComponent).hashCode()).substring(1,6);
        
        return VERSION + "-" + firstComponent + "-" + secondComponent.substring(1, 6) + "-" + thirdComponent;
    }
    
}
