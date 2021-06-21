package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.exceptions.UserServiceException;
import com.appsdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.io.repository.AddressRepository;
import com.appsdeveloperblog.app.ws.io.repository.UserRepository;
import com.appsdeveloperblog.app.ws.service.AddressService;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDto;
import com.appsdeveloperblog.app.ws.ui.model.response.AddressesRest;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<AddressDto> getAddresses(String userId) {

        List<AddressDto> returnedValue = new ArrayList<>();

        UserEntity userEntity = userRepository.findByUserId(userId);

        List<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);

        if (addresses != null && !addresses.isEmpty()) {
            java.lang.reflect.Type listType = new TypeToken<List<AddressDto>>() {
            }.getType();
            returnedValue = new ModelMapper().map(addresses, listType);
        }

        return returnedValue;
    }

    @Override
    public AddressDto getAddressesById(String id) {
        AddressEntity addressEntity = addressRepository.findByAddressId(id);
        if (addressEntity == null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(addressEntity, AddressDto.class);
    }
}
