package com.shoprich.ShopRich.database;

//import com.shoprich.ShopRich.model.Seance;

import com.shoprich.ShopRich.model.Order;
import com.shoprich.ShopRich.model.Product;
import com.shoprich.ShopRich.model.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;

public class SQLrequests implements SqlRequestsInterface {
    public final static String productDefaultImgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTrk-bRE1bZZO_ld11ufN3BnH2YUhesww2cGXPPe-6fITsyNA8O0bujMpO_5hda3sqsiYU&usqp=CAU";

    @Override
    public boolean findUserByEmail(User user) throws SQLException {
        String SQL_FIND_USER_BY_EMAIL = "SELECT user_id FROM users WHERE user_email=(?)";
        PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_FIND_USER_BY_EMAIL);
        preparedStatement.setString(1, user.getEmail());
        ResultSet result = preparedStatement.executeQuery();
        return result.next();
    }

    @Override
    public void userToBlackList(String userEmail, boolean toBlackListOrFrom) throws SQLException {
        String SQL_USER_TO_BLACK_LIST = "UPDATE users SET user_is_banned=(?) WHERE user_email=(?)";
        PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_USER_TO_BLACK_LIST);
        preparedStatement.setBoolean(1, toBlackListOrFrom);
        preparedStatement.setString(2, userEmail);
        preparedStatement.executeUpdate();
    }

    @Override
    public String getPurOrderStatus(int purId) throws SQLException {
        String SQL_GET_PUR_STATUS = "SELECT * FROM pur_process WHERE purchase_id=(?)";
        PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_GET_PUR_STATUS);
        preparedStatement.setInt(1, purId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("pur_status");
        } else {
            return "none";
        }
    }

    @Override
    public Product getInfoAboutProduct(int productId) throws SQLException {
        String SQL_GET_INFO_ABOUT_PRODUCT = "SELECT * FROM products WHERE product_id=(?)";
        PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_GET_INFO_ABOUT_PRODUCT);
        preparedStatement.setInt(1, productId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Product(resultSet.getString("product_name"), resultSet.getString("product_img_url"),
                    resultSet.getInt("product_price"));
        } else return null;
    }


    @Override
    public void deleteAllPurchases() throws SQLException {
        String SQL_GET_ALL_EMAILS = "SELECT * FROM users";
        Statement statement = ConnectorToDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_GET_ALL_EMAILS);
        while (resultSet.next()) {
            clearAllUserPurHistory(resultSet.getString("user_email"));
        }
    }

    @Override
    public boolean isUserBanned(String userEmail) throws SQLException {
        String SQL_IS_USER_BANNED = "SELECT * FROM users WHERE user_email=(?)";
        PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_IS_USER_BANNED);
        preparedStatement.setString(1, userEmail);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getBoolean("user_is_banned");
        } else return false;
    }

    @Override
    public void addNewProduct(Product product) throws SQLException {
        String SQL_ADD_NEW_PRODUCT = "INSERT INTO products (product_quantity, product_name, product_desc, product_price, product_img_url) VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_ADD_NEW_PRODUCT);
        preparedStatement.setInt(1, product.getProductQuantity());
        preparedStatement.setString(2, product.getProductName());
        preparedStatement.setString(3, product.getProductDesc());
        preparedStatement.setInt(4, product.getProductPrice());
        preparedStatement.setString(5, product.getProductImgUrl().equals("none") ? productDefaultImgUrl : product.getProductImgUrl());
        preparedStatement.executeUpdate();
    }

    @Override
    public void deletePurProcess(int purProcId) throws SQLException {
        String SQL_DELETE_PUR_PROC = "DELETE FROM pur_process WHERE pur_process_id=(?)";
        PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_DELETE_PUR_PROC);
        preparedStatement.setInt(1, purProcId);
        preparedStatement.executeUpdate();
    }

    @Override
    public void editProductById(Product product) throws SQLException {
        String SQL_EDIT_PRODUCT_BY_ID = "UPDATE products set (product_quantity, product_name, product_desc, product_price, product_img_url)=(?,?,?,?,?) WHERE product_id=(?)";
        PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_EDIT_PRODUCT_BY_ID);
        preparedStatement.setInt(1, product.getProductQuantity());
        preparedStatement.setString(2, product.getProductName());
        preparedStatement.setString(3, product.getProductDesc());
        preparedStatement.setInt(4, product.getProductPrice());
        preparedStatement.setString(5, product.getProductImgUrl());
        preparedStatement.setInt(6, product.getProductId());
        preparedStatement.executeUpdate();
    }

    @Override
    public boolean isPurDone(int purId) throws SQLException {
        String SQL_CHECK_PUR = "SELECT * FROM pur_process WHERE purchase_id=(?)";
        PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_CHECK_PUR);
        preparedStatement.setInt(1, purId);
        return preparedStatement.executeQuery().next();
    }

    @Override
    public void clearAllUserPurHistory(String userEmail) throws SQLException {
        String SQL_GET_PURCHASE_QUANTITY = "SELECT * FROM purchased_product WHERE pur_buyer=(?)";
        PreparedStatement preparedStatement1 = ConnectorToDB.getConnection().prepareStatement(SQL_GET_PURCHASE_QUANTITY);
        preparedStatement1.setString(1, userEmail);
        ResultSet quantity = preparedStatement1.executeQuery();
        int quantityToReturn = 0;
        int productId;
        TreeMap<Integer, Integer> productAndQuantity = new TreeMap<>();
        while (quantity.next()) {
            productId = quantity.getInt("pur_product_id");
            quantityToReturn = quantity.getInt("pur_quantity");
            if (productAndQuantity.containsKey(productId)) {
                quantityToReturn += productAndQuantity.get(productId);
                productAndQuantity.replace(productId, quantityToReturn);
            } else {
                productAndQuantity.put(productId, quantityToReturn);
            }
        }
        String SQL_CHANGE_QUANTITY = "UPDATE products SET product_quantity=(?) WHERE product_id=(?)";
        PreparedStatement preparedStatement2 = ConnectorToDB.getConnection().prepareStatement(SQL_CHANGE_QUANTITY);
        String SQL_GET_OLD_QUANTITY_VALUE = "SELECT product_quantity FROM products WHERE product_id=(?)";
        PreparedStatement preparedStatement3 = ConnectorToDB.getConnection().prepareStatement(SQL_GET_OLD_QUANTITY_VALUE);
        for (Integer id : productAndQuantity.keySet()) {
            preparedStatement3.setInt(1, id);
            ResultSet resultSet = preparedStatement3.executeQuery();
            resultSet.next();
            preparedStatement2.setInt(1, resultSet.getInt("product_quantity") + productAndQuantity.get(id));
            preparedStatement2.setInt(2, id);
            preparedStatement2.executeUpdate();
        }
        String SQL_CLEAR_USER_HISTORY = "DELETE FROM purchased_product WHERE pur_buyer=(?)";
        PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_CLEAR_USER_HISTORY);
        preparedStatement.setString(1, userEmail);
        preparedStatement.executeUpdate();
    }

    @Override
    public void clearOnePurHistory(int purchaseId, String userEmail) throws SQLException {
        String SQL_GET_QUANTITY = "SELECT * FROM purchased_product WHERE pur_id=(?)";
        PreparedStatement preparedStatement0 = ConnectorToDB.getConnection().prepareStatement(SQL_GET_QUANTITY);
        preparedStatement0.setInt(1, purchaseId);
        ResultSet result = preparedStatement0.executeQuery();
        result.next();
        int quantity = result.getInt("pur_quantity");
        int productId = result.getInt("pur_product_id");
        String SQL_GET_OLD_QUANTITY_VALUE = "SELECT product_quantity FROM products WHERE product_id=(?)";
        PreparedStatement preparedStatement3 = ConnectorToDB.getConnection().prepareStatement(SQL_GET_OLD_QUANTITY_VALUE);
        preparedStatement3.setInt(1, productId);
        ResultSet resultSet = preparedStatement3.executeQuery();
        if (resultSet.next()) {
            quantity += resultSet.getInt("product_quantity");
            String SQL_ADD_QUANTITY = "UPDATE products SET product_quantity=(?) WHERE product_id=(?)";
            PreparedStatement preparedStatement1 = ConnectorToDB.getConnection().prepareStatement(SQL_ADD_QUANTITY);
            preparedStatement1.setInt(1, quantity);
            preparedStatement1.setInt(2, productId);
            preparedStatement1.executeUpdate();
        }
        String SQL_CLEAR_ONE_PUR_HISTORY = "DELETE FROM purchased_product WHERE (pur_id=(?) and pur_buyer=(?))";
        PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_CLEAR_ONE_PUR_HISTORY);
        preparedStatement.setInt(1, purchaseId);
        preparedStatement.setString(2, userEmail);
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteProductById(int productId) throws SQLException {
        String SQL_DELETE_PRODUCT_BY_ID = "DELETE FROM products WHERE product_id=(?)";
        PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_DELETE_PRODUCT_BY_ID);
        preparedStatement.setInt(1, productId);
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteAllProducts() throws SQLException {
        String SQL_DELETE_ALL_PRODUCTS = "DELETE FROM products";
        Statement statement = ConnectorToDB.getConnection().createStatement();
        statement.executeUpdate(SQL_DELETE_ALL_PRODUCTS);
    }

    @Override
    public void addNewOrderProc(Order order) throws SQLException {
        String SQL_ADD_NEW_ORDER_PROC = "INSERT INTO pur_process (purchase_id, pur_buyer_fname, pur_buyer_sname, pur_buyer_email, pur_buyer_phone, pur_buyer_address, pur_price) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_ADD_NEW_ORDER_PROC);
        preparedStatement.setInt(1, order.getPurId());
        preparedStatement.setString(2, order.getfName());
        preparedStatement.setString(3, order.getsName());
        preparedStatement.setString(4, order.getEmail());
        preparedStatement.setString(5, order.getPhoneNumber());
        preparedStatement.setString(6, order.getAddress());
        preparedStatement.setInt(7, order.getPurPrice());
        preparedStatement.executeUpdate();
    }

    @Override
    public boolean userAuthorization(User user, HttpServletRequest request) throws SQLException {
        if (findUserByEmail(user)) {
            String SQL_AUTHORIZATION_CHECK = "SELECT user_id FROM users WHERE user_email=(?) AND user_pass=(?)";
            PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_AUTHORIZATION_CHECK);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return true;
            } else {
                request.getSession().setAttribute("passwordIsWrong", true);
                return false;
            }
        } else {
            request.getSession().setAttribute("emailNotFound", true);
            return false;
        }
    }


    @Override
    public void editUserInfo(String newUserName, String newUserPassword, int userId, String newUserPhone) throws SQLException {
        String SQL_CHANGE_USER_INFO_BY_ID;
        if (newUserPhone != null && !newUserPhone.equals("")) {
            String SQL_CHANGE_USER_PHONE = "UPDATE users SET user_phone=(?) WHERE user_id=(?)";
            PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_CHANGE_USER_PHONE);
            preparedStatement.setString(1, newUserPhone);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        }
        if (newUserName != null && !newUserName.equals("")) {
            if (newUserPassword != null && !newUserPassword.equals("")) {
                SQL_CHANGE_USER_INFO_BY_ID = "UPDATE users SET user_name=(?),user_pass=(?) WHERE user_id=(?)";
                PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_CHANGE_USER_INFO_BY_ID);
                preparedStatement.setString(1, newUserName);
                preparedStatement.setString(2, newUserPassword);
                preparedStatement.setInt(3, userId);
                preparedStatement.executeUpdate();
            } else {
                SQL_CHANGE_USER_INFO_BY_ID = "UPDATE users SET user_name=(?) WHERE user_id=(?)";
                PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_CHANGE_USER_INFO_BY_ID);
                preparedStatement.setString(1, newUserName);
                preparedStatement.setInt(2, userId);
                preparedStatement.executeUpdate();
            }
        } else if (newUserPassword != null && !newUserPassword.equals("")) {
            SQL_CHANGE_USER_INFO_BY_ID = "UPDATE users SET user_pass=(?) WHERE user_id=(?)";
            PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_CHANGE_USER_INFO_BY_ID);
            preparedStatement.setString(1, newUserPassword);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        }
    }


    @Override
    public synchronized boolean buyProductForUser(String userEmail, int productId, int purQuantity, int newProductQuantity, String purDateAndTime) throws SQLException {
        if (isUserBanned(userEmail)) {
            return false;
        } else {
            String SQL_BUY_PRODUCT = "INSERT INTO purchased_product (pur_product_id, pur_buyer, pur_quantity, pur_date_and_time) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_BUY_PRODUCT);
            preparedStatement.setInt(1, productId);
            preparedStatement.setString(2, userEmail);
            preparedStatement.setInt(3, purQuantity);
            preparedStatement.setString(4, purDateAndTime);
            preparedStatement.executeUpdate();
            String SQL_CHANGE_PRODUCT_QUANTITY = "UPDATE products SET product_quantity=(?) WHERE product_id=(?)";
            PreparedStatement prepStm = ConnectorToDB.getConnection().prepareStatement(SQL_CHANGE_PRODUCT_QUANTITY);
            prepStm.setInt(1, newProductQuantity);
            prepStm.setInt(2, productId);
            prepStm.executeUpdate();
            return true;
        }
    }


    @Override
    public String getUserPasswordByEmail(String email) throws SQLException {
        String SQL_GET_USER_PASSWORD = "SELECT * FROM users WHERE user_email=(?)";
        PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_GET_USER_PASSWORD);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("user_pass");
        } else return "none";
    }

    @Override
    public String getUserRemindTimeByEmail(String email) throws SQLException {
        String SQL_GET_USER_PASSWORD = "SELECT * FROM users WHERE user_email=(?)";
        PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_GET_USER_PASSWORD);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("remind_time");
        } else return "none";
    }

    @Override
    public void changeRemindTime(String email, String remindTime) throws SQLException {
        String SQL_CHANGE_USER_REMINDED_TIME = "UPDATE users SET remind_time=(?) WHERE user_email=(?)";
        PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_CHANGE_USER_REMINDED_TIME);
        preparedStatement.setString(1, remindTime);
        preparedStatement.setString(2, email);
        preparedStatement.executeUpdate();

    }

    @Override
    public boolean addUser(User user) throws SQLException {
        if (!findUserByEmail(user)) {
            String SQL_ADD_NEW_USER = "INSERT INTO users (user_name, user_email, user_pass, user_phone) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_ADD_NEW_USER);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.executeUpdate();
            return true;
        } else return false;

    }

    @Override
    public void deleteNotReadyPur(int purId) throws SQLException {
        String SQL_DELETE_NOT_READY = "DELETE FROM pur_process WHERE purchase_id=(?)";
        PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_DELETE_NOT_READY);
        preparedStatement.setInt(1, purId);
        preparedStatement.executeUpdate();
    }

    @Override
    public void setOrderStatusReady(int purProcId) throws SQLException {
        String SQL_SET_ORDER_STATUS_READY = "UPDATE pur_process SET pur_status='ready' WHERE pur_process_id=(?)";
        PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_SET_ORDER_STATUS_READY);
        preparedStatement.setInt(1, purProcId);
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteFromPursAndOrders(int purProcId, int purId) throws SQLException {
        String SQL_DELETE_FROM_PUR_PROC = "DELETE FROM pur_process WHERE pur_process_id=(?)";
        PreparedStatement preparedStatement = ConnectorToDB.getConnection().prepareStatement(SQL_DELETE_FROM_PUR_PROC);
        preparedStatement.setInt(1, purProcId);
        preparedStatement.executeUpdate();
        String SQL_DELETE_FROM_PURS = "DELETE FROM purchased_product WHERE pur_id=(?)";
        PreparedStatement preparedStatement1 = ConnectorToDB.getConnection().prepareStatement(SQL_DELETE_FROM_PURS);
        preparedStatement1.setInt(1, purId);
        preparedStatement1.executeUpdate();
    }


}
