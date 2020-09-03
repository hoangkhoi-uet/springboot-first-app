//package com.springboot.app.controller;
//
//import com.springboot.app.entity.Employee;
//import com.springboot.app.service.EmployeeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//public class EmployeeController {
//    @Autowired
//    private EmployeeService employeeService;
//
//    @RequestMapping("/")
//    public String index(Model model) {
//        List<Employee> employees = employeeService.getAllEmployee();
//
//        model.addAttribute("employees", employees);
//
//        return "employee/list";
//    }
//
//    @RequestMapping(value = "add")
//    public String addEmployee(Model model) {
//        model.addAttribute("employee", new Employee());
//        return "addEmployee";
//    }
//
//    @RequestMapping(value = "/edit", method = RequestMethod.GET)
//    public String editEmployee(@RequestParam("id") Long employeeId, Model model) {
//        Optional<Employee> employeeEdit = employeeService.findEmployeeById(employeeId);
//        employeeEdit.ifPresent(employee -> model.addAttribute("employee", employee));
//        return "editEmployee";
//    }
//
//    @RequestMapping(value = "save", method = RequestMethod.POST)
//    public String save(Employee employee) {
//        employeeService.saveEmployee(employee);
//        return "redirect:/";
//    }
//
//    @RequestMapping(value = "/delete", method = RequestMethod.GET)
//    public String deleteEmployee(@RequestParam("id") Long employeeId, Model model) {
//        employeeService.deleteEmployee(employeeId);
//        return "redirect:/";
//    }
//
//
//    private String fileLocation;
//
//    @PostMapping("/uploadExcelFile")
//    public String uploadFile(Model model, MultipartFile file) throws IOException {
//        InputStream in = file.getInputStream();
//        File currDir = new File(".");
//        String path = currDir.getAbsolutePath();
//        fileLocation = path.substring(0, path.length() - 1) + file.getOriginalFilename();
//        FileOutputStream f = new FileOutputStream(fileLocation);
//        int ch = 0;
//        while ((ch = in.read()) != -1) {
//            f.write(ch);
//        }
//        f.flush();
//        f.close();
//        model.addAttribute("message", "File: " + file.getOriginalFilename()
//                + " has been uploaded successfully!");
//        return "excel";
//    }
//
//
//}
