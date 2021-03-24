package com.company.clinic.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseUuidEntity;

import java.math.BigDecimal;
import java.util.Date;

@MetaClass(name = "clinic_PriceHistory")
public class PriceHistory extends BaseUuidEntity {
    private static final long serialVersionUID = 7676694418842352400L;

    @MetaProperty
    private Date priceTime;

    @MetaProperty
    private BigDecimal priceValue;

    public BigDecimal getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(BigDecimal priceValue) {
        this.priceValue = priceValue;
    }

    public Date getPriceTime() {
        return priceTime;
    }

    public void setPriceTime(Date priceTime) {
        this.priceTime = priceTime;
    }
}