package proyectointegrador.bidup.models;

import java.util.Date;

/**
 * Created by user on 2/11/2017.
 */

public class BidUp {
    private String _id;
    private User user;
    private String card;
    private double amount;
    private Date created;

    public BidUp(String _id, User user, double amount, Date created) {
        this._id = _id;
        this.user = user;
        this.amount = amount;
        this.created = created;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
