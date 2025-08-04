package solid;

import java.util.Map;

public class OpenForExtensionClosedForModification {
}


abstract class PaymentService{
    abstract void  processPayment(Map<String, Object> paymentDetails);
}

class PsuBankPaymentService extends PaymentService{

    @Override
    void processPayment(Map<String, Object> paymentDetails) {
        //implement logs
    }
}

class PrivateBankPaymentService extends PaymentService{

    @Override
    void processPayment(Map<String, Object> paymentDetails) {
        //implement logs
    }
}

// any other bank can extends and implements its own logic