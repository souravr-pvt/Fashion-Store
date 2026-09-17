package com.fashionstore.model;

public class User {
    
    private int userId;
    private String fullName;
    private String email;
    private String phone;
    private String password;
    private String gender;
    private String address;

    // 1. Default Constructor (Required by frameworks like Servlets/JSP)
    public User() {
    }

    // 2. Parameterized Constructor (Useful when fetching data from the database)
    public User(int userId, String fullName, String email, String phone, String password, String gender, String address) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.gender = gender;
        this.address = address;
    }

    // 3. Constructor without userId (Useful when registering a NEW user, since DB auto-increments ID)
    public User(String fullName, String email, String phone, String password, String gender, String address) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.gender = gender;
        this.address = address;
    }

    // 4. Getters and Setters (To securely access and update the private fields)
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    // 5. toString() method (Great for debugging to print user details in the console)
    @Override
    public String toString() {
        return "User [userId=" + userId + ", fullName=" + fullName + ", email=" + email + 
               ", phone=" + phone + ", gender=" + gender + "]";
    }
}