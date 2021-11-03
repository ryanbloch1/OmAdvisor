package com.cobi.puresurveyandroid.model;

public class PostStaffRequest {

    private String deviceToken;
    private String digitalId;
    private String salesCode;
    private String emailAddress;
    private String area;
    private String team;
    private String region;
    private String role;
    private String channel;
    private String segment;
    private String firstName;
    private String lastName;

    public PostStaffRequest(String deviceToken, String digitalId, String salesCode, String emailAddress, String area, String team, String region, String role, String channel, String segment, String firstName, String lastName) {
        this.deviceToken = deviceToken;
        this.digitalId = digitalId;
        this.salesCode = salesCode;
        this.emailAddress = emailAddress;
        this.area = area;
        this.team = team;
        this.region = region;
        this.role = role;
        this.channel = channel;
        this.segment = segment;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDigitalId() {
        return digitalId;
    }

    public void setDigitalId(String digitalId) {
        this.digitalId = digitalId;
    }

    public String getSalesCode() {
        return salesCode;
    }

    public void setSalesCode(String salesCode) {
        this.salesCode = salesCode;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
