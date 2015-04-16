package parallel_programming.bank;

public class BankOperation {
    public static void main(String[] args) {
        Bank DeutscheBank = new Bank("DeutscheBank");
        BankEmplojee eve = new BankEmplojee("Eve", DeutscheBank);
        BankEmplojee hanna = new BankEmplojee("Hanna", DeutscheBank);

        System.out.println((DeutscheBank.getAccounts()[1]).get());

        //page 14 . JavaThreads.pdf
    }
}