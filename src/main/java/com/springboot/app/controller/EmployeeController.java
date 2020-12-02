package com.springboot.app.controller;

import com.springboot.app.entity.Employee;
import com.springboot.app.service.EmployeeService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {

    String uploadPath = "C:/Users/hoang/uploads";
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


        List<String> results = new ArrayList<String>();

        File[] files = new File(uploadPath).listFiles();

        for (File f : files) {
            if (f.isFile()) {
                results.add(f.getName());
            }
        }
        System.out.println(results);
        model.addAttribute("fileList", results);





            try  {
                FileInputStream fileInputStream = new FileInputStream(new File(uploadPath+"/test.xlsx"));
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




//    @RequestMapping("/upload-xlsx-file/{fName}")
//    public String uploadXLSXFile(@PathVariable("fName") MultipartFile file, Model model) {
//        String n = file.getOriginalFilename();
//        System.out.println(n);
//        String path = "C:/Users/khoihn/uploads/" + n;
//        try  {
//            FileInputStream fileInputStream = new FileInputStream(new File("C:/Users/khoihn/uploads/test.xlsx"));
//            Workbook workbook = new XSSFWorkbook(fileInputStream);
//            Sheet datatypeSheet = workbook.getSheetAt(0);
//            DataFormatter fmt = new DataFormatter();
//            Iterator<Row> iterator = datatypeSheet.iterator();
//            Row firstRow = iterator.next();
//            Cell firstCell = firstRow.getCell(0);
//            System.out.println(firstCell.getStringCellValue());
//            List<Employee> listOfEmployee = new ArrayList<Employee>();
//            employeeService.deleteAllEmployee(); //Delete all before read new file
//
//            while (iterator.hasNext()) {
//                Row currentRow = iterator.next();
//                Employee employee = new Employee();
//                employee.setId(Long.parseLong(fmt.formatCellValue(currentRow.getCell(0))));
//                employee.setFirstName(currentRow.getCell(1).getStringCellValue());
//                employee.setLastName(currentRow.getCell(2).getStringCellValue());
//                listOfEmployee.add(employee);
////                    employeeService.saveEmployee(employee);
//
//                employeeService.save(employee);
//            }
//
//
//            //TODO : Save to DB
//
//            // save users list on model
//
//
//            model.addAttribute("users", listOfEmployee);
//            model.addAttribute("status", true);
//            workbook.close();
//
//
//        } catch (Exception ex) {
//            model.addAttribute("message", "An error occurred while processing the CSV file.");
//            model.addAttribute("status", false);
//        }
//
//        return "file-upload-status";
//    }


    @RequestMapping("/upload-file-list")
    public String uploadFileList(Model model) {
        List<String> results = new ArrayList<String>();

        File[] files = new File(uploadPath).listFiles();

        for (File f : files) {
            if (f.isFile()) {
                results.add(f.getName());
            }
        }
        System.out.println(results);
        model.addAttribute("fileList", results);


        return "file-list";
    }


    @RequestMapping("/employee/modify")
    public String modifyXLSXFile(Model model) {

        // validate file
//        if (file.isEmpty()) {
//            model.addAttribute("message", "Please select a XLSX file to upload.");
//            model.addAttribute("status", false);
//        } else {

        // parse CSV file to create a list of `User` objects

//        File directoryPath = new File("C:/Users/khoihn/uploads");
//
//        List<String> pathList = new ArrayList<>();
//        pathList = directoryPath.list();


        List<Employee> listOfEmployee = employeeService.getAllEmployee();
        System.out.println(listOfEmployee);

        String excelFilePath = uploadPath+ "/test.xlsx";

        try  {
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            workbook.removeSheetAt(0);
            XSSFSheet sheet = workbook.createSheet("Sheet1");

            int rowNum = 0;

            Row firstRow = sheet.createRow(rowNum++);
            Cell firstCell = firstRow.createCell(0);
            firstCell.setCellValue("id");
            Cell secondCell = firstRow.createCell(1);
            secondCell.setCellValue("first_name");
            Cell thirdCell = firstRow.createCell(2);
            thirdCell.setCellValue("last_name");

            for (Employee employee : listOfEmployee) {
                Row row = sheet.createRow(rowNum++);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(employee.getId());
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(employee.getFirstName());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(employee.getLastName());
            }

            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

            System.out.println("File modified");
        } catch (Exception ex) {
            model.addAttribute("message", "An error occurred while processing the CSV file.");
            model.addAttribute("status", false);
        }


//        try  {
//            File fold = new File(excelFilePath);
//            fold.delete();
//            File fnew = new File(excelFilePath);
//
//            System.out.println("Create file excel");
//            XSSFWorkbook workbook = new XSSFWorkbook();
//            XSSFSheet sheet = workbook.createSheet();
//            int rowNum = 0;
//
//            Row firstRow = sheet.createRow(rowNum++);
//            Cell firstCell = firstRow.createCell(0);
//            firstCell.setCellValue("id");
//            Cell secondCell = firstRow.createCell(1);
//            secondCell.setCellValue("first_name");
//            Cell thirdCell = firstRow.createCell(2);
//            thirdCell.setCellValue("last_name");
//
//            for (Employee employee : listOfEmployee) {
//                Row row = sheet.createRow(rowNum++);
//                Cell cell1 = row.createCell(0);
//                cell1.setCellValue(employee.getId());
//                Cell cell2 = row.createCell(1);
//                cell2.setCellValue(employee.getFirstName());
//                Cell cell3 = row.createCell(2);
//                cell3.setCellValue(employee.getLastName());
//            }
//
//            FileOutputStream outputStream = new FileOutputStream(fnew);
//            workbook.write(outputStream);
//            workbook.close();
//            outputStream.close();
//
//            System.out.println("File modified");
//        } catch (Exception ex) {
//            model.addAttribute("message", "An error occurred while processing the CSV file.");
//            model.addAttribute("status", false);
//        }

        return "redirect:/readAll";
    }


    @RequestMapping("/readAll")
    public String index(Model model) {
        List<Employee> employees = employeeService.getAllEmployee();
        model.addAttribute("users", employees);

        return "file-upload-status";
    }

    @RequestMapping(value = "/employee/save", method = RequestMethod.POST)
    public String saveEmployee(Employee employee) {
        employeeService.save(employee);
        return "redirect:/readAll";
    }

    @RequestMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployeeById(id);
        return "redirect:/readAll";
    }


    @RequestMapping("/employee/add")
    public String addEmployee(Model model) {
        model.addAttribute("user", new Employee());
        return "addEmployee";
    }

    @RequestMapping(value = "/employee/edit/{id}", method = RequestMethod.GET)
    public String editEmployee(@PathVariable("id") Long id, Model model) {
        Optional<Employee> employeeEdit = employeeService.findEmployeeById(id);
        employeeEdit.ifPresent(employee -> model.addAttribute("user", employee));
//        model.addAttribute("user", employeeEdit.get());
        return "editEmployee";
    }

}
