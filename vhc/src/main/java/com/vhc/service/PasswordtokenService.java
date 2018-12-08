package com.vhc.service;


import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.model.Passwordtoken;
import com.vhc.model.User;
import com.vhc.repository.PasswordtokenRepository;
import com.vhc.security.LoginUser;
import com.vhc.security.UsernamePasswordAuthenticationToken;



@Service
@Transactional
public class PasswordtokenService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordtokenService.class);

	@Autowired
	private PasswordtokenRepository passwordtokenRepository;

	public void createPasswrodtokenForUser(final User emhcuser, final String token) {


		Passwordtoken theToken = getPasswordtokenByUser(emhcuser);

		if(theToken == null || theToken.getToken() == null) {
			theToken = new Passwordtoken(emhcuser, token);
		} else {
			theToken.updateToken(token);
		}

		passwordtokenRepository.save(theToken);
	}

    public Passwordtoken getPasswordtoken(final String token) {
        return passwordtokenRepository.findByToken(token);
    }

    public Passwordtoken getPasswordtokenByUser(final User emhcuser) {
        return passwordtokenRepository.findByUser(emhcuser);
    }

    public String validatePasswordResetToken(long id, String token) {
        final Passwordtoken passToken = getPasswordtoken(token);

        if ((passToken == null) || (passToken.getUser().getUserid() != id)) {
            return "invalidToken";
        }

        final Calendar cal = Calendar.getInstance();
        if ((passToken.getExpirydate().getTime() - cal.getTime().getTime()) <= 0) {
            return "expired";
        }

        final User emhcuser = passToken.getUser();
        final Authentication auth = new UsernamePasswordAuthenticationToken(new LoginUser(emhcuser), null, AuthorityUtils.createAuthorityList("CLIENT"));
        SecurityContextHolder.getContext().setAuthentication(auth);
        LOGGER.debug("====Authentication is set after validate the token===");
        return null;
    }

    public void deleteUsedToken(final User emhcuser, final String token) {
    	Passwordtoken theToken = new Passwordtoken(emhcuser, token);
    	passwordtokenRepository.delete(theToken);
    }

    public void deleteExpiredToken() {
    	Date now = Date.from(Instant.now());
    	passwordtokenRepository.deleteAllExpiredSince(now);
    }
}
