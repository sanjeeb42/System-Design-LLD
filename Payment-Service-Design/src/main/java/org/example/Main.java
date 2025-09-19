package org.example;

import java.util.Random;

class PaymentRequest{
    String sender;
    String receiver;
    double amount;
    String currency;

    public PaymentRequest(String sender, String receiver, double amount, String currency) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.currency = currency;
    }
}

/* ---------------------------------------------------------------------------------------- */

interface IBankingSystem{
    boolean processPayment(double amount);
}

class PaytmBankingSystem implements IBankingSystem{
    private Random rand=new Random();
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Payment Done via Paytm for amount: "+amount);
        int r = rand.nextInt(100);
        return r < 90;
    }
}

class RazorPayBankingSystem implements IBankingSystem{
    private Random rand=new Random();
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Payment Done via RazorPay for amount: "+amount);
        int r = rand.nextInt(100);
        return r < 80;
    }
}

/* ---------------------------------------------------------------------------------------- */

abstract class PaymentGateway{
    IBankingSystem bankingSystem;

    public PaymentGateway(){
        bankingSystem=null;
    }

    public boolean processPayment(PaymentRequest request){
        if (!validatePayment(request)) {
            System.out.println("[PaymentGateway] Validation failed for " + request.sender + ".");
            return false;
        }
        if (!initiatePayment(request)) {
            System.out.println("[PaymentGateway] Initiation failed for " + request.sender + ".");
            return false;
        }
        if (!confirmPayment(request)) {
            System.out.println("[PaymentGateway] Confirmation failed for " + request.sender + ".");
            return false;
        }
        return true;
    }

    protected abstract boolean validatePayment(PaymentRequest request);
    protected abstract boolean initiatePayment(PaymentRequest request);
    protected abstract boolean confirmPayment(PaymentRequest request);
}

class PaytmPaymentGateway extends PaymentGateway{

    public PaytmPaymentGateway(){
        this.bankingSystem=new PaytmBankingSystem();
    }

    @Override
    protected boolean validatePayment(PaymentRequest request) {
        System.out.println("[Paytm] Validating payment for " + request.sender + ".");
        if (request.amount <= 0 || !"INR".equals(request.currency)) {
            return false;
        }
        return true;
    }

    @Override
    protected boolean initiatePayment(PaymentRequest request) {
        System.out.println("[Paytm] Initiating payment of " + request.amount
                + " " + request.currency + " for " + request.sender + ".");
        return bankingSystem.processPayment(request.amount);
    }

    @Override
    protected boolean confirmPayment(PaymentRequest request) {
        System.out.println("[Paytm] Confirming payment for " + request.sender + ".");
        // Confirmation always succeeds in this simulation
        return true;
    }
}

class RazorPayPaymentGateway extends PaymentGateway{
    public RazorPayPaymentGateway(){
        this.bankingSystem=new RazorPayBankingSystem();
    }

    @Override
    protected boolean validatePayment(PaymentRequest request) {
        System.out.println("[RazorPay] Validating payment for " + request.sender + ".");
        if (request.amount <= 0 || !"INR".equals(request.currency)) {
            return false;
        }
        return true;
    }

    @Override
    protected boolean initiatePayment(PaymentRequest request) {
        System.out.println("[RazorPay] Initiating payment of " + request.amount
                + " " + request.currency + " for " + request.sender + ".");
        return bankingSystem.processPayment(request.amount);
    }

    @Override
    protected boolean confirmPayment(PaymentRequest request) {
        System.out.println("[RazorPay] Confirming payment for " + request.sender + ".");
        // Confirmation always succeeds in this simulation
        return true;
    }
}

/* ---------------------------------------------------------------------------------------- */
class PaymentGatewayProxy extends PaymentGateway{
    PaymentGateway paymentGateway;
    private int retries;

