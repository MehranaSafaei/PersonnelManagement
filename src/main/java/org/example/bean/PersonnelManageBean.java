package org.example.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import org.example.service.PersonnelService;

@Named
@SessionScoped
public class PersonnelManageBean {

    private PersonnelService personnelService;


}
