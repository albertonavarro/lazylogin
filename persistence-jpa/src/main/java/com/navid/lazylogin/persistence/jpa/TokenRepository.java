package com.navid.lazylogin.persistence.jpa;

import com.navid.lazylogin.persistence.jpa.domain.TokenHb;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alberto
 */
public interface TokenRepository extends JpaRepository<TokenHb, String> {

}
