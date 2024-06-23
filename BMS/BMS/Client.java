package BMS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Client implements Serializable 
{
    private static final long serialVersionUID = -7257838172658919243L;
    private int id;
    private Person personDetails;
    private List<Account> accounts;
    public static int count = 0;

    public Client(Person personDetails){
        this.id = ++count;
        this.personDetails = personDetails;
        this.accounts = new ArrayList<>();
    }
    
    public Person getPersonDetails() {
        return personDetails;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public String Person_Name() {
        return personDetails.getName();
    }

    public String Person_CNIC() {
        return personDetails.getCNIC();
    }
    
    public String Person_Phone() {
        return personDetails.getPhone();
    }

    public float totalAmount() {
        float total = 0;
        for (Account account : accounts) {
            total += account.getAmount();
        }
        return total;
    }

    void addAccount(Account a){
        accounts.add(a);
    }
    

    public int getId() {
        return id;
    }

    void withdraw(float amount,String accNo){
        for (Account account : accounts) {
            if (account.getNumber().equals(accNo)) {
                account.withdraw(amount);
                System.out.println("Account number=" +accNo +"current amount =" + account.getAmount());
                return;
            }
        }
        System.out.println("Not found Acc");
    } 

    void deposit(float amount,String accNo){
        for (Account account : accounts) {
            if (account.getNumber().equals(accNo)) {
                account.deposit(amount);
                System.out.println("Account number=" +accNo +"current amount =" + account.getAmount());
                return;
            }
        }
        System.out.println("Not found Acc");
    } 

    
    @Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\n=============================\nClient ID: ").append(id).append("\n");
    sb.append("Person Details: ").append(personDetails).append("\n");
    sb.append("Accounts:\n");
    for (Account account : accounts) {
        sb.append(account).append("\n");
    }
    sb.append("=========================\n");
    return sb.toString();
}

}
