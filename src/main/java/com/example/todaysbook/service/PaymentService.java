package com.example.todaysbook.service;

import com.example.todaysbook.domain.dto.PaymentAddressAndMileageInfo;
import com.example.todaysbook.domain.dto.PaymentBookInfoDto;

import java.util.List;

public interface PaymentService {
    void createOrder(long userId, List<PaymentBookInfoDto> bookDtoList, PaymentAddressAndMileageInfo addressAndMileageInfo);
    void subtractStock(List<PaymentBookInfoDto> books);
}
