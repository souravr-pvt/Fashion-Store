package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.User;

public interface UserDao {
    
    // Core Authentication & Registration
    boolean registerUser(User user);
    User loginUser(String email, String password);
    
    // Validation & Checks (Crucial for registration and profile updates)
    boolean isEmailExist(String email);
    boolean isPhoneExist(String phone);
    
    // Fetching Users
    User getUserById(int userId);
    User getUserByEmail(String email);
    List<User> getAllUsers(); // To list users in an admin panel
    
    // Updating Data
    boolean updateUser(User user);
    boolean updatePassword(int userId, String newPassword);
    
    // Deleting/Deactivating
    boolean deleteUser(int userId);
}