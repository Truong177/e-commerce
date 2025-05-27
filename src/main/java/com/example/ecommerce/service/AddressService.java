package com.example.ecommerce.service;

import com.example.ecommerce.dto.AddressDto;
import com.example.ecommerce.dto.Response;

public interface AddressService {
    Response saveAndUpdateAddress(AddressDto addressDto);

}
