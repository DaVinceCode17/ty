package com.resources.service;

import com.resources.dao.CustomerDAO;
import com.resources.dao.OrderDAO;
import com.resources.dao.PricingDAO;
import com.resources.model.Customer;
import com.resources.model.Order;
import com.resources.model.Pricing;
import java.sql.SQLException;
import java.util.List;

public class LaundryService {
    
    private CustomerDAO customerDAO;
    private OrderDAO orderDAO;
    private PricingDAO pricingDAO;
    
    public LaundryService() {
        customerDAO = new CustomerDAO();
        orderDAO = new OrderDAO();
        pricingDAO = new PricingDAO();
    }
    
    public Customer login(String contact, String password) throws SQLException {
        return customerDAO.findByContactAndPassword(contact, password);
    }
    
    public boolean register(Customer customer) throws SQLException {
        if (customerDAO.findByContact(customer.getContact()) != null) {
            throw new IllegalArgumentException("Contact already registered");
        }
        return customerDAO.save(customer);
    }
    
    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.findAll();
    }
    
    public Customer getCustomerById(int id) throws SQLException {
        return customerDAO.findById(id);
    }
    
    public Customer getCustomerByContact(String contact) throws SQLException {
        return customerDAO.findByContact(contact);
    }
    
    public Order createOrder(Order order) throws SQLException {
        order.setQueueNumber(orderDAO.getNextQueueNumber());
        order.setStatus("set_pricing");
        return orderDAO.saveToSetPricing(order);
    }
    
    public boolean updateSetPricingWeightAndPrice(int queueNumber, int customerId, double weight, double price) throws SQLException {
        return orderDAO.updateSetPricingWeightAndPrice(queueNumber, customerId, weight, price);
    }
    
    public Order moveToPending(int queueNumber, int customerId) throws SQLException {
        return orderDAO.moveToPending(queueNumber, customerId);
    }
    
    public Order moveToWash(int queueNumber, int customerId) throws SQLException {
        return orderDAO.moveToWash(queueNumber, customerId);
    }
    
    public Order moveToDry(int queueNumber, int customerId) throws SQLException {
        return orderDAO.moveToDry(queueNumber, customerId);
    }
    
    public Order moveToIron(int queueNumber, int customerId) throws SQLException {
        return orderDAO.moveToIron(queueNumber, customerId);
    }
    
    public Order moveToFold(int queueNumber, int customerId) throws SQLException {
        return orderDAO.moveToFold(queueNumber, customerId);
    }
    
    public Order moveToForPickup(int queueNumber, int customerId) throws SQLException {
        return orderDAO.moveToForPickup(queueNumber, customerId);
    }
    
    public Order moveToDeliver(int queueNumber, int customerId) throws SQLException {
        return orderDAO.moveToDeliver(queueNumber, customerId);
    }
    
    public Order moveToClaimed(int queueNumber, int customerId) throws SQLException {
        return orderDAO.moveToClaimed(queueNumber, customerId);
    }
    
    public Order moveToClaimedFromPickup(int queueNumber, int customerId) throws SQLException {
        return orderDAO.moveToClaimedFromPickup(queueNumber, customerId);
    }
    
    public List<Order> getSetPricingOrders() throws SQLException {
        return orderDAO.getSetPricingOrders();
    }
    
    public List<Order> getOrdersByTable(String tableName) throws SQLException {
        return orderDAO.getOrdersByTable(tableName);
    }
    
    public List<Order> getLaundryLogs() throws SQLException {
        return orderDAO.getLaundryLogs();
    }
    
    public List<Order> getOrdersByCustomer(int customerId) throws SQLException {
        return orderDAO.getOrdersByCustomer(customerId);
    }
    
    public Pricing getPricing() throws SQLException {
        Pricing p = pricingDAO.getPricing();
        if (p == null) {
            pricingDAO.insertDefaultPricing();
            p = pricingDAO.getPricing();
        }
        return p;
    }
    
    public boolean updatePricing(Pricing pricing) throws SQLException {
        return pricingDAO.updatePricing(pricing);
    }
}