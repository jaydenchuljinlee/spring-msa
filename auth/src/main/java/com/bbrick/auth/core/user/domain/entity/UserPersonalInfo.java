package com.bbrick.auth.core.user.domain.entity;

import com.bbrick.auth.core.user.domain.exceptions.UserDomainValueException;
import com.bbrick.auth.comn.entity.BaseEntity;
import com.bbrick.auth.comn.validation.checker.BirthDateFormatChecker;
import com.bbrick.auth.comn.validation.checker.PhoneNumberFormatChecker;
import com.bbrick.auth.comn.validation.checker.UserNameFormatChecker;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name ="user_personal_info")
public class UserPersonalInfo extends BaseEntity {

    @Column(name ="user_name")
    private String userName;

    @Column(name ="birth_date")
    private String birthDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name ="gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded
    private Address address;

    @Builder
    private UserPersonalInfo(String userName, String phoneNumber, String birthDate, Gender gender, Address address) {

        this.validateAndSetName(userName);
        this.validateAndSetPhoneNumber(phoneNumber);
        this.validateAndSetBirthDate(birthDate);

        this.gender = gender;
        this.address = address;
    }

    private void validateAndSetName(String userName) {
        if (!UserNameFormatChecker.check(userName)) {
            throw new UserDomainValueException("User.userName", userName);
        }

        this.userName = userName;
    }

    private void validateAndSetPhoneNumber(String phoneNumber) {
        if (!PhoneNumberFormatChecker.check(phoneNumber)) {
            throw new UserDomainValueException("User.phoneNumber", phoneNumber);
        }

        this.phoneNumber = phoneNumber;
    }

    private void validateAndSetBirthDate(String birthDate) {
        if (!BirthDateFormatChecker.check(birthDate)) {
            throw new UserDomainValueException("User.birthDate", birthDate);
        }

        this.birthDate = birthDate;
    }
}
