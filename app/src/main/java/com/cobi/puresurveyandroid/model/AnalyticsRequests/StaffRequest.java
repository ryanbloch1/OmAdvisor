package com.cobi.puresurveyandroid.model.AnalyticsRequests;

public class StaffRequest {

    public String DeviceId;
    public String DigitalId;
    public String EmployeeCode;
    public String DisplayName;
    public String Email;
    public String FirstName;
    public String LastName;
    public String Area;
    public String Branch;
    public String Province;
    public String Segment;
    public String EmployeeType;
    public String BusinessUnit;
    public String ContactNo;
    public String TokenId;

    public StaffRequest(String deviceId, String digitalId, String employeeCode, String displayName, String email, String firstName, String lastName, String area, String branch, String province, String segment, String employeeType, String businessUnit, String contactNo, String tokenId) {
        DeviceId = deviceId;
        DigitalId = digitalId;
        EmployeeCode = employeeCode;
        DisplayName = displayName;
        Email = email;
        FirstName = firstName;
        LastName = lastName;
        Area = area;
        Branch = branch;
        Province = province;
        Segment = segment;
        EmployeeType = employeeType;
        BusinessUnit = businessUnit;
        ContactNo = contactNo;
        TokenId = tokenId;
    }
}
