package entity;

public class Phone {

    private String phoneNumber;
    private String phoneDescription;
    private String phoneComment;

    public Phone() {
    }

    public String getPhoneNumber() {return phoneNumber;}

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneDescription() {
        return phoneDescription;
    }

    public void setPhoneDescription(String phoneDescription) {
        this.phoneDescription = phoneDescription;
    }

    public String getPhoneComment() {
        return phoneComment;
    }

    public void setPhoneComment(String phoneComment) {
        this.phoneComment = phoneComment;
    }
}
