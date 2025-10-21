
class PaymentRequest{
    public String sender;
    public String receiver;
    public double amount;
    public String currency;

    public PaymentRequest(String sender, String receiver, double amount, String currency) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.currency = currency;
    }
};

interface BankingSystem{
    boolean processPayment(double amount);
};

class PaytmBankingSystem implements BankingSystem {

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Paying using Paytm");
        return true;
    }
};

class GpayBankingSystem implements BankingSystem {

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Paying using Gpay");
        return true;
    }
};

abstract class PaymentGateway{

    abstract boolean initiatePayment(PaymentRequest paymentRequest);
    abstract boolean validatePayment(PaymentRequest paymentRequest);
    abstract boolean confirmPayment(PaymentRequest paymentRequest);

    public boolean processPayment(PaymentRequest paymentRequest){
        boolean result=true;
        if(paymentRequest!=null){
            result=initiatePayment(paymentRequest);
        }
        if(result)result=validatePayment(paymentRequest);
        if(result)result=confirmPayment(paymentRequest);
        return result;
    }
}

class PaytmPaymentGateway extends PaymentGateway {
    BankingSystem bankingSystem;
    PaytmPaymentGateway() {
        bankingSystem=new PaytmBankingSystem();
    }

    @Override
    boolean initiatePayment(PaymentRequest paymentRequest) {
        System.out.println("Initiating payment of "+paymentRequest.amount+" using Paytm");
        if(paymentRequest.amount<=0)return false;
        return true;
    }

    @Override
    boolean validatePayment(PaymentRequest paymentRequest) {
        System.out.println(paymentRequest.sender+" attempting to send "+paymentRequest.amount);
        return true;
    }

    @Override
    boolean confirmPayment(PaymentRequest paymentRequest) {
        System.out.println(paymentRequest.amount +" deducted from "+paymentRequest.sender);
        return true;
    }
}

class GpayPaymentGateway extends PaymentGateway{
    BankingSystem bankingSystem;
    GpayPaymentGateway() {
        bankingSystem=new GpayBankingSystem();
    }

    @Override
    boolean initiatePayment(PaymentRequest paymentRequest) {
        System.out.println("Initiating payment of "+paymentRequest.amount+" using Gpay");
        return true;
    }

    @Override
    boolean validatePayment(PaymentRequest paymentRequest) {
        System.out.println(paymentRequest.sender+" attempting to send "+paymentRequest.amount);
        return true;
    }

    @Override
    boolean confirmPayment(PaymentRequest paymentRequest) {
        System.out.println(paymentRequest.amount +" deducted from "+paymentRequest.sender);
        return true;
    }
}

class GatewayFactory{

    public static PaymentGateway getInstance(String gateway){
        if(gateway=="Paytm")return new PaytmPaymentGateway();
        return new GpayPaymentGateway();
    }
}

public class PaymentGatewayApplication {
    public static void main(String[] args) {
        PaymentRequest paymentRequest=new PaymentRequest("Sanjeeb","Pranav",2000,"USD");
        String payUsing= "Gpay";

        PaymentGateway paymentGateway=GatewayFactory.getInstance(payUsing);
        paymentGateway.processPayment(paymentRequest);
    }
}