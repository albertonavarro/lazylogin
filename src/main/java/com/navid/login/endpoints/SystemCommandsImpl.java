
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.navid.login.endpoints;

import com.navid.login.SystemCommands;
import java.util.logging.Logger;

/**
 * This class was generated by Apache CXF 2.7.7
 * 2013-12-05T22:40:49.409Z
 * Generated source version: 2.7.7
 * 
 */

@javax.jws.WebService(endpointInterface = "com.navid.login.SystemCommands")
                      
public class SystemCommandsImpl implements SystemCommands {

    private static final Logger LOG = Logger.getLogger(SystemCommandsImpl.class.getName());

    /* (non-Javadoc)
     * @see com.navid.login.SystemCommands#getUserInfo(java.lang.String  arg0 )*
     */
    public com.navid.login.UserInfo getUserInfo(java.lang.String arg0) { 
        LOG.info("Executing operation getUserInfo");
        System.out.println(arg0);
        try {
            com.navid.login.UserInfo _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
