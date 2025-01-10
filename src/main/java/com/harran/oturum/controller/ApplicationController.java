package com.harran.oturum.controller;

import com.harran.oturum.model.authority.Application;
import com.harran.oturum.service.authority.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
public class ApplicationController
{
    @Autowired
    private ApplicationService applicationService;//application servise buraya atamasını yap

    //Aktif tüm grupları get ile getir.
    @GetMapping("applications")
    public ResponseEntity<Iterable<Application>> getAllApplications(){
        return ResponseEntity.ok(applicationService.getAllApplications());
    }
    //verilen grup id göre grup bilgilerini getir.
    @GetMapping("application/{applicationId}")
    public ResponseEntity<Application> getApplicationById(@PathVariable("applicationId") Integer id){
        Application oldApplication = applicationService.getApplicationById(id);
        if(oldApplication != null){
            return ResponseEntity.ok(oldApplication);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Yeni bir grup eklemek için bilgileri yollanır.
    @PostMapping("application")
    public ResponseEntity<Application> createApplication(@RequestBody Application application){
        //Application newApplication = new Application();
        //newApplication.setName(application.getName());
        //newApplication.setDescription(application.getDescription());
        return ResponseEntity.ok(applicationService.createApplication(application));
    }
    //Tüm grup içinde grup adı ve açıklaması araması yapılıyor
    @GetMapping("search_applications/{searchText}")
    public ResponseEntity<Iterable<Application>> searchApplication(@PathVariable("searchText") String searchText){
        if(!Objects.equals(searchText, "")){
            return ResponseEntity.ok(applicationService.getApplicationsByName(searchText,searchText));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Belirlenen grup id göre grup bilgisi getiriliyor
    @PutMapping("application/{applicationId}")
    public ResponseEntity<Application> updateApplication(@PathVariable("applicationId") Integer id, @RequestBody Application application){
        Application oldApplication = applicationService.getApplicationById(id);
        if(oldApplication != null){
            return ResponseEntity.ok(applicationService.updateApplication(oldApplication, application));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Grup bilgisi silme işlemi grubu pasif hale getirilerek yapılmaktadır.
    @DeleteMapping("application/{applicationId}")
    public ResponseEntity<Application> deleteApplication(@PathVariable("applicationId") Integer id){
        Application oldApplication = applicationService.getApplicationById(id);
        if(oldApplication != null){
            return ResponseEntity.ok(applicationService.deleteApplication(oldApplication));
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
