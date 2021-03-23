package com.company.clinic.service;

import com.company.clinic.entity.Veterinarian;
import com.haulmont.cuba.security.entity.User;

public interface VetService {
    String NAME = "clinic_VetService";

    Veterinarian findVetByUser(User user);
}