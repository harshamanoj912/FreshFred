package com.teamevox.freshfred.IT19208718;

public class Rider {

    String riderName, riderMobile, riderBikeNumber, riderCommission, riderPassword, riderNic;

    public Rider(){

    }


    public Rider(String riderName2, String riderMobile2, String riderBikeNumber2, String riderCommission2, String riderPassword2, String riderNic2){
            this.riderName = riderName2;
            this.riderMobile = riderMobile2;
            this.riderBikeNumber = riderBikeNumber2;
            this.riderCommission = riderCommission2;
            this.riderPassword = riderPassword2;
            this.riderNic = riderNic2;
    }

    public String getRiderName() {
        return riderName;
    }

    public String getRiderMobile() {
        return riderMobile;
    }

    public String getRiderBikeNumber() {
        return riderBikeNumber;
    }

    public String getRiderCommission() {
        return riderCommission;
    }

    public String getRiderPassword() {
        return riderPassword;
    }

    public String getRiderNic() {
        return riderNic;
    }
}
