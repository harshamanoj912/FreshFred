package com.teamevox.freshfred;

import android.app.Application;

public class GlobalClass extends Application {

    private String loggedRiderNIC;

    public String getLoggedRiderNIC() {
        return loggedRiderNIC;
    }

    public void setLoggedRiderNIC(String loggedRiderNIC) {
        this.loggedRiderNIC = loggedRiderNIC;
    }
}
