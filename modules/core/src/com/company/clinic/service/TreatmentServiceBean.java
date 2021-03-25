package com.company.clinic.service;

import com.company.clinic.entity.Pet;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service(TreatmentService.NAME)
public class TreatmentServiceBean implements TreatmentService {

    @Inject
    private Logger log;

    @Override
    public void completeTreatment(Pet pet) {
        log.info(String.format("Treatment for the pet: %s is completed", pet.getName()));
    }
}