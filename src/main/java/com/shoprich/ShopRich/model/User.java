package com.shoprich.ShopRich.model;

import java.util.ArrayList;
import java.util.Arrays;

public class User {
    private static ArrayList<String> adminsEmails = new ArrayList<>(Arrays.asList("shoprich3@gmail.com"));
    private static ArrayList<String> managersEmails = new ArrayList<>(Arrays.asList("shoprich3@gmail.com","ttatta3adpot@gmail.com"));
    private Integer userId = null;
    private String name;
    private String email;
    private String phone;
    private String password;
    private boolean isBanned;

    public static ArrayList<String> getAdminsEmails() {
        return adminsEmails;
    }

    public static ArrayList<String> getManagersEmails() {
        return managersEmails;
    }
    public static void addAdminToAdminsEmails(String newAdminEmail) {
        adminsEmails.add(newAdminEmail);
    }

    public static void addManagerToManagersEmails(String newManagerEmail) {
        managersEmails.add(newManagerEmail);
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password, String phone) {

        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
