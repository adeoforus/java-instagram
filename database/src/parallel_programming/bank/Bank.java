package parallel_programming.bank;

class Bank {
    private Account[] accounts;
    String bankName;
    int count=0;
    private boolean lock;

    public Bank(String bankName) {
        this.bankName = bankName;
        accounts = new Account[100];
        for(int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account();
        }
        lock = false; // initialize to “not locked”
    }

    public void booking(String employee,
                        int accountnr, float amount) {

        synchronized ( accounts[accountnr] ) {
            // Only one account is locked!
            float oldBalance = accounts[accountnr].get();
            float newBalance = oldBalance + amount;
            accounts[accountnr].set(newBalance);

            System.out.println(count + " " + accountnr + " " + newBalance + " " + employee);
        }

    }


    public Account[] getAccounts(){
        return accounts;
    }


}