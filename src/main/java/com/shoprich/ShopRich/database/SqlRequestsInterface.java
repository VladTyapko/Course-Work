package com.shoprich.ShopRich.database;

//import com.shoprich.ShopRich.model.Seance;

import com.shoprich.ShopRich.model.Order;
import com.shoprich.ShopRich.model.Product;
import com.shoprich.ShopRich.model.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

public interface SqlRequestsInterface {

    boolean addUser(User user) throws SQLException;

    void deleteNotReadyPur(int purId) throws SQLException;

    void setOrderStatusReady(int purProcId) throws SQLException;

    void deleteFromPursAndOrders(int purProcId,int purId) throws SQLException;

    boolean findUserByEmail(User user) throws SQLException;

    void userToBlackList(String userEmail,boolean toBlackListOrFrom) throws SQLException;

    String getPurOrderStatus(int purId) throws SQLException;

    Product getInfoAboutProduct(int purId) throws SQLException;

    void deleteAllPurchases() throws SQLException;

    boolean isUserBanned(String userEmail) throws SQLException;

    void addNewProduct(Product product) throws SQLException;

    void deletePurProcess(int purProcId) throws SQLException;

    void editProductById(Product product) throws SQLException;

    boolean isPurDone(int purId) throws SQLException;

    void clearAllUserPurHistory(String userEmail) throws SQLException;

    void clearOnePurHistory(int purchaseId, String userEmail) throws SQLException;

    void deleteProductById(int productId) throws SQLException;

    void deleteAllProducts() throws SQLException;

    void addNewOrderProc(Order order) throws  SQLException;

    boolean userAuthorization(User user, HttpServletRequest request) throws SQLException;

    void editUserInfo(String newUserName, String newUserPassword, int userId, String newUserPhone) throws SQLException;

    boolean buyProductForUser(String userEmail, int productId, int purQuantity, int newProductQuantity, String purDateAndTime) throws SQLException;

    String getUserPasswordByEmail(String email) throws SQLException;

    String getUserRemindTimeByEmail(String email) throws SQLException;

    void changeRemindTime(String email, String remindTime) throws SQLException;
}
