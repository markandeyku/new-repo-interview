package solid;

import java.util.Map;

public class IntefaceSegregation {
}


// It tells that interface should not have more properties in a single interface instead should be multiple intefaces with multiple properties

/**
  *💡 Benefits of ISP:
 * Decoupling: Easier to maintain and extend
 * Flexibility: Fewer breaking changes
 * Better abstraction: Clearer contracts per feature
 */
interface UserRepository{
    void createUser(String userNo);

    void updateUser(Map<String, String> userDetails);

    void deleteUser(String userNo);

    Map<String, String> fetchUserDetails(String userNo);

}

// It has all permissions

class AdminRepository implements UserRepository{

    @Override
    public void createUser(String userNo) {

    }

    @Override
    public void updateUser(Map<String, String> userDetails) {

    }

    @Override
    public void deleteUser(String userNo) {

    }

    @Override
    public Map<String, String> fetchUserDetails(String userNo) {
        return Map.of();
    }
}

//

class RegularUser implements UserRepository{

    @Override
    public void createUser(String userNo) {

    }

    @Override
    public void updateUser(Map<String, String> userDetails) {

    }

    @Override
    public void deleteUser(String userNo) {
        throw new UnsupportedOperationException("you cannot delete users");
    }

    @Override
    public Map<String, String> fetchUserDetails(String userNo) {
        return Map.of();
    }
}


//-- <<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>> --

interface ParkingLot{
    //chargecaluculation
    //park
    //unpark

}

class ChargeableParking implements ParkingLot{

}

class FreeParking implements ParkingLot{
    //chargecaluculation  -- unsupported operatrion
    // so try to
}



//***********************************************

interface NotificationService {
    void sendEmail(String msg);
    void sendSms(String msg);
    void sendPush(String msg);
}

// break into multiple interfaces

interface EmailSender {
    void sendEmail(String msg);
}

interface SmsSender {
    void sendSms(String msg);
}

interface PushSender {
    void sendPush(String msg);
}

// easy to maintain and imeplement

class SmsAlertService implements SmsSender {
    public void sendSms(String msg) {
        // send SMS
    }
}
