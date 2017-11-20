package proyectointegrador.bidup.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Auction {
    private String _id;
    private User user;
    private String objectName;
    private double initialAmount;
    private Date created;
    private Date lastDate;
    private BidUp currentBidUp;
    private String[] photosUrl;
    private ArrayList<BidUp> bidUpsList;
    private ArrayList<User> followersList;
    public Auction(){}
    public Auction(String _id, String objectName, double initialAmount) {
        this._id = _id;
        this.objectName = objectName;
        this.initialAmount = initialAmount;
    }
    public Auction(String _id, String objectName,double initialAmount, User user) {
        this._id = _id;
        this.objectName = objectName;
        this.initialAmount = initialAmount;
        this.user = user;
    }
    public Auction(String _id, String objectName, double initialAmount, User user, String[] photosUrl){
        this._id = _id;
        this.objectName = objectName;
        this.initialAmount = initialAmount;
        this.user = user;
        this.photosUrl = photosUrl;
    }
    public Auction(String _id, User user, String objectName, double initialAmount, Date created, Date lastDate, String[] photosUrl, ArrayList<BidUp> bidUpsList, ArrayList<User> followersList) {
        this._id = _id;
        this.user = user;
        this.objectName = objectName;
        this.initialAmount = initialAmount;
        this.created = created;
        this.lastDate = lastDate;
        this.photosUrl = photosUrl;
        this.bidUpsList = bidUpsList;
        this.followersList = followersList;
    }
    public String get_id() {
        return _id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public double getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(double initialAmount) {
        this.initialAmount = initialAmount;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public BidUp getCurrentBidUp() {
        return currentBidUp;
    }

    public void setCurrentBidUp(BidUp currentBidUp) {
        this.currentBidUp = currentBidUp;
    }

    public String[] getPhotosUrl() {
        return photosUrl;
    }

    public void setPhotosUrl(String[] photosUrl) {
        this.photosUrl = photosUrl;
    }

    public ArrayList<BidUp> getBidUpsList() {
        return bidUpsList;
    }

    public ArrayList<User> getFollowersList() {
        return followersList;
    }

    @Override
    public String toString() {
        return "Nombre: " + objectName + '\n' +
                "Precio inicial: " + initialAmount + '\n';
    }
}
