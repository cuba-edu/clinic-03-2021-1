package com.company.clinic.web.screens.consumable;

import com.haulmont.charts.gui.amcharts.model.Color;
import com.haulmont.charts.gui.components.charts.SerialChart;
import com.haulmont.charts.gui.data.DataItem;
import com.haulmont.charts.gui.data.ListDataProvider;
import com.haulmont.charts.gui.data.MapDataItem;
import com.haulmont.charts.gui.data.SimpleDataItem;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Consumable;

import javax.inject.Inject;
import java.awt.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@UiController("clinic_Consumable.browse")
@UiDescriptor("consumable-browse.xml")
@LookupComponent("consumablesTable")
@LoadDataBeforeShow
public class ConsumableBrowse extends StandardLookup<Consumable> {

    @Inject
    private SerialChart consumablePrices;

    @Subscribe(id = "consumablesDc", target = Target.DATA_CONTAINER)
    public void onConsumablesDcCollectionChange(CollectionContainer.CollectionChangeEvent<Consumable> event) {

        List<DataItem> items = event.getSource().getItems()
                .stream().map(consumable ->
                    MapDataItem.of("name", consumable.getTitle(),
                            "price", consumable.getPrice(),
                            "color", Color.ORANGE.getValue()
                    ))
                .collect(Collectors.toList());

        consumablePrices.setDataProvider(new ListDataProvider(items));
    }


}