package parallel_programming.bank;

class BankEmplojee extends Thread {
    private Bank bank;
    public String name;

    public BankEmplojee(String name, Bank bank) {
        super(name);
        this.bank = bank;
        this.name = name;
        start(); // thread started in the constructor
    }

    public void run() {
        for(int i = 0; i < 1000; i++) {
            int accountnr = 1; // better random number
            float amount = 1; // better random number
            bank.booking(name, accountnr, amount);
        }
    }
}