package org.example.main;

import org.example.entity.Leave;
import org.example.entity.Personnel;
import org.example.service.LeaveService;
import org.example.service.PersonnelService;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;



//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        sayHello("A");
        sayHello("A,B");
        sayHello("A,B,C");
        menu();
        try {
            while (running) {
                System.out.print("Please enter an option: ");
                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline left by nextInt()

                menu();
                switch (choice) {
                    case 1:
                        insert(sc); //this method for adding information
                        break;
                    case 2:
                        select(); //this method for selecting
                        break;
                    case 3:
                        update(sc); //this method for updating
                        break;
                    case 4:
                        remove(sc); //this method fo removing
                        break;
                    case 5:
                        searchByName(sc); // New method for searching by username
                        break;
                    case 6:
                        addLeave(sc);
                        break;
                    case 7:
                        viewList(sc);
                        break;
                    case 8:
                        viewLeaveListByUsername(sc);
                        break;
                    case 9:
                        System.out.println("Goodbye!");
                        sc.close();
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option, please try again...");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void sayHello(String... name) {
        for (int i = 0; i < name.length; i++) {
            System.out.println("hello" + name[i]);
        }
    }

    public static void menu() {
        System.out.println("*** Menu ***");
        System.out.println("1) Add");
        System.out.println("2) Select Personnel List");
        System.out.println("3) Update Personnel");
        System.out.println("4) Remove Personnel");
        System.out.println("5) Search by Name");
        System.out.println("6) Add Leave");
        System.out.println("7) view Leave List");
        System.out.println("8) view List By username");
        System.out.println("9) Exit");
    }

    public static Optional<Personnel> insert(Scanner sc) throws SQLException {
        PersonnelService personnelService = new PersonnelService();
        System.out.println("** Enter Information **");
        System.out.print("Enter your username: ");
        String userName = sc.nextLine();
        System.out.print("Enter your mobile number: ");
        String mobile = sc.nextLine();
        System.out.print("Enter your personnel code: ");
        int personnelCode = sc.nextInt();
        System.out.print("Enter your email: ");
        String email = sc.next();

        Personnel personnel = new Personnel();
        personnel.setUserName(userName);
        personnel.setMobile(mobile);
        personnel.setEmail(email);
        personnel.setPersonnelCode((long) personnelCode);
        System.out.println("Your information has been saved: " + userName + " - " + mobile + " - " + personnelCode + " - " + email);
        return personnelService.createPersonnel(personnel);
    }

    public static List<Personnel> select() throws SQLException {
        PersonnelService personnelService = new PersonnelService();
        List<Personnel> personnelList = personnelService.getListPersonnel();
        System.out.println("    Username | Mobile | Personnel Code");
        System.out.println("--------------------------------------");
        for (Personnel personnel : personnelList) {
            System.out.println(personnel.getUserName().trim() + " - " + personnel.getMobile() + " - " + personnel.getPersonnelCode());
        }
        return personnelList;
    }

    public static Personnel update(Scanner sc) throws SQLException {
        PersonnelService personnelService = new PersonnelService();
        System.out.print("Enter your personnel code: ");
        long personnelCode = sc.nextLong();
        sc.nextLine();

        Personnel existingPersonnel = personnelService.findPersonnelByCode(personnelCode);
        if (existingPersonnel == null) {
            System.out.println("Personnel not found!");
            return null;
        }

        System.out.println("** Enter New Information **");
        System.out.print("Enter your username: ");
        String userName = sc.nextLine();
        System.out.print("Enter your mobile number: ");
        String mobile = sc.nextLine();
        System.out.print("enter your email: ");
        String email = sc.next();
        existingPersonnel.setUserName(userName);
        existingPersonnel.setMobile(mobile);
        existingPersonnel.setEmail(email);

        Personnel updatedPersonnel = personnelService.updatePersonnel(existingPersonnel);
        if (updatedPersonnel != null) {
            System.out.println("Personnel updated successfully: " + updatedPersonnel);
        } else {
            System.out.println("Failed to update personnel.");
        }

        return updatedPersonnel;
    }

    public static void remove(Scanner sc) throws SQLException {
        PersonnelService personnelService = new PersonnelService();
        System.out.print("Enter your ID: ");
        long id = sc.nextLong();
        sc.nextLine();

        Optional<Personnel> existingPersonnel = personnelService.findById(id);

        if (existingPersonnel.isPresent()) {
            personnelService.deleteById(id);
            System.out.println("Personnel with ID " + id + " has been deleted successfully.");
        } else {
            System.out.println("Personnel not found with ID " + id + "!");
        }
    }

    //  Search by Name
    public static void searchByName(Scanner sc) throws SQLException {
        PersonnelService personnelService = new PersonnelService();
        System.out.print("Enter the username to search: ");
        String name = sc.nextLine();

        List<Personnel> results = (List<Personnel>) personnelService.findPersonnelByName(name);
        if (results.isEmpty()) {
            System.out.println("No personnel found with the name: " + name);
        } else {
            for (Personnel personnel : results) {
                System.out.println("Personnel : " + personnel.getUserName().trim() + " - " + personnel.getMobile() + " - " + personnel.getPersonnelCode());
            }
        }
    }

    public static Optional<Leave> addLeave(Scanner sc) throws SQLException {
        System.out.print("Enter your personnelCode: ");
        long personnelCode = sc.nextLong();
        sc.nextLine();

        PersonnelService personnelService = new PersonnelService();
        Personnel p = personnelService.findPersonnelByCode(personnelCode);

        if (p == null) {
            System.out.println("Personnel not found.");
            return Optional.empty();
        }

        LocalDateTime loginTime = LocalDateTime.now();
        System.out.println("Login time: " + loginTime);

        System.out.println("Enter leave details:");
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDate = sc.nextLine();
        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDate = sc.nextLine();
        System.out.print("Enter description: ");
        String description = sc.nextLine();

        Leave leave = new Leave();
        leave.setStartDate(Date.valueOf(startDate).toLocalDate());
        leave.setEndDate(Date.valueOf(endDate).toLocalDate());
//        leave.setStartDate(Date.from(loginTime.atZone(ZoneId.systemDefault()).toInstant()));
//        leave.setEndDate(Date.from(loginTime.atZone(ZoneId.systemDefault()).toInstant()));
        leave.setDescription(description);
        leave.setPersonnel(p);
        leave.setLoginTime(loginTime);

        LeaveService leaveService = new LeaveService();
        System.out.println("Your information has been saved: " + startDate + " - " + endDate + " - " + description + " - " + personnelCode);
        return leaveService.insert(leave);

    }

    public static List<Leave> viewList(Scanner sc) throws SQLException {
        LeaveService leaveService = new LeaveService();
        List<Leave> leaveList = leaveService.findAll();
        System.out.println("Start Date - End Date - Description - Personnel ID - loginTime - status");
        for (Leave leave : leaveList) {
            System.out.println(leave.getStartDate() + " - " + leave.getEndDate() + " - " + leave.getDescription() + " - " + leave.getPersonnel() + " - " + leave.getLoginTime() + " - " + leave.getLeaveStatus());
        }
        return leaveList;
    }

    public static List<Leave> viewLeaveListByUsername(Scanner sc) throws SQLException {
        List<Leave> leaveList = new ArrayList<>();
        LeaveService leaveService = new LeaveService();

        System.out.print("Enter your username: ");
        String username = sc.nextLine();

        PersonnelService personnelService = new PersonnelService();
        List<Personnel> personnel = personnelService.findPersonnelByName(username);
        if (personnel.isEmpty()) {
            System.out.println("No personnel found with the username: " + username);
            return leaveList;
        }
//            if (personnel.isEmpty()) return System.out.println(STR."No personnel found with the username: \{username}"), leaveList;

        Long personnelId = personnel.getFirst().getId();
        leaveList = leaveService.findLeaveByPersonnelId(personnelId);
        if (leaveList.isEmpty()) {
            System.out.println("No leave records found for username: " + username);
        } else {
            System.out.println("Leave records for username: " + username + " - " + leaveList.size());
            for (Leave leave : leaveList) {
                System.out.println(leave);
            }
        }
//            System.out.println(leaveList.isEmpty() ? STR."No leave records found for username: \{username}" : STR."Leave records for username: \{username}");
//            if (!leaveList.isEmpty()) leaveList.forEach(System.out::println);
        return leaveList;
    }
}