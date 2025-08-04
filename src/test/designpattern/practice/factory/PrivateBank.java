package test.designpattern.practice.factory;

import java.util.Map;

public class PrivateBank implements Bank {
    @Override
    public void openAccount() {

    }

    @Override
    public void closeAccount() {

    }

    @Override
    public Map<String, String> getAccountDetails() {
        return Map.of();
    }

    @Override
    public void setAccountDetails(Map<String, String> accountDetails) {

    }
}
