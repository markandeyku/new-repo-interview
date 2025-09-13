package lld;


import java.util.UUID;

// Payment Status
enum PaymentStatus {
    SUCCESS, FAILED, PENDING, REFUNDED
}

// Supported Payment Modes
enum PaymentMode {
    CREDIT_CARD, DEBIT_CARD, UPI, NET_BANKING, CASH
}

// Generic Payment Request
class PaymentRequest {
    private final String userId;
    private final double amount;
    private final PaymentMode mode;

    public PaymentRequest(String userId, double amount, PaymentMode mode) {
        this.userId = userId;
        this.amount = amount;
        this.mode = mode;
    }

    public String getUserId() { return userId; }
    public double getAmount() { return amount; }
    public PaymentMode getMode() { return mode; }
}

// Generic Payment Response
class PaymentResponse {
    private final String transactionId;
    private final double amount;
    private final PaymentStatus status;
    private final String message;

    private final PaymentMode mode;

    public PaymentResponse(String transactionId, double amount, PaymentStatus status, String message, PaymentMode mode) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.status = status;
        this.message = message;
        this.mode = mode;
    }

    public String getTransactionId() { return transactionId; }
    public PaymentStatus getStatus() { return status; }
    public String getMessage() { return message; }

    public double getAmount() {
        return amount;
    }

    public PaymentMode getMode() {
        return mode;
    }
}

// Payment Strategy
interface PaymentProcessor {
    PaymentResponse process(PaymentRequest request);
}

// Credit Card Payment
class CreditCardPaymentProcessor implements PaymentProcessor {
    @Override
    public PaymentResponse process(PaymentRequest request) {
        // Simulate calling credit card gateway API
        return new PaymentResponse(UUID.randomUUID().toString(), request.getAmount(),
                PaymentStatus.SUCCESS,
                "Credit Card payment successful", request.getMode());
    }
}

// UPI Payment
class UpiPaymentProcessor implements PaymentProcessor {
    @Override
    public PaymentResponse process(PaymentRequest request) {
        return new PaymentResponse(UUID.randomUUID().toString(),request.getAmount(),
                PaymentStatus.SUCCESS,
                "UPI payment successful", request.getMode());
    }
}

// Cash Payment
class CashPaymentProcessor implements PaymentProcessor {
    @Override
    public PaymentResponse process(PaymentRequest request) {
        return new PaymentResponse(UUID.randomUUID().toString(),request.getAmount(),
                PaymentStatus.SUCCESS,
                "Cash payment recorded", request.getMode());
    }
}

// Factory Pattern
class PaymentProcessorFactory {
    public static PaymentProcessor getProcessor(PaymentMode mode) {
        switch (mode) {
            case CREDIT_CARD: return new CreditCardPaymentProcessor();
            case UPI: return new UpiPaymentProcessor();
            case CASH: return new CashPaymentProcessor();
            default: throw new IllegalArgumentException("Unsupported payment mode: " + mode);
        }
    }
}

class RefundResponse{

    private String transactionId;
    private double amount;
    private PaymentStatus paymentStatus;
    private String message;

    public RefundResponse(String transactionId, double amount, PaymentStatus paymentStatus, String message) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.message = message;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public String getMessage() {
        return message;
    }
}
// Generic Service
public class PaymentManagementService {

    public PaymentResponse pay(PaymentRequest request) {
        PaymentProcessor processor = PaymentProcessorFactory.getProcessor(request.getMode());
        return processor.process(request);
    }

    public RefundResponse refund(String transactionId, double amount) {
        // Can add refund logic here (e.g., call gateway API)
        return new RefundResponse(transactionId,amount, PaymentStatus.REFUNDED, "Refund successful");
    }

    // Example usage
    public static void main(String[] args) {
        PaymentManagementService service = new PaymentManagementService();

        PaymentRequest request1 = new PaymentRequest("U1", 500.0, PaymentMode.CREDIT_CARD);
        PaymentResponse response1 = service.pay(request1);
        System.out.println("TxnId: " + response1.getTransactionId() +
                " Status: " + response1.getStatus() + " Msg: " + response1.getMessage());

        PaymentRequest request2 = new PaymentRequest("U2", 200.0, PaymentMode.CASH);
        PaymentResponse response2 = service.pay(request2);
        System.out.println("TxnId: " + response2.getTransactionId() +
                " Status: " + response2.getStatus() + " Msg: " + response2.getMessage());

        RefundResponse refundResponse = service.refund(response1.getTransactionId(), 500.0);
        System.out.println("Refund TxnId: " + refundResponse.getTransactionId() +
                " Status: " + refundResponse.getPaymentStatus());
    }
}
