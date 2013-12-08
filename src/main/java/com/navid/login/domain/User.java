/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.navid.login.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author alberto
 */
@Entity
@Table(name = "USER2")
public class User implements Serializable {
    
    protected User() {
        this.username = null;
    }
    
    public User(String name){
        this.username = name;
    }
    
    @Id
    @Column(name = "USER_ID")
    private final String username;
    
   
    
}
