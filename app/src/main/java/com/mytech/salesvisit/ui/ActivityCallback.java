package com.mytech.salesvisit.ui;

import com.mytech.salesvisit.db.User;
import com.mytech.salesvisit.net.GeoLocation;

public interface ActivityCallback {
    ActivityCallback nullActivityCallback=new NullActivityCallback();

    User storeUser(User userResponse);
    User getCurrentUser();
    void updateHeader();
    void visitInProgress(boolean inProgress);
    void moveToHome();
    GeoLocation getLocation();
    void checkIn(Integer custId);
    void moveToLogin();
    void moveToCheckout();


    class NullActivityCallback implements ActivityCallback{

        @Override
        public User storeUser(User userResponse) {
            return null;
        }

        @Override
        public User getCurrentUser() {
            return null;
        }

        @Override
        public void updateHeader() {

        }

        @Override
        public void visitInProgress(boolean inProgress) {

        }

        @Override
        public void moveToHome() {

        }

        @Override
        public GeoLocation getLocation() {
            return null;
        }

        @Override
        public void checkIn(Integer custId) {

        }

        @Override
        public void moveToLogin() {

        }

        @Override
        public void moveToCheckout() {

        }
    }


}
