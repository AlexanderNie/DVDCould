/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.welearn.domain.services;

import com.welearn.domain.entities.Staff;
import com.welearn.utils.HibernateUtil;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author weiwei2017
 */
public class LoginService {
    
    private Map<String, String> staffByUsername = new HashMap<String, String>();

            
    private static String QUERY_BASED_ON_USER = "from Staff a where a.username = '"; 
    public static final String TO_MANY_STAFF = "System exception, please contact admin";
    public static final String NO_SUCH_USER = "Please check your user name";
    public static final String INVALID_PASSWORD = "Please check your password";
    public static final String LOGIN_SUCCESS = "Login success";
    
    
        private void executeHQLQuery(String hql) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            List resultList = q.list();
            setupStaffList(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
    }
        public void getStaffByName(String username)
        {
            executeHQLQuery(QUERY_BASED_ON_USER + username + "'");
        }
        
        public String validateStaff(String username, String password)
        {
            getStaffByName(username);
            if (getStaffByUsername().size() >1)
                return TO_MANY_STAFF;
            if (getStaffByUsername().size() <1)
                return NO_SUCH_USER;
            if (getStaffByUsername().size() == 1  && !validatePassword(username, password))
                return INVALID_PASSWORD;
            return LOGIN_SUCCESS;
        }

    private void setupStaffList(List resultList) {
        for (Object o : resultList) {
            Staff staff = (Staff) o;
            getStaffByUsername().put(staff.getUsername().toLowerCase(), staff.getPassword());
        }
    }

   

    private boolean validatePassword(String username, String userinput) {
        String password = getStaffByUsername().get(username.toLowerCase());
        try {
            if(EncryptionService.encrypt(userinput).equals(password))
                return true;
            else
                return false;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * @return the staffByUsername
     */
    public Map<String, String> getStaffByUsername() {
        return staffByUsername;
    }

    /**
     * @param staffByUsername the staffByUsername to set
     */
    public void setStaffByUsername(Map<String, String> staffByUsername) {
        this.staffByUsername = staffByUsername;
    }
}
