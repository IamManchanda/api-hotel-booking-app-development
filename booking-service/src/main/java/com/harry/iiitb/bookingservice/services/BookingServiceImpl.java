package com.harry.iiitb.bookingservice.services;

import com.harry.iiitb.bookingservice.dao.BookingDAO;
import com.harry.iiitb.bookingservice.dto.BookingRequestDTO;
import com.harry.iiitb.bookingservice.dto.BookingDTO;
import com.harry.iiitb.bookingservice.dto.TransactionRequestDTO;
import com.harry.iiitb.bookingservice.entities.BookingInfoEntity;
import com.harry.iiitb.bookingservice.entities.TransactionDetailsEntity;
import com.harry.iiitb.bookingservice.producers.KafkaMessageProducer;
import com.harry.iiitb.bookingservice.utils.MapperUtil;

import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookingServiceImpl implements BookingService {
        @Value("${paymentService.transactionUrl}")
        private String transactionUrlForPaymentService;

        @Value("${notificationService.sentKafkaTopic}")
        private String sentKafkaTopicToNotificationService;

        @Autowired
        private BookingDAO bookingDAO;

        @Autowired
        private RestTemplate restTemplate;

        @Autowired
        private KafkaMessageProducer kafkaMessageProducer;

        @Override
        public BookingDTO createBooking(BookingRequestDTO bookingRequestDTO) {
                BookingInfoEntity createdBookingInfo = BookingInfoEntity
                                .fromBookingRequestDTO(bookingRequestDTO);

                BookingInfoEntity savedBookingInfo = bookingDAO.save(createdBookingInfo);

                return MapperUtil.mapBookingInfoToDTO(savedBookingInfo);
        }

        @Override
        public BookingDTO createBookingTransaction(TransactionRequestDTO transactionRequestDTO) throws IOException {
                int bookingIdValidate = transactionRequestDTO.getBookingId();
                Optional<BookingInfoEntity> existingBookingInfoById = bookingDAO.findById(bookingIdValidate);

                if (existingBookingInfoById.isEmpty()) {
                        throw new IllegalArgumentException(
                                        "Invalid Booking Id ");
                }

                BookingInfoEntity existingBookingInfo = existingBookingInfoById.get();

                if (existingBookingInfo.getTransactionId() != 0) {
                        throw new IllegalArgumentException(
                                        "Transaction already exists for this booking");
                }

                TransactionDetailsEntity receivedTransactionDetails = restTemplate.postForObject(
                                transactionUrlForPaymentService,
                                transactionRequestDTO,
                                TransactionDetailsEntity.class);

                if (receivedTransactionDetails == null) {
                        throw new IllegalArgumentException(
                                        "Transaction failed");
                }

                // Update the transactionId field
                int transactionId = receivedTransactionDetails.getTransactionId();
                existingBookingInfo.setTransactionId(transactionId);

                BookingInfoEntity bookingInfo = bookingDAO.save(existingBookingInfo);

                // Publish the booking confirmation message to Kafka
                String key = "Booking Id: " + bookingInfo.getBookingId();
                String message = "Booking confirmed for user with aadhaar number: "
                                + bookingInfo.getAadharNumber()
                                + "    |    " + "Here are the booking details:    " + bookingInfo.toString();
                kafkaMessageProducer.publish(sentKafkaTopicToNotificationService, key, message);

                return MapperUtil.mapBookingInfoToDTO(bookingInfo);
        }
}
