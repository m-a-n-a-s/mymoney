package com.example.mymoney;

public class home_budget {
    private String amount;
    private String spend;
    private String spend_today;
    private String start_date;
    private String end_date;
    private String days_left;

    public home_budget(String amount, String spend, String spend_today, String start_date, String end_date, String days_left) {
        this.amount = amount;
        this.spend = spend;
        this.spend_today = spend_today;
        this.start_date = start_date;
        this.end_date = end_date;
        this.days_left = days_left;
    }

    //getter

    public String getAmount() {
        return amount;
    }

    public String getSpend() {
        return spend;
    }

    public String getSpend_today() {
        return spend_today;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getDays_left() {
        return days_left;
    }

    //Setter

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setSpend(String spend) {
        this.spend = spend;
    }

    public void setSpend_today(String spend_today) {
        this.spend_today = spend_today;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setDays_left(String days_left) {
        this.days_left = days_left;
    }
}
