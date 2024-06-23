package BMS;

import java.io.Serializable;

public class Person implements Serializable  {
    private String name, CNIC, phone;

    Person(String nam,String CNIC,String phon){
        this.name = nam;
        this.CNIC = CNIC;
        this.phone = phon;
    }

    public String getName() {
        return name;
    }

    public String getCNIC() {
        return CNIC;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString(){
        return "\nYour name===" + name + ", Your CNIC===" + CNIC + ", Your phone number===" + phone + " "
        ;
    }
}