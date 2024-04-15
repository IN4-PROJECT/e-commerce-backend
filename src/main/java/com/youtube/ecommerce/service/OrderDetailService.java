package com.youtube.ecommerce.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.youtube.ecommerce.configuration.JwtRequestFilter;
import com.youtube.ecommerce.dao.CartDao;
import com.youtube.ecommerce.dao.OrderDetailDao;
import com.youtube.ecommerce.dao.ProductDao;
import com.youtube.ecommerce.dao.UserDao;
import com.youtube.ecommerce.entity.*;
import com.youtube.ecommerce.repository.OrderDetailRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {

    private static final String ORDER_PLACED = "Placed";

    private static final String KEY = "rzp_test_AXBzvN2fkD4ESK";
    private static final String KEY_SECRET = "bsZmiVD7p1GMo6hAWiy4SHSH";
    private static final String CURRENCY = "INR";

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private OrderDetailRepository orderDetailRepository;



    public List<OrderProduct> getOrderProductListd(Integer orderId) {
        List<OrderProduct> orderProductList = new ArrayList<>();
        Optional<OrderDetail> optionalOrderDetail = orderDetailRepository.findById(Long.valueOf(orderId));
        if (optionalOrderDetail.isPresent()) {
            OrderDetail orderDetail = optionalOrderDetail.get();
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProductName(orderDetail.getProduct().getProductName());
            orderProduct.setOrderAmount(orderDetail.getOrderAmount());
            orderProduct.setOrderContactNumber(orderDetail.getOrderContactNumber());
            orderProduct.setOrderFullName(orderDetail.getOrderFullName());
            orderProduct.setOrderFullOrder(orderDetail.getOrderFullOrder());
            orderProduct.setOrderStatus(orderDetail.getOrderStatus());
            orderProduct.setTransactionId(orderDetail.getTransactionId());
            orderProduct.setProductDiscountedPrice(orderDetail.getProduct().getProductDiscountedPrice());
            orderProduct.setTime(orderDetail.getTime());
            orderProductList.add(orderProduct);
        }
        System.out.println("liste de donnees: " + orderProductList);
        return orderProductList;
    }

    public List<OrderProduct> getOrderProductListd() {
        List<OrderProduct> orderProductList = new ArrayList<>();
        List<OrderDetail> orderDetails = getAll();
        for (OrderDetail orderDetail : orderDetails) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProductName(orderDetail.getProduct().getProductName());
            orderProduct.setOrderAmount(orderDetail.getOrderAmount());
            orderProduct.setOrderContactNumber(orderDetail.getOrderContactNumber());
            orderProduct.setOrderFullName(orderDetail.getOrderFullName());
            orderProduct.setOrderFullOrder(orderDetail.getOrderFullOrder());
            orderProduct.setOrderStatus(orderDetail.getOrderStatus());
            orderProduct.setTransactionId(orderDetail.getTransactionId());
            orderProduct.setTime(orderDetail.getTime());
            orderProductList.add(orderProduct);
        }
        System.out.println("liste de donnees: " + orderProductList);
        return orderProductList;

    }

    public List<OrderDetail> getAll() {
        Pageable pageable = PageRequest.of(0,12);
        return (List<OrderDetail>) orderDetailDao.findAll(pageable);
    }

    public List<OrderDetail> getAllOrderDetails(String status) {
        List<OrderDetail> orderDetails = new ArrayList<>();

        if(status.equals("All")) {
            orderDetailDao.findAll().forEach(
                    x -> orderDetails.add(x)
            );
        } else {
            orderDetailDao.findByOrderStatus(status).forEach(
                    x -> orderDetails.add(x)
            );
        }


         return orderDetails;
    }

    public List<OrderDetail> getOrderDetails() {
        String currentUser = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(currentUser).get();

        return orderDetailDao.findByUser(user);
    }

    public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout) {
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();

        for (OrderProductQuantity o: productQuantityList) {
            Product product = productDao.findById(o.getProductId()).get();

            String currentUser = JwtRequestFilter.CURRENT_USER;
            User user = userDao.findById(currentUser).get();

//            java.util.Date.now = System.currentTimeMillis();
//            LocalDate date = new LocalDate();
            OrderDetail orderDetail = new OrderDetail(
                    orderInput.getFullName(),
                    orderInput.getFullAddress(),
                    orderInput.getContactNumber(),
                    orderInput.getAlternateContactNumber(),
                    ORDER_PLACED,
                    product.getProductDiscountedPrice() * o.getQuantity(),
                    product,
                    user,
                    orderInput.getTransactionId(),

                    new Date(System.currentTimeMillis())
            );

            // empty the cart.
            if(!isSingleProductCheckout) {
                List<Cart> carts = cartDao.findByUser(user);
                carts.stream().forEach(x -> cartDao.deleteById(x.getCartId()));
            }

            orderDetailDao.save(orderDetail);
        }
    }

    public void markOrderAsDelivered(Integer orderId) {
        OrderDetail orderDetail = orderDetailDao.findById(orderId).get();

        if(orderDetail != null) {
            orderDetail.setOrderStatus("Delivered");
            orderDetailDao.save(orderDetail);
        }

    }

    public TransactionDetails createTransaction(Double amount) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount", (amount * 100) );
            jsonObject.put("currency", CURRENCY);

            RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);

            Order order = razorpayClient.orders.create(jsonObject);

            TransactionDetails transactionDetails =  prepareTransactionDetails(order);
            return transactionDetails;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private TransactionDetails prepareTransactionDetails(Order order) {
        String orderId = order.get("id");
        String currency = order.get("currency");
        Integer amount = order.get("amount");

        TransactionDetails transactionDetails = new TransactionDetails(orderId, currency, amount, KEY);
        return transactionDetails;
    }
}
