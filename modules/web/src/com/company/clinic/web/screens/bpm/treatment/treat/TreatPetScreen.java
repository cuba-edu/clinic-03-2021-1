package com.company.clinic.web.screens.bpm.treatment.treat;

import com.company.clinic.entity.Pet;
import com.haulmont.addon.bproc.web.processform.*;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;

@UiController("clinic_TreatPetScreen")
@UiDescriptor("treat-pet-screen.xml")
@LoadDataBeforeShow
@ProcessForm (
        outcomes = {
                @Outcome(id = "complete"),
                @Outcome(id = "hasQuestions",
                    outputVariables = {
                        @OutputVariable(name = "question", type = String.class)
                    }
                )
        }
)
public class TreatPetScreen extends Screen {

    @Inject
    private ProcessFormContext processFormContext;

    @Inject
    @ProcessVariable(name = "question")
    private TextArea<String> question;

    @Inject
    @ProcessVariable(name = "pet")
    private LookupField<Pet> pet;

    @Inject
    @ProcessVariable(name = "description")
    private TextArea<String> description;

    @Subscribe("questions")
    public void onQuestions(Action.ActionPerformedEvent event) {
        processFormContext.taskCompletion()
                .withOutcome("hasQuestions")
                .addProcessVariable("question", question.getValue())
                .complete();
        closeWithDefaultAction();
    }

    @Subscribe("complete")
    public void onComplete(Action.ActionPerformedEvent event) {
        processFormContext.taskCompletion().withOutcome("complete").complete();
        closeWithDefaultAction();
    }
}