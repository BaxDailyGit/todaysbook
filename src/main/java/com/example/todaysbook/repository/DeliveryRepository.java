package com.example.todaysbook.repository;

import com.example.todaysbook.domain.dto.DeliveryDto;
import com.example.todaysbook.domain.entity.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    @Query("select new com.example.todaysbook.domain.dto.DeliveryDto(o.id, d.id, o.userId, d.status) " +
            "from Orders o " +
            "inner join Delivery d " +
            "on o.deliveryId=d.id " +
            "order by " +
            "CASE " +
            "WHEN d.status = '배송 준비중' THEN 1 " +
            "WHEN d.status = '배송중' THEN 2 " +
            "WHEN d.status = '배송완료' THEN 3 " +
            "ELSE 4 " +
            "END ASC , o.id desc ")
    Page<DeliveryDto> findAllDelivery(Pageable pageable);

    @Query("select new com.example.todaysbook.domain.dto.DeliveryDto(o.id, d.id, o.userId, d.status) " +
            "from Orders o " +
            "inner join Delivery d " +
            "on o.deliveryId=d.id " +
            "where CAST(o.id as string) like %:keyword% " +
            "order by " +
            "CASE " +
            "WHEN d.status = '배송 준비중' THEN 1 " +
            "WHEN d.status = '배송중' THEN 2 " +
            "WHEN d.status = '배송완료' THEN 3 " +
            "ELSE 4 " +
            "END ASC")
    Page<DeliveryDto> findDeliveryByKeyword(String keyword, Pageable pageable);

    @Query("select new com.example.todaysbook.domain.dto.DeliveryDto(o.id, d.id, o.userId, d.status) " +
            "from Orders o " +
            "inner join Delivery d " +
            "on o.deliveryId=d.id " +
            "where d.id=:id ")
    Optional<DeliveryDto> findByIdWithUser(String id);

    Optional<Delivery> findById(String id);
}