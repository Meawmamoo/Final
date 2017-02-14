package com.mimicki.afinal.Retrofit;

import android.database.sqlite.SQLiteDatabase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 7/22/2016.
 */
public class ServiceModelLogin {
    /**
     * isConnectDBSuccess : true
     * status : true
     * userdata : {"ideplsuser":"1","username":"jj@hotmail.com","password":"1234","gender-eplsuser":"M","phone-eplsuser":"089999999","emerphone-eplsuser":"085555555","lathome-eplsuser":null,"lnghome-eplsuser":null,"address-eplsuser":"124324","dob-eplsuser":"2016-10-19","firstname-eplsuser":"ชื่อ","lastname-eplsuser":"นามม","last-updated-time":"2016-11-20 16:31:28"}
     */

    private boolean isConnectDBSuccess;
    public boolean status;
    public List<String> errmsg;
    /**
     * ideplsuser : 1
     * username : jj@hotmail.com
     * password : 1234
     * gender-eplsuser : M
     * phone-eplsuser : 089999999
     * emerphone-eplsuser : 085555555
     * lathome-eplsuser : null
     * lnghome-eplsuser : null
     * address-eplsuser : 124324
     * dob-eplsuser : 2016-10-19
     * firstname-eplsuser : ชื่อ
     * lastname-eplsuser : นามม
     * last-updated-time : 2016-11-20 16:31:28
     */

    private UserdataBean userdata;

    public boolean isConnectDBSuccess() {
        return isConnectDBSuccess;
    }

    public void setConnectDBSuccess(boolean connectDBSuccess) {
        isConnectDBSuccess = connectDBSuccess;
    }

