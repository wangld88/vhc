package com.vhc.dto;

//import javax.validation.constraints.Size;

//import org.hibernate.validator.constraints.NotEmpty;


public class ForgetPasswordForm {

   /* @NotEmpty
    @Size(min=0, max=80)*/
    private String email = "";

    /*@NotEmpty
    @Size(min=0, max=50)*/
    private String username = "";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim();
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username.trim();
    }

    @Override
    public String toString() {
        return "ForgetPasswordForm {" +
                "email:'" + email.replaceFirst("@.+", "@***") + "'" +
                ", username:'" + username +
                "'}";
    }

}
