//package com.springboot.app.controller;
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.tomcat.jni.Global;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RestController
//@RequestMapping("/excel")
//public class ExcelController {
////    @Autowired
////    private ExcelService excelService;
////    @Autowired
////    private HttpServletResponse response;
////
////    @RequestMapping(value = "/read/{fileName}", method = RequestMethod.GET)
////    public List read(@PathVariable String fileName) throws IOException {
////        Resource resource = new ClassPathResource(Global.DOC + File.separator + fileName + Global.SUFFIX_XLS);
////        File file = resource.getFile();
////        List<List<Object>> list = excelService.readExcel(file);
////    }
//}
