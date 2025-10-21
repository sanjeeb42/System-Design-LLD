import java.util.Random;

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

    private Random rand=new Random();
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Paying using Paytm");
        int r=rand.nextInt(100);
        return r<=90;
    }
};

class GpayBankingSystem implements BankingSystem {
    private Random rand=new Random();
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Paying using Gpay");
        int r=rand.nextInt(100);
        return r<=80;
    }
};

abstract class PaymentGateway{

    abstract boolean initiatePayment(PaymentRequest paymentRequest);
    abstract boolean validatePayment(PaymentRequest paymentRequest);
    abstract boolean confirmPayment(PaymentRequest paymentRequest);

    public boolean processPayment(PaymentRequest paymentRequest){

        if(!initiatePayment(paymentRequest)){
            System.out.println("Initiation Failed");
            return false;
        }
        if(!validatePayment(paymentRequest)){
            System.out.println("Validation Failed");
            return false;
        }
        if(!confirmPayment(paymentRequest)){
            System.out.println("Acknowledgement Failed");
            return false;
        }
        return true;
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
        return bankingSystem.processPayment(paymentRequest.amount);
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
        return bankingSystem.processPayment(paymentRequest.amount);
    }

    @Override
    boolean confirmPayment(PaymentRequest paymentRequest) {
        System.out.println(paymentRequest.amount +" deducted from "+paymentRequest.sender);
        return true;
    }
}

enum GatewayType{
    PAYTM,
    GPAY
};

class GatewayFactory{
    public static PaymentGateway instance=null;

    public static PaymentGateway getInstance(GatewayType gatewayType){
        if(gatewayType.equals(GatewayType.PAYTM))instance=new PaytmPaymentGateway();
        else if (gatewayType.equals(GatewayType.GPAY))instance= new GpayPaymentGateway();
        return instance;
    }
}

class PaymentGatewayProxy extends PaymentGateway {

    // Single Responsibility hoga iska - Retry ka sirf

    PaymentGateway realGateway;
    int retriesCount;

    PaymentGatewayProxy(PaymentGateway paymentGateway,int retriesCount){
        this.realGateway=paymentGateway;
        this.retriesCount=retriesCount;
    }

    @Override
    boolean initiatePayment(PaymentRequest paymentRequest) {
        return realGateway.initiatePayment(paymentRequest);
    }

    @Override
    boolean validatePayment(PaymentRequest paymentRequest) {
        return realGateway.validatePayment(paymentRequest);
    }

    @Override
    boolean confirmPayment(PaymentRequest paymentRequest) {
        return realGateway.confirmPayment(paymentRequest);
    }


    public boolean processPayment(PaymentRequest paymentRequest){
        for(int i=0;i<retriesCount;i++){
            if(!realGateway.processPayment(paymentRequest)){
                System.out.println("Retrying Payment Again");
                continue;
            }
            else {
                System.out.println("Payment Successful and amount credited to "+paymentRequest.receiver);
                return true;
            }
        }
        System.out.println("Max Retries Exceeded , Payment failed");
        return false;
    }

}

public class PaymentGatewayApplication {
    public static void main(String[] args) {
        PaymentRequest paymentRequest=new PaymentRequest("Sanjeeb","Pranav",2000,"USD");
        String payUsing= "Gpay";

        PaymentGateway paymentGateway=GatewayFactory.getInstance(GatewayType.GPAY);
        PaymentGatewayProxy paymentGatewaywithRetries=new PaymentGatewayProxy(paymentGateway,3);
        paymentGatewaywithRetries.processPayment(paymentRequest);
    }
}