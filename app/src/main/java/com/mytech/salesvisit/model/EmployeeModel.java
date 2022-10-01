package com.mytech.salesvisit.model;

public class EmployeeModel {

    String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    String text;

    /*int UserID;//": 1,

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getEmployeeCode() {
        return EmployeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        EmployeeCode = employeeCode;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getJoiningDate() {
        return JoiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        JoiningDate = joiningDate;
    }

    public int getCompany() {
        return Company;
    }

    public void setCompany(int company) {
        Company = company;
    }

    public int getDesignation() {
        return Designation;
    }

    public void setDesignation(int designation) {
        Designation = designation;
    }

    public int getDepartment() {
        return Department;
    }

    public void setDepartment(int department) {
        Department = department;
    }

    public int getReportTo() {
        return ReportTo;
    }

    public void setReportTo(int reportTo) {
        ReportTo = reportTo;
    }

    public int getSalesCoordinator() {
        return SalesCoordinator;
    }

    public void setSalesCoordinator(int salesCoordinator) {
        SalesCoordinator = salesCoordinator;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public int getDivisionID() {
        return DivisionID;
    }

    public void setDivisionID(int divisionID) {
        DivisionID = divisionID;
    }

    public int getLocationID() {
        return LocationID;
    }

    public void setLocationID(int locationID) {
        LocationID = locationID;
    }

    public int getTicketSystemID() {
        return TicketSystemID;
    }

    public void setTicketSystemID(int ticketSystemID) {
        TicketSystemID = ticketSystemID;
    }

    public boolean isPartofSales() {
        return IsPartofSales;
    }

    public void setPartofSales(boolean partofSales) {
        IsPartofSales = partofSales;
    }

    public boolean isAdmin() {
        return IsAdmin;
    }

    public void setAdmin(boolean admin) {
        IsAdmin = admin;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getLastUpdated() {
        return LastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        LastUpdated = lastUpdated;
    }

    public boolean isUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(boolean updatedBy) {
        UpdatedBy = updatedBy;
    }

    public int getFK_EmployeeCategory() {
        return FK_EmployeeCategory;
    }

    public void setFK_EmployeeCategory(int FK_EmployeeCategory) {
        this.FK_EmployeeCategory = FK_EmployeeCategory;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public boolean isAllowLogon() {
        return AllowLogon;
    }

    public void setAllowLogon(boolean allowLogon) {
        AllowLogon = allowLogon;
    }

    public int getSequence() {
        return Sequence;
    }

    public void setSequence(int sequence) {
        Sequence = sequence;
    }

    public int getCTC() {
        return CTC;
    }

    public void setCTC(int CTC) {
        this.CTC = CTC;
    }

    public boolean isKRAApplicable() {
        return IsKRAApplicable;
    }

    public void setKRAApplicable(boolean KRAApplicable) {
        IsKRAApplicable = KRAApplicable;
    }

    public int getKRAAmount() {
        return KRAAmount;
    }

    public void setKRAAmount(int KRAAmount) {
        this.KRAAmount = KRAAmount;
    }

    public boolean isAppAccessAllowed() {
        return IsAppAccessAllowed;
    }

    public void setAppAccessAllowed(boolean appAccessAllowed) {
        IsAppAccessAllowed = appAccessAllowed;
    }

    public boolean isGEOTrackEnabled() {
        return IsGEOTrackEnabled;
    }

    public void setGEOTrackEnabled(boolean GEOTrackEnabled) {
        IsGEOTrackEnabled = GEOTrackEnabled;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    String EmployeeCode;//": null,
    String FirstName;//": "CRM",
    String LastName;//": "Admin",
    String UserName;//": "yogm",
    String Password;//": "TXlLZXlAMDgyMg==",
    String BirthDate;//": "1982-07-05T00:00:00",
    String JoiningDate;//": "2014-07-03T00:00:00",
    int Company;//": 3,
    int Designation;//": 9,
    int Department;//": 3,
    int ReportTo;//": 2,
    int SalesCoordinator;//": 2,
    String Email;//": "ymahajan@gmail.com",
    String ContactNumber;//": "YOGESH",
    int DivisionID;//": 5,
    int LocationID;//": 1,
    int TicketSystemID;//": 1,
    boolean IsPartofSales;//": false,
    boolean IsAdmin;//": true,
    boolean IsActive;//": true,
    String CreatedOn;//": "2019-02-26T00:00:00",
    String LastUpdated;//": "2022-08-01T00:00:00",
    boolean UpdatedBy;//": 2,
    int FK_EmployeeCategory;//": 6,
    String MiddleName;//": "VASUDEV",
    boolean AllowLogon;//": true,
    int Sequence;//": 1,
    int CTC;//": 0,
    boolean IsKRAApplicable;//": true,
    int KRAAmount;//": 200,
    boolean IsAppAccessAllowed;//": true,
    boolean IsGEOTrackEnabled;//": true,
    String FullName;//": "CRM Admin"
*/

}
