package com.mimicki.afinal.Retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 11/21/2016.
 */
public class ServiceModelLoginDriver {


    /**
     * isConnectDBSuccess : true
     * status : true
     * userdata : {"noambulance":"1","username-ambulance":"Dum.ron","password-ambulance":"1234","phone-ambulance":"08000000","status":"going","lat-ambulance":"2.00000000","lng-ambulance":"4.00000000","active":"1","lastname-ambulance":"คงอยู่","firstname-ambulance":"ดำรง","address-ambulance":"ถนน","last-updated-time":"2016-11-07 12:51:45","register-datetime":"2016-10-30 12:27:55","gender-ambulance":"M"}
     */

    private boolean isConnectDBSuccess;
    public boolean status;

    /**
     * noambulance : 1
     * username-ambulance : Dum.ron
     * password-ambulance : 1234
     * phone-ambulance : 08000000
     * status : going
     * lat-ambulance : 2.00000000
     * lng-ambulance : 4.00000000
     * active : 1
     * lastname-ambulance : คงอยู่
     * firstname-ambulance : ดำรง
     * address-ambulance : ถนน
     * last-updated-time : 2016-11-07 12:51:45
     * register-datetime : 2016-10-30 12:27:55
     * gender-ambulance : M
     */

    private UserdataBean userdata;
    private List<String> errmsg;

    public boolean isIsConnectDBSuccess() {
        return isConnectDBSuccess;
    }

    public void setIsConnectDBSuccess(boolean isConnectDBSuccess) {
        this.isConnectDBSuccess = isConnectDBSuccess;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public UserdataBean getUserdata() {
        return userdata;
    }

    public void setUserdata(UserdataBean userdata) {
        this.userdata = userdata;
    }

    public List<String> getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(List<String> errmsg) {
        this.errmsg = errmsg;
    }

    /**
     * noambulance : 1
     * username-ambulance : Dum.ron
     * password-ambulance : 1234
     * phone-ambulance : 08000000
     * status : going
     * lat-ambulance : 2.00000000
     * lng-ambulance : 4.00000000
     * active : 1
     * lastname-ambulance : คงอยู่
     * firstname-ambulance : ดำรง
     * address-ambulance : ถนน
     * last-updated-time : 2016-11-07 12:51:45
     * register-datetime : 2016-10-30 12:27:55
     * gender-ambulance : M
     */

    public static class UserdataBean {
        private String noambulance;
        @SerializedName("username-ambulance")
        private String usernameAm;
        @SerializedName("password-ambulance")
        private String passwordAm;
        @SerializedName("phone-ambulance")
        private String phoneAm;

        private String status;
        @SerializedName("lat-ambulance")
        private String latAm;
        @SerializedName("lng-ambulance")
        private String lngAm;

        private String active;
        @SerializedName("lastname-ambulance")
        private String lastnameAm;
        @SerializedName("firstname-ambulance")
        private String firstnameAm;
        @SerializedName("gender-ambulance")
        private String genderAm;


        public String getNoambulance() {
            return noambulance;
        }

        public void setNoambulance(String noambulance) {
            this.noambulance = noambulance;
        }

        public String getUsernameAm() {
            return usernameAm;
        }

        public void setUsernameAm(String usernameAm) {
            this.usernameAm = usernameAm;
        }

        public String getPasswordAm() {
            return passwordAm;
        }

        public void setPasswordAm(String passwordAm) {
            this.passwordAm = passwordAm;
        }

        public String getPhoneAm() {
            return passwordAm;
        }

        public void setPhoneAm(String phoneAm) {
            this.phoneAm = phoneAm;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLatAm() {
            return latAm;
        }

        public void setLatAm(String latAm) {
            this.latAm = latAm;
        }

        public String getLngAm() {
            return lngAm;
        }

        public void setLngAm(String lngAm) {
            this.lngAm = lngAm;
        }


        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public String getLastnameAm() {
            return lastnameAm;
        }

        public void setLastnameAm(String lastnameAm) {
            this.lastnameAm = lastnameAm;
        }

        public String getFirstnameAm() {
            return firstnameAm;
        }

        public void setFirstnameAm(String firstnameAm) {
            this.firstnameAm = firstnameAm;
        }

        public String getGenderAm() {
            return genderAm;
        }

        public void setGenderAm(String genderAm) {
            this.genderAm = genderAm;
        }
    }

    public String toString() {
        StringBuilder string = new StringBuilder().append("isConnectDBSuccess : " + isConnectDBSuccess + "\n")
                .append("status: " + status + "\n");

        if (errmsg != null ) {
            string.append(errmsg);
        }
        return string.toString();
    }

}
