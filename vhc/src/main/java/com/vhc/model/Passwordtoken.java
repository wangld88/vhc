package com.vhc.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the PASSWORDTOKENS database table.
 *
 */
@Entity
@Table(name="passwordtokens")
@NamedQuery(name="Passwordtoken.findAll", query="SELECT p FROM Passwordtoken p")
public class Passwordtoken implements Serializable {
	private static final long serialVersionUID = 1L;

    private static final int EXPIRATION = 60 * 24;

	@Id
	private long passwordtokenid;

	@Temporal(TemporalType.DATE)
	private Date expirydate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "userid")
	private User user;

	private String token;

	public Passwordtoken() {
	}

    public Passwordtoken(final User emhcuser, final String token) {
        super();

        this.token = token;
        this.user = emhcuser;
        this.expirydate = calculateExpirydate(EXPIRATION);
    }

	public long getPasswordtokenid() {
		return this.passwordtokenid;
	}

	public void setPasswordtokenid(long passwordtokenid) {
		this.passwordtokenid = passwordtokenid;
	}

	public Date getExpirydate() {
		return this.expirydate;
	}

	public void setExpirydate(Date expirydate) {
		this.expirydate = expirydate;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

    private Date calculateExpirydate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expirydate = calculateExpirydate(EXPIRATION);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((expirydate == null) ? 0 : expirydate.hashCode());
        result = prime * result + ((token == null) ? 0 : token.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Passwordtoken other = (Passwordtoken) obj;
        if (expirydate == null) {
            if (other.expirydate != null) {
                return false;
            }
        } else if (!expirydate.equals(other.expirydate)) {
            return false;
        }
        if (token == null) {
            if (other.token != null) {
                return false;
            }
        } else if (!token.equals(other.token)) {
            return false;
        }
        if (user == null) {
            if (other.user != null) {
                return false;
            }
        } else if (!user.equals(other.user)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Token [String=").append(token).append("]").append("[Expires").append(expirydate).append("]");
        return builder.toString();
    }

}