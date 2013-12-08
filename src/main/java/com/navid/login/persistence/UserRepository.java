/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.navid.login.persistence;

import com.navid.login.domain.SsoId;
import com.navid.login.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alberto
 */
public interface UserRepository extends JpaRepository<User, String> {
    
}
