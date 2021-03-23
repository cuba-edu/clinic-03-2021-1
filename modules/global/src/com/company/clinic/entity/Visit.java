package com.company.clinic.entity;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "CLINIC_VISIT")
@Entity(name = "clinic_Visit")
@NamePattern("%s|description")
@Listeners("clinic_VisitEntityListener")
@PublishEntityChangedEvents
public class Visit extends StandardEntity {
    private static final long serialVersionUID = -8254124810105648524L;

    @Column(name = "NUMBER_")
    private Long number;

    @Lookup(type = LookupType.DROPDOWN, actions = "lookup")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PET_ID")
    private Pet pet;

    @Lookup(type = LookupType.DROPDOWN, actions = "lookup")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "VETERINARIAN_ID")
    private Veterinarian veterinarian;

    @NotNull
    @Column(name = "DATE_", nullable = false)
    private LocalDateTime date;

    @Lob
    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
    @Column(name = "HOURS_SPENT", nullable = false)
    @PositiveOrZero
    private Integer hoursSpent;

    @NotNull
    @Column(name = "AMOUNT", nullable = false)
    @PositiveOrZero
    private BigDecimal amount;

    @JoinTable(name = "CLINIC_VISIT_CONSUMABLE_LINK",
            joinColumns = @JoinColumn(name = "VISIT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CONSUMABLE_ID"))
    @ManyToMany
    private List<Consumable> consumables;

    @Transient
    @MetaProperty(related = {"date", "hoursSpent"})
    public LocalDateTime getEndDate() {
        if (date != null && hoursSpent != null) {
            return date.plusHours(hoursSpent);
        }
        return null;
    }

    @Transient
    @MetaProperty(related = {"pet", "date"})
    public String getCaption() {
        if (pet != null && date != null) {
            return pet.getName()+"["+date.getDayOfWeek()+"]";
        }
        return "";
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public List<Consumable> getConsumables() {
        return consumables;
    }

    public void setConsumables(List<Consumable> consumables) {
        this.consumables = consumables;
    }

    public Integer getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(Integer hoursSpent) {
        this.hoursSpent = hoursSpent;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Veterinarian getVeterinarian() {
        return veterinarian;
    }

    public void setVeterinarian(Veterinarian veterinarian) {
        this.veterinarian = veterinarian;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @PostConstruct
    public void postConstruct() {
        setAmount(BigDecimal.ZERO);
    }
}