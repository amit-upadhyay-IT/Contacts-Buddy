package me.binomial.contacts;

public class EmailInfo {

    private String address;
    private String type;

    public EmailInfo(String address, String type) {
        this.address = address;
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "EmailInfo{" +
                "address='" + address + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
