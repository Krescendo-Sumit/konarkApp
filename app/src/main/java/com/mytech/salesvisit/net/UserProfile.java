package com.mytech.salesvisit.net;

public class UserProfile {
    private int UserId;
    private String Name;
    private String JoiningDate;
    private String Company;
    private String Designation;
    private String Department;
    private String Division;
    private String Location;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getJoiningDate() {
        return JoiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        JoiningDate = joiningDate;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
