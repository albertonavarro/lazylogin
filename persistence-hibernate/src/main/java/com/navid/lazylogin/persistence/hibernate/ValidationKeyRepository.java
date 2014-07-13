package com.navid.lazylogin.persistence.hibernate;

import com.navid.lazylogin.persistence.hibernate.domain.ValidationKeyHb;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alberto
 */
public interface ValidationKeyRepository extends JpaRepository<ValidationKeyHb, String>{
    
}
