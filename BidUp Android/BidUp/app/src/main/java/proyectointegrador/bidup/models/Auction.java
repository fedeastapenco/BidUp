package proyectointegrador.bidup.models;

import java.util.ArrayList;
import java.util.Date;


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
    private boolean finished;
    public Auction(){}
    public Auction(String _id, String objectName, double initialAmount, boolean finished, Date created, Date lastDate) {
        this._id = _id;
        this.objectName = objectName;
        this.initialAmount = initialAmount;
        this.finished = finished;
        this.created = created;
        this.lastDate = lastDate;
    }
    public Auction(String _id, String objectName, double initialAmount, User user, boolean finished) {
        this._id = _id;
        this.objectName = objectName;
        this.initialAmount = initialAmount;
        this.user = user;
        this.finished = finished;
    }
    public Auction(String _id, String objectName, double initialAmount, User user, String[] photosUrl, boolean finished, Date created, Date lastDate){
        this._id = _id;
        this.objectName = objectName;
        this.initialAmount = initialAmount;
        this.user = user;
        this.photosUrl = photosUrl;
        this.finished = finished;
        this.lastDate = lastDate;
        this.created = created;
    }
    public Auction(String _id, User user, String objectName, double initialAmount, Date created, Date lastDate, String[] photosUrl, ArrayList<BidUp> bidUpsList, ArrayList<User> followersList, boolean finished) {
        this._id = _id;
        this.user = user;
        this.objectName = objectName;
        this.initialAmount = initialAmount;
        this.created = created;
        this.lastDate = lastDate;
        this.photosUrl = photosUrl;
        this.bidUpsList = bidUpsList;
        this.followersList = followersList;
        this.finished = finished;
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
    public boolean getFinished(){return finished;}

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
