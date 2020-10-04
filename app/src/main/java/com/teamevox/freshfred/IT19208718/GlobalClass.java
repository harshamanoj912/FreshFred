package com.teamevox.freshfred.IT19208718;

import android.app.Application;

public class GlobalClass extends Application {

    private String loggedRiderUsername, loggedOwnerUsername, loggedSupplierUsername, loggedCustomerUsername;
    private String resetPasswordUserType;
    private String resetPasswordNIC, resetPasswordMobile;
    private String newRiderCommission;

    public String getNewRiderCommission() {
        return newRiderCommission;
    }

    public void setNewRiderCommission(String newRiderCommission) {
        this.newRiderCommission = newRiderCommission;
    }

    public String getLoggedRiderUsername() {
        return loggedRiderUsername;
    }

    public void setLoggedRiderUsername(String loggedRiderUsername) {
        this.loggedRiderUsername = loggedRiderUsername;
    }

    public String getGetLoggedOwnerUsername() {
        return loggedOwnerUsername;
    }

    public void setGetLoggedOwnerUsername(String getLoggedOwnerUsername) {
        this.loggedOwnerUsername = getLoggedOwnerUsername;
    }

    public String getGetLoggedSupplierUsername() {
        return loggedSupplierUsername;
    }

    public void setGetLoggedSupplierUsername(String getLoggedSupplierUsername) {
        this.loggedSupplierUsername = getLoggedSupplierUsername;
    }

    public String getGetLoggedCustomerUsername() {
        return loggedCustomerUsername;
    }

    public void setGetLoggedCustomerUsername(String getLoggedCustomerUsername) {
        this.loggedCustomerUsername = getLoggedCustomerUsername;
    }

    public String getResetPasswordNIC() {
        return resetPasswordNIC;
    }

    public void setResetPasswordNIC(String resetPasswordNIC) {
        this.resetPasswordNIC = resetPasswordNIC;
    }

    public String getResetPasswordMobile() {
        return resetPasswordMobile;
    }

    public void setResetPasswordMobile(String resetPasswordMobile) {
        this.resetPasswordMobile = resetPasswordMobile;
    }

    public String getResetPasswordUserType() {
        return resetPasswordUserType;
    }

    public void setResetPasswordUserType(String resetPasswordUserType) {
        this.resetPasswordUserType = resetPasswordUserType;
    }
}