    PaymentGatewayProxy(PaymentGateway paymentGateway,int retries){
        this.paymentGateway=paymentGateway;
        this.retries=retries;
    }
    @Override
    public  boolean processPayment(PaymentRequest request){
        boolean result = false;
        for (int attempt = 0; attempt < retries; ++attempt) {
            if (attempt > 0) {
                System.out.println("[Proxy] Retrying payment (attempt " + (attempt+1)
                        + ") for " + request.sender + ".");
            }
            result = paymentGateway.processPayment(request);
            if (result) break;
        }
        if (!result) {
            System.out.println("[Proxy] Payment failed after " + retries
                    + " attempts for " + request.sender + ".");
        }
        return result;
    }


    @Override
    protected boolean validatePayment(PaymentRequest request) {
        return paymentGateway.validatePayment(request);
    }

    @Override
    protected boolean initiatePayment(PaymentRequest request) {
        return paymentGateway.initiatePayment(request);
    }

    @Override
    protected boolean confirmPayment(PaymentRequest request) {
       return paymentGateway.confirmPayment(request);
    }
}


/* ---------------------------------------------------------------------------------------- */


enum GATEWAYTYPE{
    PAYTM,
    RAZORPAY
};

class GatewayFactory{
    private static final GatewayFactory gatewayFactory=new GatewayFactory();

    public static GatewayFactory getInstance(){
        return gatewayFactory;
    }

    public PaymentGateway getGateway(GATEWAYTYPE type){
        if(type == GATEWAYTYPE.PAYTM){
            PaymentGateway paymentGateway=new PaytmPaymentGateway();
            return new PaymentGatewayProxy(paymentGateway,5);
        }
        else if(type == GATEWAYTYPE.RAZORPAY){
            PaymentGateway paymentGateway=new RazorPayPaymentGateway();
            return new PaymentGatewayProxy(paymentGateway,3);
        }
        return null;
    }
}

/* ---------------------------------------------------------------------------------------- */

class PaymentService{
    private static final PaymentService instance=new PaymentService();
    PaymentGateway gateway;

    private PaymentService() {
        this.gateway = null;
    }

    public static PaymentService getInstance() {
        return instance;
    }

    public void setGateway(PaymentGateway g) {
        this.gateway = g;
    }

    public boolean processPayment(PaymentRequest request) {
        if (gateway == null) {
            System.out.println("[PaymentService] No payment gateway selected.");
            return false;
        }
        return gateway.processPayment(request);
    }
}

/* ---------------------------------------------------------------------------------------- */

class PaymentController {
    private static final PaymentController instance = new PaymentController();

    private PaymentController() {}

    public static PaymentController getInstance() {
        return instance;
    }

    public boolean handlePayment(GATEWAYTYPE type, PaymentRequest req) {
        PaymentGateway paymentGateway = GatewayFactory.getInstance().getGateway(type);
        PaymentService.getInstance().setGateway(paymentGateway);
        return PaymentService.getInstance().processPayment(req);
    }
}

/* ---------------------------------------------------------------------------------------- */


public class Main {
    public static void main(String[] args) {
        PaymentRequest req1 = new PaymentRequest("Aditya", "Shubham", 1000.0, "INR");

        System.out.println("Processing via Paytm");
        System.out.println("------------------------------");
        boolean res1 = PaymentController.getInstance().handlePayment(GATEWAYTYPE.PAYTM, req1);
        System.out.println("Result: " + (res1 ? "SUCCESS" : "FAIL"));
        System.out.println("------------------------------\n");

        PaymentRequest req2 = new PaymentRequest("Shubham", "Aditya", 500.0, "USD");

        System.out.println("Processing via Razorpay");
        System.out.println("------------------------------");
        boolean res2 = PaymentController.getInstance().handlePayment(GATEWAYTYPE.RAZORPAY, req2);
        System.out.println("Result: " + (res2 ? "SUCCESS" : "FAIL"));
        System.out.println("------------------------------");
    }
}