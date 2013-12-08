/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.navid.login.persistence;

import com.navid.login.domain.ValidationKey;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alberto
 */
public interface ValidationKeyRepository extends JpaRepository<ValidationKey, String>{
    
}
