package com.harry.iiitb.bookingservice.dao;

import com.harry.iiitb.bookingservice.entities.BookingInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDAO extends JpaRepository<BookingInfoEntity, Integer> {
}
