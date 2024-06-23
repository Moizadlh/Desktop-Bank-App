package BMS;


import java.io.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

public class Bank implements Serializable 
{
    String name;
    ArrayList<Client> cllist;
    List<Account> aclist;
    List<Person> pList;

    // LogINS DATA
    List<String> Usernames = new ArrayList<>();
    private int userind = 0;
    List<String> Userpassw = new ArrayList<>();
    List<String> Adnames = Arrays.asList("Moeez", "Muawwaz");
    private int ind = 0;
    List<String> Adpassw = Arrays.asList("1234", "4321");

    // Requests and Complains
    ArrayList<String> Requests = new ArrayList<>();

    public Bank(String name) {
        this.name   = name;
        this.cllist = new ArrayList<>();
        this.aclist = new ArrayList<>();
        this.pList  = new ArrayList<>();
    }
    
    public ArrayList<String> getRequests() {
        return Requests;
    }

    public List<String> getUsernames() {
        return Usernames;
    }

    public List<String> getUserpassw() {
        return Userpassw;
    }

    public ArrayList<Client> getCllist() {
        return cllist;
    }

    public List<Account> getAclist() {
        return aclist;
    }

    public List<Person> getpList() {
        return pList;
    }

    public String getName() {
        return name;
    }

    public Boolean IsGuest(String name){
        for (int i = 0; i < Usernames.size(); i++) {
            if (Usernames.get(i).equals(name)) {
                userind = i;
                return true;
            }
        }
        return false;
    }

    public Boolean IsUserpassw (String passw){
        if (Userpassw.get(userind).equals(passw)) {
            return true;
        }
        return false;
    }

    public Boolean IsAdmin(String names){
        for (int index = 0; index < Adnames.size(); index++) {
            if (Adnames.get(index).equals(names)) {
                ind = index;
                return true;
            }
        }
        return false;
    }

    public Boolean IsAdpassw(String passw){
            if (Adpassw.get(ind).equals(passw)) {
                return true;
            }
        return false;
    }

    public void add_Person(Person p){
        pList.add(p);
    }

    public void add_guest_name(String s){
        Usernames.add(s);
    }
    public void add_guest_passw(String s){
        Userpassw.add(s);
    }


    public Client add_client(Person p){
        Client newClient = new Client(p);
        cllist.add(newClient);
        return newClient;
    }

    public Account addAccount(float amount, Client c) {
        Account newAccount = new Account(amount, c);
        aclist.add(newAccount);
        // c.addAccount(newAccount);
        return newAccount;
    }

    

    public Account searchAccount(String accNo) {
        for (Account account : aclist) {
            if (account.getNumber().equals(accNo)) {
                return account;
            }
        }
        System.out.println("Account not found.");
        return null;
    }

