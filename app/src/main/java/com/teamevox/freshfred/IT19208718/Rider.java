package com.teamevox.freshfred.IT19208718;

public class Rider {

    String riderName, riderMobile, riderBikeNumber, riderCommission, riderPassword, riderNic;

    public Rider(){

    }

    public Rider(String riderName, String riderMobile, String riderBikeNumber, String riderCommission, String riderPassword, String riderNic){
            this.riderName = riderName;
            this.riderMobile = riderMobile;
            this.riderBikeNumber = riderBikeNumber;
            this.riderCommission = riderCommission;
            this.riderPassword = riderPassword;
            this.riderNic = riderNic;
    }

    @Override
    public String toString() {
        return "Rider{" +
                "riderName='" + riderName + '\'' +
                ", riderMobile='" + riderMobile + '\'' +
                ", riderBikeNumber='" + riderBikeNumber + '\'' +
                ", riderCommission='" + riderCommission + '\'' +
                ", riderPassword='" + riderPassword + '\'' +
                ", riderNic='" + riderNic + '\'' +
                '}';
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public String getRiderMobile() {
        return riderMobile;
    }

    public void setRiderMobile(String riderMobile) {
        this.riderMobile = riderMobile;
    }

    public String getRiderBikeNumber() {
        return riderBikeNumber;
    }

    public void setRiderBikeNumber(String riderBikeNumber) {
        this.riderBikeNumber = riderBikeNumber;
    }

    public String getRiderCommission() {
        return riderCommission;
    }

    public void setRiderCommission(String riderCommission) {
        this.riderCommission = riderCommission;
    }

    public String getRiderPassword() {
        return riderPassword;
    }

    public void setRiderPassword(String riderPassword) {
        this.riderPassword = riderPassword;
    }

    public String getRiderNic() {
        return riderNic;
    }

    public void setRiderNic(String riderNic) {
        this.riderNic = riderNic;
    }
}
