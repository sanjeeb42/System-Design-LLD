import java.util.Random;

class PaymentRequests{
    public String sender;
    public String receiver;
    public double amount;
    public String currency;

    public PaymentRequests(String sender, String receiver, double amount, String currency) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.currency = currency;
    }
};

interface BankingSystems {
    boolean processPayment(double amount);
};

class PaytmBankingSystems implements BankingSystems {

    private Random rand=new Random();
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Paying using Paytm");
        int r=rand.nextInt(100);
        return r<=90;
    }
};

class GpayBankingSystems implements BankingSystems {
    private Random rand=new Random();
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Paying using Gpay");
        int r=rand.nextInt(100);
        return r<=80;
    }
};

abstract class PaymentGateways {

    abstract boolean initiatePayment(PaymentRequests paymentRequest);
    abstract boolean validatePayment(PaymentRequests paymentRequest);
    abstract boolean confirmPayment(PaymentRequests paymeZntRequest);

    public boolean processPayment(PaymentRequests paymentRequest){

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

class PaytmPaymentGateways extends PaymentGateways {
    BankingSystems bankingSystems;
    PaytmPaymentGateways() {
        bankingSystems =new PaytmBankingSystems();
    }

    @Override
    boolean initiatePayment(PaymentRequests paymentRequest) {
        System.out.println("Initiating payment of "+paymentRequest.amount+" using Paytm");
        if(paymentRequest.amount<=0)return false;
        return true;
    }

    @Override
    boolean validatePayment(PaymentRequests paymentRequest) {
        System.out.println(paymentRequest.sender+" attempting to send "+paymentRequest.amount);
        return bankingSystems.processPayment(paymentRequest.amount);
    }

    @Override
    boolean confirmPayment(PaymentRequests paymentRequest) {
        System.out.println(paymentRequest.amount +" deducted from "+paymentRequest.sender);
        return true;
    }
}

class GpayPaymentGateways extends PaymentGateways {
    BankingSystems bankingSystems;
    GpayPaymentGateways() {
        bankingSystems =new GpayBankingSystems();
    }

    @Override
    boolean initiatePayment(PaymentRequests paymentRequest) {
        System.out.println("Initiating payment of "+paymentRequest.amount+" using Gpay");
        return true;
    }

    @Override
    boolean validatePayment(PaymentRequests paymentRequest) {
        System.out.println(paymentRequest.sender+" attempting to send "+paymentRequest.amount);
        return bankingSystems.processPayment(paymentRequest.amount);
    }

    @Override
    boolean confirmPayment(PaymentRequests paymentRequest) {
        System.out.println(paymentRequest.amount +" deducted from "+paymentRequest.sender);
        return true;
    }
}

enum GatewayTypes {
    PAYTM,
    GPAY,
    PHONEPE,
    RAZORPAY
};

class GatewayFactorys{
    public static PaymentGateways instance=null;

    public static PaymentGateways getInstance(GatewayTypes gatewayTypes){
        if(gatewayTypes.equals(GatewayTypes.PAYTM))instance=new PaytmPaymentGateways();
        else if (gatewayTypes.equals(GatewayTypes.GPAY))instance= new GpayPaymentGateways();
        return instance;
    }
}

interface RetryStrategy{
    public boolean retry(PaymentRequests paymentRequest,PaymentGateways gateway,int retryCount);
};

class LinearRetry implements RetryStrategy{
    int waitTime=5;

    @Override
    public boolean retry(PaymentRequests paymentRequest, PaymentGateways gateway,int retryCount) {
        System.out.println("Using Linear Retry Strategy");
        for(int i=0;i<=retryCount;i++){
            if(!gateway.processPayment(paymentRequest)){
                if(i==retryCount)return false;
                System.out.println("Payment Failed , Retrying in next "+waitTime+" ms");
                try{
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    System.out.println("Some Error happened with System, Please try Later");
                    return false;
                }
            }
            else {
                System.out.println("Payment SuccessFull");
                return true;
            }
        }
        return false;
    }
}

class ExponentialRetry implements RetryStrategy{
    int retryAttempt =0;


    @Override
    public boolean retry(PaymentRequests paymentRequest, PaymentGateways gateway,int retryCount) {
        System.out.println("Using Exponential Retry Strategy");
        for(int i=0;i<=retryCount;i++){
            long waitTime=(long) Math.pow(2, retryAttempt) * 1000L;
            if(!gateway.processPayment(paymentRequest)){
                if(i==retryCount)return false;
                System.out.println("Payment Failed , Retrying in next "+waitTime+ " ms");
                try{
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    System.out.println("Some Error happened with System, Please try Later");
                    return false;
                }
                retryAttempt++;
            }
            else {
                System.out.println("Payment SuccessFull");
                return true;
            }
        }
        return false;
    }
}

class ExponentialRetrywithJitter implements RetryStrategy{
    int retryAttempt =0;


    @Override
    public boolean retry(PaymentRequests paymentRequest, PaymentGateways gateway,int retryCount) {
        System.out.println("Using Exponential Retry Strategy with Jitter");
        for(int i=0;i<=retryCount;i++){
            Random random = new Random();
            long baseDelay=(long) Math.pow(2, retryAttempt) * 1000L;
            long jitter = random.nextInt((int) baseDelay);
            long waitTime=baseDelay+jitter;
            if(!gateway.processPayment(paymentRequest)){
                if(i==retryCount)return false;
                System.out.println("Payment Failed , Retrying in next "+waitTime+ " ms");
                try{
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    System.out.println("Some Error happened with System, Please try Later");
                    return false;
                }
                retryAttempt++;
            }
            else {
                System.out.println("Payment SuccessFull");
                return true;
            }
        }
        return false;
    }
}

class IncrementalRetry implements RetryStrategy{

    int waitTime=0;

    @Override
    public boolean retry(PaymentRequests paymentRequest, PaymentGateways gateway,int retryCount) {
        System.out.println("Using Incremental Retry Strategy");
        for(int i=0;i<=retryCount;i++){
            if(!gateway.processPayment(paymentRequest)){
                if(i==retryCount)return false;
                System.out.println("Payment Failed , Retrying in next "+waitTime+" ms");
                try{
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    System.out.println("Some Error happened with System, Please try Later");
                    return false;
                }
                waitTime+=5; // Incrementing 5ms wait Time on everyFailure
                // Delay sequence hoga - 0ms, 5ms, 10ms, 15ms
                /*
                    Difference between Linear vs Incremental Retry

                    | Mechanism                | Delay Pattern                  | Example         | Growth Type   |
                    | ------------------------ | ------------------------------ | --------------- | ------------- |
                    | **Fixed (Linear) Retry** | Same delay each time           | 5s, 5s, 5s…     | Constant      |
                    | **Incremental Backoff**  | Adds a fixed amount each retry | 1s, 3s, 5s, 7s… | Linear growth |

                 */
            }
            else {
                System.out.println("Payment SuccessFull");
                return true;
            }
        }
        return false;
    }
}

class DynamicRetry implements RetryStrategy{

    @Override
    public boolean retry(PaymentRequests paymentRequest, PaymentGateways gateway, int retryCount) {
        // Yeha it depends on various conditions
        /*
            | Feedback Source                 | Example Behavior                                                                                                                     |
            | ------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------ |
            | **HTTP Status Codes**           | `429 Too Many Requests` → You’re hitting rate limits. The API may include a `Retry-After: 10` header → wait 10 seconds before retry. |
            | **Response Time (Latency)**     | If the API is responding slower than usual → increase delay before next retry to reduce load.                                        |
            | **Error Rate or Failure Trend** | If recent retries keep failing → increase the interval. If recent ones succeed → shorten the interval again.                         |
            | **Server Load Metrics**         | In microservices, you might use internal metrics (CPU, queue size) to slow retries if the target service is overloaded.              |

         */

        System.out.println("Using Linear Retry Strategy");
        for(int i=0;i<=retryCount;i++){
            Random rand=new Random();
            if(!gateway.processPayment(paymentRequest)){
                if(i==retryCount)return false;
                long dynamicWaitTime= rand.nextLong(2000);
                System.out.println("Payment Failed , Retrying in next "+dynamicWaitTime+" ms");
                try{
                    Thread.sleep(dynamicWaitTime);
                } catch (InterruptedException e) {
                    System.out.println("Some Error happened with System, Please try Later");
                    return false;
                }
            }
            else {
                System.out.println("Payment SuccessFull");
                return true;
            }
        }
        return false;
    }
}

class PaymentGatewaysProxy extends PaymentGateways {

    // Single Responsibility hoga iska - Retry ka sirf

    PaymentGateways realGateway;
    int retriesCount;
    RetryStrategy retryStrategy;

    PaymentGatewaysProxy(PaymentGateways paymentGateways, int retriesCount,RetryStrategy retryStrategy){
        this.realGateway= paymentGateways;
        this.retriesCount=retriesCount;
        this.retryStrategy=retryStrategy;
    }

    @Override
    boolean initiatePayment(PaymentRequests paymentRequest) {
        return realGateway.initiatePayment(paymentRequest);
    }

    @Override
    boolean validatePayment(PaymentRequests paymentRequest) {
        return realGateway.validatePayment(paymentRequest);
    }

    @Override
    boolean confirmPayment(PaymentRequests paymentRequest) {
        return realGateway.confirmPayment(paymentRequest);
    }

    public boolean processPayment(PaymentRequests paymentRequest){
        return retryStrategy.retry(paymentRequest,realGateway,retriesCount);
    }

}

public class PaymentGatewayApplicationWithRetryStrategyAssignment {
    public static void main(String[] args) {
        PaymentRequests paymentRequest=new PaymentRequests("Sanjeeb","Pranav",2000,"USD");
        PaymentGateways paymentGateways = GatewayFactorys.getInstance(GatewayTypes.GPAY);
        RetryStrategy retryStrategy=new ExponentialRetrywithJitter();
        PaymentGatewaysProxy paymentGatewaywithRetries=new PaymentGatewaysProxy(paymentGateways,3,retryStrategy);
        paymentGatewaywithRetries.processPayment(paymentRequest);
    }
}