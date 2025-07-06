package com.supermarket.repository;

import com.supermarket.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    Page<Order> findByUserId(Long userId, Pageable pageable);
    
    Page<Order> findByStatus(Order.OrderStatus status, Pageable pageable);
    
    Page<Order> findByPaymentStatus(Order.PaymentStatus paymentStatus, Pageable pageable);
    
    @Query("SELECT o FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    Page<Order> findByOrderDateBetween(@Param("startDate") LocalDateTime startDate,
                                     @Param("endDate") LocalDateTime endDate,
                                     Pageable pageable);
    
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate AND o.paymentStatus = 'PAID'")
    BigDecimal getTotalSalesBetween(@Param("startDate") LocalDateTime startDate,
                                  @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    Long getOrderCountBetween(@Param("startDate") LocalDateTime startDate,
                            @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.orderDate BETWEEN :startDate AND :endDate")
    List<Order> findUserOrdersBetween(@Param("userId") Long userId,
                                    @Param("startDate") LocalDateTime startDate,
                                    @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT c.name, SUM(oi.quantity * oi.unitPrice) as totalSales " +
           "FROM Order o JOIN o.items oi JOIN oi.product p JOIN p.category c " +
           "WHERE o.orderDate BETWEEN :startDate AND :endDate AND o.paymentStatus = 'PAID' " +
           "GROUP BY c.id, c.name " +
           "ORDER BY totalSales DESC")
    List<Object[]> getSalesByCategory(@Param("startDate") LocalDateTime startDate,
                                    @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT p.name, SUM(oi.quantity) as totalQuantity, SUM(oi.quantity * oi.unitPrice) as totalSales " +
           "FROM Order o JOIN o.items oi JOIN oi.product p " +
           "WHERE o.orderDate BETWEEN :startDate AND :endDate AND o.paymentStatus = 'PAID' " +
           "GROUP BY p.id, p.name " +
           "ORDER BY totalSales DESC")
    List<Object[]> getSalesByProduct(@Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate);
}