package com.company.clinic.web.screens.visit;

import com.company.clinic.entity.Consumable;
import com.company.clinic.service.VisitService;
import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.model.CollectionChangeType;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Visit;
import com.haulmont.cuba.web.gui.components.JavaScriptComponent;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.Map;

@UiController("clinic_Visit.edit")
@UiDescriptor("visit-edit.xml")
@EditedEntityContainer("visitDc")
@LoadDataBeforeShow
public class VisitEdit extends StandardEditor<Visit> {

    @Inject
    private VisitService visitService;
    @Inject
    private Logger log;
    @Inject
    private JavaScriptComponent descrJs;

    class QuillState {
        public Map<String, Object> options;
    }

    private void initJsEditor() {
        QuillState state = new QuillState();
        state.options = ParamsMap.of("theme", "snow");
        descrJs.setState(state);
    }

    @Subscribe(id = "visitDc", target = Target.DATA_CONTAINER)
    public void onVisitDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<Visit> event) {
        if ("hoursSpent".equals(event.getProperty())) {
            refreshAmount();
        }
    }

    @Subscribe(id = "consumablesDc", target = Target.DATA_CONTAINER)
    public void onConsumablesDcCollectionChange(CollectionContainer.CollectionChangeEvent<Consumable> event) {
        if (event.getChangeType() != CollectionChangeType.REFRESH) {
            refreshAmount();
        }
    }

    private void refreshAmount() {
        Visit visit = getEditedEntity();
        visit.setAmount(visitService.calculateAmount(visit));
    }

}