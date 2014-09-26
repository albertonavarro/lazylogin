package com.navid.lazylogin.persistence.jpa;

import com.navid.lazylogin.persistence.jpa.domain.ValidationKeyHb;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alberto
 */
public interface ValidationKeyRepository extends JpaRepository<ValidationKeyHb, String>{
    
}
