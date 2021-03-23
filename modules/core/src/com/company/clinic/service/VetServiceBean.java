package com.company.clinic.service;

import com.company.clinic.entity.Veterinarian;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.security.entity.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service(VetService.NAME)
public class VetServiceBean implements VetService {

    @Inject
    private DataManager dataManager;

    @Override
    public Veterinarian findVetByUser(User user) {
        return dataManager.load(Veterinarian.class)
                .query("select v from clinic_Veterinarian v where v.user.id = :userId")
                .parameter("userId", user.getId())
                .optional()
                .orElseThrow(IllegalArgumentException::new);
    }
}