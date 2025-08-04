package test.designpattern.practice.factory;

import java.util.Map;
import java.util.Objects;

public interface Bank {

    public void openAccount();

    public void closeAccount();

    public Map<String, String> getAccountDetails();

    public void setAccountDetails(Map<String, String> accountDetails);

}
