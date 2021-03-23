package com.company.clinic.web.screens.main;

import com.company.clinic.entity.Pet;
import com.company.clinic.entity.Visit;
import com.company.clinic.service.VetService;
import com.company.clinic.web.screens.visit.VisitEdit;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.global.UserSession;
import com.haulmont.cuba.web.app.main.MainScreen;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Date;


@UiController("clinicMainScreen")
@UiDescriptor("clinic-main-screen.xml")
@LoadDataBeforeShow
public class ClinicMainScreen extends MainScreen {

    @Inject
    private CollectionLoader<Visit> visitsDl;
    @Inject
    private DataContext dataContext;
    @Inject
    private DateField<LocalDateTime> dateSelector;
    @Inject
    private LookupField<Pet> petLookup;
    @Inject
    private VetService vetService;
    @Inject
    private UserSession userSession;
    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;

    @Subscribe("refreshAction")
    public void onRefreshAction(Action.ActionPerformedEvent event) {
        visitsDl.load();
    }

    @Subscribe("scheduleAction")
    public void onScheduleAction(Action.ActionPerformedEvent event) {
        Visit visit = dataManager.create(Visit.class);

        visit.setDate(dateSelector.getValue());
        visit.setPet(petLookup.getValue());
        visit.setDescription("No description");
        visit.setHoursSpent(1);
        visit.setVeterinarian(vetService.findVetByUser(userSession.getUser()));

        dataManager.commit(visit);

        dateSelector.setValue(null);
        petLookup.setValue(null);
        visitsDl.load();
    }

    @Subscribe("visitsCalendar")
    public void onVisitsCalendarCalendarEventClick(Calendar.CalendarEventClickEvent<LocalDateTime> event) {
        Visit visit = (Visit) event.getEntity();

        if (visit == null) return;

        VisitEdit screen = screenBuilders.editor(Visit.class, this)
                .withScreenClass(VisitEdit.class)
                .editEntity(visit)
                .withOpenMode(OpenMode.DIALOG)
                .build();

        screen.addAfterCloseListener(closeEvent -> {
            if (closeEvent.getCloseAction() == WINDOW_COMMIT_AND_CLOSE_ACTION) {
                visitsDl.load();
            }
        });

        screen.show();
    }
}