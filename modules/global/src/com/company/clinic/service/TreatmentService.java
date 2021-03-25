package com.company.clinic.service;

import com.company.clinic.entity.Pet;

public interface TreatmentService {
    String NAME = "clinic_TreatmentService";

    void completeTreatment(Pet pet);

}