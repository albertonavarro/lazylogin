package com.navid.lazylogin.persistence.hibernate;

import com.navid.lazylogin.persistence.hibernate.domain.UserHb;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

/**
 *
 * @author alberto
 */
@Component
public class Queries {
    
  @PersistenceContext
  private EntityManager entityManager;
  
  public UserHb findSingleUserByEmail(String email) {
    
    Query query = entityManager.createQuery("from UserHb where email = :email");
    query.setParameter("email", email);
    
    List<UserHb> results = query.getResultList();
    
    if(results.size() > 1){
        throw new IllegalStateException("Inconsistent database, found " + results.size() + " results for " + email);
    }
    
    if (results.isEmpty()){
        return null;
    }
    
    return results.get(0);
  }
  
    
}
