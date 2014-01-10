package com.navid.login.persistence.hibernate;

import com.navid.login.persistence.hibernate.domain.TokenHb;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alberto
 */
public interface TokenRepository extends JpaRepository<TokenHb, String>{
    
}
