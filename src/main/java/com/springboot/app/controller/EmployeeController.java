package com.springboot.app.controller;

import com.springboot.app.entity.Employee;
import com.springboot.app.service.EmployeeService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {
    @Autowired private EmployeeService employeeService;

    @GetMapping("/url")
    public String index() {

        return "upload-one-file";
    }

    //Read excel
    @PostMapping("/upload-xlsx-file")
    public String uploadXLSXFile(@RequestParam("file") MultipartFile file, Model model) {

        // validate file
//        if (file.isEmpty()) {
//            model.addAttribute("message", "Please select a XLSX file to upload.");
//            model.addAttribute("status", false);
//        } else {

            // parse CSV file to create a list of `User` objects


        File directoryPath = new File("C:/Users/khoihn/uploads");

        List<String> pathList = new ArrayList<>();
//        pathList = directoryPath.list();

            try  {
                FileInputStream fileInputStream = new FileInputStream(new File("C:/Users/khoihn/uploads/test.xlsx"));
                Workbook workbook = new XSSFWorkbook(fileInputStream);
                Sheet datatypeSheet = workbook.getSheetAt(0);
                DataFormatter fmt = new DataFormatter();
                Iterator<Row> iterator = datatypeSheet.iterator();
                Row firstRow = iterator.next();
                Cell firstCell = firstRow.getCell(0);
                System.out.println(firstCell.getStringCellValue());
                List<Employee> listOfEmployee = new ArrayList<Employee>();
                employeeService.deleteAllEmployee(); //Delete all before read new file

                while (iterator.hasNext()) {
                    Row currentRow = iterator.next();
                    Employee employee = new Employee();
                    employee.setId(Long.parseLong(fmt.formatCellValue(currentRow.getCell(0))));
                    employee.setFirstName(currentRow.getCell(1).getStringCellValue());
                    employee.setLastName(currentRow.getCell(2).getStringCellValue());
                    listOfEmployee.add(employee);
//                    employeeService.saveEmployee(employee);

                    employeeService.save(employee);
                }


                //TODO : Save to DB

                // save users list on model


                model.addAttribute("users", listOfEmployee);
                model.addAttribute("status", true);
                workbook.close();


            } catch (Exception ex) {
                model.addAttribute("message", "An error occurred while processing the CSV file.");
                model.addAttribute("status", false);
            }


        return "file-upload-status";
    }


    @PostMapping("/employee/modify")
    public String modifyXLSXFile(@RequestParam("file") MultipartFile file, Model model) {

        // validate file
//        if (file.isEmpty()) {
//            model.addAttribute("message", "Please select a XLSX file to upload.");
//            model.addAttribute("status", false);
//        } else {

        // parse CSV file to create a list of `User` objects


        File directoryPath = new File("C:/Users/khoihn/uploads");

        List<String> pathList = new ArrayList<>();
//        pathList = directoryPath.list();

        try  {
            FileInputStream fileInputStream = new FileInputStream(new File("C:/Users/khoihn/uploads/test.xlsx"));
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            DataFormatter fmt = new DataFormatter();
            Iterator<Row> iterator = datatypeSheet.iterator();
            Row firstRow = iterator.next();
            Cell firstCell = firstRow.getCell(0);
            System.out.println(firstCell.getStringCellValue());
            List<Employee> listOfEmployee = new ArrayList<Employee>();
            employeeService.deleteAllEmployee(); //Delete all before read new file

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Employee employee = new Employee();
                employee.setId(Long.parseLong(fmt.formatCellValue(currentRow.getCell(0))));
                employee.setFirstName(currentRow.getCell(1).getStringCellValue());
                employee.setLastName(currentRow.getCell(2).getStringCellValue());
                listOfEmployee.add(employee);
//                    employeeService.saveEmployee(employee);

                employeeService.save(employee);
            }


            //TODO : Save to DB

            // save users list on model


            model.addAttribute("users", listOfEmployee);
            model.addAttribute("status", true);
            workbook.close();


        } catch (Exception ex) {
            model.addAttribute("message", "An error occurred while processing the CSV file.");
            model.addAttribute("status", false);
        }


        return "file-upload-status";
    }



    @RequestMapping("/upload-xlsx-file")
    public String index(Model model) {
        List<Employee> users = employeeService.getAllEmployee();

        model.addAttribute("users", users);

        return "file-upload-status";
    }

    @RequestMapping(value = "/employee/save", method = RequestMethod.POST)
    public String saveEmployee(Employee employee) {
        employeeService.save(employee);
        return "redirect:/upload-xlsx-file";
    }

    @RequestMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return "redirect:/upload-xlsx-file";
    }


    @RequestMapping("/employee/add")
    public String addEmployee(Model model) {
        model.addAttribute("user", new Employee());
        return "addEmployee";
    }

    @RequestMapping(value = "/employee/edit/{id}", method = RequestMethod.GET)
    public String editEmployee(@PathVariable Long id, Model model) {
        Optional<Employee> employeeEdit = employeeService.findEmployeeById(id);
        employeeEdit.ifPresent(employee -> model.addAttribute("user", employee));
//        model.addAttribute("user", employeeEdit.get());
        return "editEmployee";
    }

}
