package strategies;

public class NetBankingPaymentStrategy implements PaymentStrategy {
    private String bankName;
    private String accountHolderName;

    public NetBankingPaymentStrategy(String bankName, String accountHolderName) {
        this.bankName = bankName;
        this.accountHolderName = accountHolderName;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " using Net Banking (" + bankName + " - " + accountHolderName + ")");
    }

    public String getBankName() {
        return bankName;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }
}