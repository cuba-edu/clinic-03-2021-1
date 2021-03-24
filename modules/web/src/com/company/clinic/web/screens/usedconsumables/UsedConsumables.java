package com.company.clinic.web.screens.usedconsumables;

import com.company.clinic.entity.Consumable;
import com.company.clinic.service.ConsumablesService;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.reports.app.service.ReportService;
import com.haulmont.reports.entity.Report;
import com.haulmont.reports.gui.ReportGuiManager;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@UiController("clinic_UsedConsumables")
@UiDescriptor("used-consumables.xml")
@LoadDataBeforeShow
public class UsedConsumables extends Screen {

    @Inject
    private CollectionContainer<Consumable> consumablesDc;

    @Inject
    private ConsumablesService consumablesService;

    @Inject
    private DataManager dataManager;

    @Inject
    private ReportGuiManager reportGuiManager;

/*
    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        LoadContext<Consumable> loadContext = new LoadContext<>(Consumable.class)
                .setQuery(new LoadContext.Query("select distinct c from clinic_Visit v join v.consumables c " +
                        "where @between(c.createTs, now-7, now+1, day)")).setView(View.LOCAL);
        List<Consumable> consumables = dataManager.loadList(loadContext);
        consumablesDc.setItems(consumables);
    }
*/

    private void loadByService() {
        List<Consumable> consumables = consumablesService.getUsedConsumables();
        consumablesDc.setItems(consumables);
    }

    @Install(to = "consumablesDl", target = Target.DATA_LOADER)
    private List<Consumable> consumablesDlLoadDelegate(LoadContext<Consumable> loadContext) {
        return consumablesService.getUsedConsumables();
    }

    @Subscribe("runPriceList")
    public void onConsumablesTableRunPriceList(Action.ActionPerformedEvent event) {
        Report report = dataManager.load(Report.class)
                .view(ReportService.MAIN_VIEW_NAME)
                .list().stream()
                .filter(r -> "pricelist".equals(r.getCode())).findAny().orElse(null);

        reportGuiManager.runReport(report, this);
    }

}