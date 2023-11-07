package edu.umb.cs681.hw02;

import java.time.LocalDate;

public class Dose {
    private String vacProductName;
    private String lotNumber;
    private LocalDate date;
    private String vacSite;

    public Dose(String vacProductName, String lotNumber, LocalDate date, String vacSite) {
        this.vacProductName = vacProductName;
        this.lotNumber = lotNumber;
        this.date = date;
        this.vacSite = vacSite;
    }

}
