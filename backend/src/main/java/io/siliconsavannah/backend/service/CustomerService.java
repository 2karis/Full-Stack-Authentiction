package io.siliconsavannah.backend.service;

import io.siliconsavannah.backend.configuration.PasswordEncoder;
import io.siliconsavannah.backend.dto.CustomerDto;
import io.siliconsavannah.backend.dto.SignUpDto;
import io.siliconsavannah.backend.dto.UserDto;
import io.siliconsavannah.backend.enums.RoleName;
import io.siliconsavannah.backend.helper.Helper;
import io.siliconsavannah.backend.model.Customer;
import io.siliconsavannah.backend.model.Role;
import io.siliconsavannah.backend.model.User;
import io.siliconsavannah.backend.repository.CustomerRepository;
import io.siliconsavannah.backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final Helper helper;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;

    public CustomerDto createCustomer(CustomerDto dto){
        Long authenticatedUserIdParentId = helper.getAuthenticatedUserIdParentId();
        Role customerRole = roleRepository.findByName(RoleName.ROLE_CUSTOMER).orElseThrow(() -> new RuntimeException("Role not found!"));

        Customer customer = toEntity(dto, customerRole, authenticatedUserIdParentId);
        log.info("{}",customer);
        return toDto(customerRepository.save(customer));
    }

    private static CustomerDto toDto(Customer entity){
        return new CustomerDto(
                entity.getUser().getFirstname(),
                entity.getUser().getLastname(),
                entity.getUser().getEmail(),
                entity.getUser().getPhoneNumber(),
                entity.getProfile(),
                entity.getAddress(),
                entity.getCountry(),
                entity.getState(),
                entity.getCity(),
                entity.getZipCode(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.isActive());
    }

    private static Customer toEntity(CustomerDto dto, Role customerRole, Long authenticatedUserIdParentId){
        User user = User.builder()
                .firstname(dto.firstname())
                .lastname(dto.lastname())
                .email(dto.email())
                .role(customerRole)
                .parentId(authenticatedUserIdParentId)
                .build();
        // do password reset
        return  Customer.builder()
                .user(user)
                .profile(dto.profile())
                .address(dto.address())
                .country(dto.country())
                .state(dto.state())
                .city(dto.city())
                .zipCode(dto.zipCode())
                .startDate(dto.startDate())
                .endDate(dto.endDate())
                .isActive(dto.isActive())
                .build();
    }
}
