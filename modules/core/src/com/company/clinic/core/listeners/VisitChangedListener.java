package com.company.clinic.core.listeners;

import com.company.clinic.entity.Visit;
import com.haulmont.cuba.core.app.events.AttributeChanges;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.DataManager;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.inject.Inject;
import java.util.UUID;

@Component("clinic_VisitChangedListener")
public class VisitChangedListener {

    @Inject
    private DataManager dataManager;
    @Inject
    private Logger log;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void visitChanged(EntityChangedEvent<Visit, UUID> event) {
        AttributeChanges changes = event.getChanges();
        if (changes.isChanged("date")) {
            Id<Visit, UUID> entityId = event.getEntityId();
            Visit visit = dataManager.load(entityId).view("visit-email").one();
            sendEmail(visit.getPet().getOwner().getEmail());
        }
    }

    private void sendEmail(String email) {
        log.info(String.format("Sending email to %s", email));
    }

}