    public List<String> getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(List<String> errmsg) {
        this.errmsg = errmsg;
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


    public class UserdataBean {

        @Expose
        private String ideplsuser;
        @Expose
        private String username;
        @Expose
        private String password;
        @SerializedName("gender-eplsuser")
        @Expose
        private String genderEplsuser;
        @SerializedName("phone-eplsuser")
        @Expose
        private String phoneEplsuser;
        @SerializedName("emerphone-eplsuser")
        @Expose
        private String emerphoneEplsuser;
        @SerializedName("lathome-eplsuser")
        @Expose
        private String lathomeEplsuser;
        @SerializedName("lnghome-eplsuser")
        @Expose
        private String lnghomeEplsuser;
        @SerializedName("address-eplsuser")
        @Expose
        private String addressEplsuser;
        @SerializedName("dob-eplsuser")
        @Expose
        private String dobEplsuser;
        @SerializedName("firstname-eplsuser")
        @Expose
        private String firstnameEplsuser;
        @SerializedName("lastname-eplsuser")
        @Expose
        private String lastnameEplsuser;

        public String getIdeplsuser() {
            return ideplsuser;
        }

        public void setIdeplsuser(String ideplsuser) {
            this.ideplsuser = ideplsuser;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getGenderEplsuser() {
            return genderEplsuser;
        }

        public void setGenderEplsuser(String genderEplsuser) {
            this.genderEplsuser = genderEplsuser;
        }

        public String getPhoneEplsuser() {
            return phoneEplsuser;
        }

        public void setPhoneEplsuser(String phoneEplsuser) {
            this.phoneEplsuser = phoneEplsuser;
        }

        public String getEmerphoneEplsuser() {
            return emerphoneEplsuser;
        }

        public void setEmerphoneEplsuser(String emerphoneEplsuser) {
            this.emerphoneEplsuser = emerphoneEplsuser;
        }

        public String getLathomeEplsuser() {
            return lathomeEplsuser;
        }

        public void setLathomeEplsuser(String lathomeEplsuser) {
            this.lathomeEplsuser = lathomeEplsuser;
        }

        public String getLnghomeEplsuser() {
            return lnghomeEplsuser;
        }

        public void setLnghomeEplsuser(String lnghomeEplsuser) {
            this.lnghomeEplsuser = lnghomeEplsuser;
        }

        public String getAddressEplsuser() {
            return addressEplsuser;
        }

        public void setAddressEplsuser(String addressEplsuser) {
            this.addressEplsuser = addressEplsuser;
        }

        public String getDobEplsuser() {
            return dobEplsuser;
        }

        public void setDobEplsuser(String dobEplsuser) {
            this.dobEplsuser = dobEplsuser;
        }

        public String getFirstnameEplsuser() {
            return firstnameEplsuser;
        }

        public void setFirstnameEplsuser(String firstnameEplsuser) {
            this.firstnameEplsuser = firstnameEplsuser;
        }

        public String getLastnameEplsuser() {
            return lastnameEplsuser;
        }

        public void setLastnameEplsuser(String lastnameEplsuser) {
            this.lastnameEplsuser = lastnameEplsuser;
        }

//        public String toString() {
//            StringBuilder string = new StringBuilder().append("isConnectDBSuccess : " + isConnectDBSuccess + "\n")
//                    .append("status: " + status + "\n");
//
//            if (errmsg != null ) {
//                string.append(errmsg);
//            }
//            return string.toString();
//        }


    }


//    private boolean isSuccess;
//    private String isCorrect;
//
//    private DataBean data;
//
//    public boolean isIsSuccess() {
//        return isSuccess;
//    }
//
//    public void setIsSuccess(boolean isSuccess) {
//        this.isSuccess = isSuccess;
//    }
//
//    public String getIsCorrect() {
//        return isCorrect;
//    }
//
//    public void setIsCorrect(String isCorrect) {
//        this.isCorrect = isCorrect;
//    }
//
//    public DataBean getData() {
//        return data;
//    }
//
//    public void setData(DataBean data) {
//        this.data = data;
//    }
//
//    public static class DataBean {
//        @SerializedName("0")
//        private String value0;
//        @SerializedName("1")
//        private String value1;
//        @SerializedName("2")
//        private String value2;
//        @SerializedName("3")
//        private String value3;
//        @SerializedName("4")
//        private String value4;
//        @SerializedName("5")
//        private String value5;
//        @SerializedName("6")
//        private String value6;
//        @SerializedName("7")
//        private String value7;
//        @SerializedName("8")
//        private String value8;
//        @SerializedName("9")
//        private String value9;
//        private String uid;
//        private String email;
//        private String password;
//        private String firstname;
//        private String lastname;
//        private String birthday;
//        private String sex;
//        private String phone;
//        private String emerphone;
//        private String conpass;
//
//        public String getValue0() {
//            return value0;
//        }
//
//        public void setValue0(String value0) {
//            this.value0 = value0;
//        }
//
//        public String getValue1() {
//            return value1;
//        }
//
//        public void setValue1(String value1) {
//            this.value1 = value1;
//        }
//
//        public String getValue2() {
//            return value2;
//        }
//
//        public void setValue2(String value2) {
//            this.value2 = value2;
//        }
//
//        public String getValue3() {
//            return value3;
//        }
//
//        public void setValue3(String value3) {
//            this.value3 = value3;
//        }
//
//        public String getValue4() {
//            return value4;
//        }
//
//        public void setValue4(String value4) {
//            this.value4 = value4;
//        }
//
//        public String getValue5() {
//            return value5;
//        }
//
//        public void setValue5(String value5) {
//            this.value5 = value5;
//        }
//
//        public String getValue6() {
//            return value6;
//        }
//
//        public void setValue6(String value6) {
//            this.value6 = value6;
//        }
//
//        public String getValue7() {
//            return value7;
//        }
//
//        public void setValue7(String value7) {
//            this.value7 = value7;
//        }
//
//        public String getValue8() {
//            return value8;
//        }
//
//        public void setValue8(String value8) {
//            this.value8 = value8;
//        }
//
//        public String getValue9() {
//            return value9;
//        }
//
//        public void setValue9(String value9) {
//            this.value9 = value9;
//        }
//
//        public String getUid() {
//            return uid;
//        }
//
//        public void setUid(String uid) {
//            this.uid = uid;
//        }
//
//        public String getEmail() {
//            return email;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
//
//        public String getPassword() {
//            return password;
//        }
//
//        public void setPassword(String password) {
//            this.password = password;
//        }
//
//        public String getFirstname() {
//            return firstname;
//        }
//
//        public void setFirstname(String firstname) {
//            this.firstname = firstname;
//        }
//
//        public String getLastname() {
//            return lastname;
//        }
//
//        public void setLastname(String lastname) {
//            this.lastname = lastname;
//        }
//
//        public String getBirthday() {
//            return birthday;
//        }
//
//        public void setBirthday(String birthday) {
//            this.birthday = birthday;
//        }
//
//        public String getSex() {
//            return sex;
//        }
//
//        public void setSex(String sex) {
//            this.sex = sex;
//        }
//
//        public String getPhone() {
//            return phone;
//        }
//
//        public void setPhone(String phone) {
//            this.phone = phone;
//        }
//
//        public String getEmerphone() {
//            return emerphone;
//        }
//
//        public void setEmerphone(String emerphone) {
//            this.emerphone = emerphone;
//        }
//
//        public String getConpass() {
//            return conpass;
//        }
//
//        public void setConpass(String conpass) {
//            this.conpass = conpass;
//        }
//    }
//    SQLiteDatabase db;
//
//    public void onCreate() {
//        myDBClass myDb = new myDBClass(this);
//        myDb.getWritableDatabase();
}
