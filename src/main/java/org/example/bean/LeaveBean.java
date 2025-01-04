package org.example.bean;

import jakarta.ejb.Stateless;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.example.dao.LeaveDao;
import org.example.entity.Leave;
import org.example.service.LeaveService;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class LeaveBean implements Serializable {

    private LeaveDao leaveDao;
    private List<Leave> leave;

    @Inject
    private LeaveService leaveService;
}
