package com.example.mymoney;

public class Transaction {
    private String info;
    private String date;
    private String expenditure;
    private String receive;

    public Transaction(String info, String date, String expenditure, String receive) {
        this.info = info;
        this.date = date;
        this.expenditure = expenditure;
        this.receive = receive;
    }

    public String getInfo() {
        return info;
    }

    public String getDate() {
        return date;
    }

    public String getExpenditure() {
        return expenditure;
    }

    public String getReceive() {
        return receive;
    }
}