    public boolean removeClient(int clientId) {
        for (Client client : cllist) {
            if (client.getId() == clientId) {
                cllist.remove(client);
                for (Account account : client.getAccounts()) {
                    aclist.remove(account);
                    for (String Request : Requests) {
                        String[] parts = Request.split("\\|");
                        if (Integer.parseInt(parts[1]) == clientId) {
                            Requests.remove(Request);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public float totalAmount() {
        float total = 0;
        for (Account account : aclist) {
            total += account.getAmount();
        }
        return total;
    }
    public int TotalCustomers(){
        int i = cllist.size();
        return i;
    }

    public Client searchCustomerDetail(String cnic) {
        for (Client client : cllist) {
            if (client.getPersonDetails().getCNIC().equals(cnic)) {
                return client;
            }
        }
        System.out.println("Customer not found.");
        return null;
    }

    public Client findClientById(int clientId) {
        for (Client client : cllist) {
            if (client.getId() == clientId) {
                return client;
            }
        }
        return null;
    }

    public Boolean findClient(int clientId) {
        for (Client client : cllist) {
            if (client.getId() == clientId) {
                return true;
            }
        }
        return false;
    }


    public void updateAccount(Account updatedAccount) {
        for (int i = 0; i < aclist.size(); i++) {
            if (aclist.get(i).getNumber().equals(updatedAccount.getNumber())) {
                aclist.set(i, updatedAccount);
                return;
            }
        }
        System.out.println("Account not found for updating.");
    }

    public void updateClient(Client updatedClient) {
        for (int i = 0; i < cllist.size(); i++) {
            if (cllist.get(i).getId() == updatedClient.getId()) {
                cllist.set(i, updatedClient);
                return;
            }
        }
        System.out.println("Client not found for updating.");
    }

    //#region File Handling

    public void Person_To_File() throws IOException
    {
        FileOutputStream fos=new FileOutputStream("E:\\BMS\\BMS\\persons.txt");
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(pList);
        oos.close();
        fos.close();
    }

    public void Person_from_File() throws IOException, ClassNotFoundException{
        FileInputStream fis=new FileInputStream("E:\\BMS\\BMS\\persons.txt");
        ObjectInputStream ois=new ObjectInputStream(fis);
        pList=(ArrayList<Person>) ois.readObject();
        ois.close();
        fis.close();
    }


    public void To_File() throws IOException
    {
        FileOutputStream fos=new FileOutputStream("E:\\BMS\\BMS\\clients.txt");
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(cllist);
        oos.close();
        fos.close();
    }

    public void from_File() throws IOException, ClassNotFoundException{
        FileInputStream fis=new FileInputStream("E:\\BMS\\BMS\\clients.txt");
        ObjectInputStream ois=new ObjectInputStream(fis);
        cllist=(ArrayList<Client>) ois.readObject();
        ois.close();
        fis.close();
        for (int index = 0; index < cllist.size(); index++) {
            int i = index + 1;
            if (i == cllist.size()) {
                Client client = cllist.get(index);
                Client.count = client.getId();
            }
        }
        // Client.count = cllist.size();
    }

    public void Acc_To_File() throws IOException
    {
        FileOutputStream fos=new FileOutputStream("E:\\BMS\\BMS\\accounts.txt");
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(aclist);
        oos.close();
        fos.close();
    }

    public void Acc_from_File() throws IOException, ClassNotFoundException{
        FileInputStream fis=new FileInputStream("E:\\BMS\\BMS\\accounts.txt");
        ObjectInputStream ois=new ObjectInputStream(fis);
        aclist=(ArrayList<Account>) ois.readObject();
        ois.close();
        fis.close();
        for (int index = 0; index < aclist.size(); index++) {
            int i = index + 1;
            if (i == aclist.size()) {
                Account acc = aclist.get(index);
                String Number = acc.getNumber();
                String[] parts = Number.split("A");
                int count = Integer.parseInt(parts[1]);
                Account.setCount(count);
            }
        }
        // Account.setCount(aclist.size());
    }

// GUEST USER FILES
    public void GUEST_To_File() throws IOException
    {
        FileOutputStream fos=new FileOutputStream("E:\\BMS\\BMS\\guests.txt");
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(Usernames);
        oos.close();
        fos.close();
    }

    public void GUEST_from_File() throws IOException, ClassNotFoundException{
        FileInputStream fis=new FileInputStream("E:\\BMS\\BMS\\guests.txt");
        ObjectInputStream ois=new ObjectInputStream(fis);
        Usernames=(ArrayList<String>) ois.readObject();
        ois.close();
        fis.close();
    }

    public void GUESTP_To_File() throws IOException
    {
        FileOutputStream fos=new FileOutputStream("E:\\BMS\\BMS\\guestspass.txt");
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(Userpassw);
        oos.close();
        fos.close();
    }

    public void GUESTP_from_File() throws IOException, ClassNotFoundException{
        FileInputStream fis=new FileInputStream("E:\\BMS\\BMS\\guestspass.txt");
        ObjectInputStream ois=new ObjectInputStream(fis);
        Userpassw=(ArrayList<String>) ois.readObject();
        ois.close();
        fis.close();
    }

    public void Req_To_File() throws IOException
    {
        FileOutputStream fos=new FileOutputStream("E:\\BMS\\BMS\\Complains.txt");
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(Requests);
        oos.close();
        fos.close();
    }

    public void Req_from_File() throws IOException, ClassNotFoundException{
        FileInputStream fis=new FileInputStream("E:\\BMS\\BMS\\Complains.txt");
        ObjectInputStream ois=new ObjectInputStream(fis);
        Requests=(ArrayList<String>) ois.readObject();
        ois.close();
        fis.close();
    }
    //#endregion
}



