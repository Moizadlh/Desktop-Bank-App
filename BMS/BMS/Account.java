package BMS;

import java.io.Serializable;

public class Account implements Serializable {

    private static final long serialVersionUID = -8854516871072743693L;
    private String number;
    private float amount;
    private Client accountHolder;
    private static int count = 0;

    public Account(float amount, Client accountHolder) {
        this.number = "A" + (++count);
        this.amount = amount;
        this.accountHolder = accountHolder;
        accountHolder.addAccount(this);
    }

    public static void setCount(int count) {
        Account.count = count;
    }
    
    public static int getCount() {
        return count;
    }

    public String getNumber() {
        return number;
    }
    

    public Client getAccountHolder() {
        return accountHolder;
    }

    public float getAmount() {
        return amount;
    }

    public int GetIdofclient(){
        return accountHolder.getId();
    }

    public String GetNameofclient(){
        return accountHolder.Person_Name();
    }

    public String GetCnicofclient(){
        return accountHolder.Person_CNIC();
    }


    void deposit(float dept){
        amount += dept;
    }
    void withdraw(float dept){
        amount -= dept;
    }

    @Override
    public String toString() {
        return "Account "+ "ID==" +number +" [" + "amount=" + amount + " , accountHolderID=" + accountHolder.getId() + "]";
    }    

}
