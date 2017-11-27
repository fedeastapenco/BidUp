package proyectointegrador.bidup.models;

import java.util.Date;

/**
 * Created by user on 2/11/2017.
 */

public class Card {
    private String _id;
    private int lastFour;
    private Date expirationDate;
    private String cvv;
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Card(String number, Date expirationDate, String cvv) {
        this.number = number;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    public Card(String id, int lastFour, Date expirationDate) {
        _id = id;
        this.lastFour = lastFour;
        this.expirationDate = expirationDate;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getLastFour() {
        return lastFour;
    }

    public void setLastFour(int lastFour) {
        this.lastFour = lastFour;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
