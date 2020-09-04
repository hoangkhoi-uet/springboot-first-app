package com.springboot.app.controller;

import com.springboot.app.commons.FileResponse;
import com.springboot.app.entity.Employee;
import com.springboot.app.storage.StorageService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FileController {

    private StorageService storageService;

    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/url")
    public String listAllFiles(Model model) {

        model.addAttribute("files", storageService.loadAll().map(
                path -> ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/download/")
                        .path(path.getFileName().toString())
                        .toUriString())
                .collect(Collectors.toList()));

        return "listFiles";
    }

    @GetMapping("/download/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource resource = storageService.loadAsResource(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/upload-file")
    @ResponseBody
    public FileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String name = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(name)
                .toUriString();

        return new FileResponse(name, uri, file.getContentType(), file.getSize());
    }

    @PostMapping("/upload-multiple-files")
    @ResponseBody
    public List<FileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload-xlsx-file")
    public String uploadXLSXFile(@RequestParam("file") MultipartFile file, Model model) {

        // validate file
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a XLSX file to upload.");
            model.addAttribute("status", false);
        } else {

            // parse CSV file to create a list of `User` objects
            try  {
                Workbook workbook = new XSSFWorkbook(file.getInputStream());
                Sheet datatypeSheet = workbook.getSheetAt(0);
                DataFormatter fmt = new DataFormatter();
                Iterator<Row> iterator = datatypeSheet.iterator();
                Row firstRow = iterator.next();
                Cell firstCell = firstRow.getCell(0);
                System.out.println(firstCell.getStringCellValue());
                List<Employee> listOfEmployee = new ArrayList<Employee>();
                while (iterator.hasNext()) {
                    Row currentRow = iterator.next();
                    Employee employee = new Employee();
                    employee.setId(Integer.parseInt(fmt.formatCellValue(currentRow.getCell(0))));
                    employee.setFirstName(currentRow.getCell(1).getStringCellValue());
                    employee.setLastName(currentRow.getCell(2).getStringCellValue());
                    listOfEmployee.add(employee);
                }
                // save users list on model


                model.addAttribute("users", listOfEmployee);
                model.addAttribute("status", true);
                workbook.close();


            } catch (Exception ex) {
                model.addAttribute("message", "An error occurred while processing the CSV file.");
                model.addAttribute("status", false);
            }
        }

        return "file-upload-status";
    }
}
