package com.company.clinic.portal.controllers;

import com.company.clinic.entity.Owner;
import com.company.clinic.portal.dto.OwnerDto;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class OwnersController {

    @Inject
    private DataManager dataManager;

    @GetMapping("editOwner")
    public String editOwner(@RequestParam("id")String id, Model model) {
        OwnerDto owner = new OwnerDto(dataManager.load(Owner.class).id(UUID.fromString(id)).view(View.LOCAL).one());
        model.addAttribute("ownerDto", owner);
        return "editOwner";
    }

    @GetMapping("ownersBrowse")
    public String ownersBrowse(Model model){
        List<OwnerDto> ownerDtoList = dataManager.load(Owner.class).view(View.LOCAL).list()
                .stream()
                .map(OwnerDto::new)
                .collect(Collectors.toList());
        model.addAttribute("ownersList", ownerDtoList);
        return "owners";
    }

    @PostMapping("saveOwner")
    public String saveOwner(@ModelAttribute OwnerDto ownerDto){
        Owner owner = dataManager.load(Owner.class).id(UUID.fromString(ownerDto.getId())).one();
        owner.setName(ownerDto.getName());
        owner.setEmail(ownerDto.getEmail());
        owner.setPhoneNumber(ownerDto.getPhone());
        dataManager.commit(owner);
        return "redirect:/ownersBrowse";
    }

}
