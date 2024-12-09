package com.harran.oturum.controller;

import com.harran.oturum.model.authority.PageUrl;
import com.harran.oturum.service.authority.PageUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
public class PageUrlController
{
    @Autowired
    private PageUrlService pageUrlService;//pageUrl servise buraya atamasını yap

    //Aktif tüm grupları get ile getir.
    @GetMapping("pageUrls")
    public ResponseEntity<Iterable<PageUrl>> getAllPageUrls(){
        return ResponseEntity.ok(pageUrlService.getAllPageUrls());
    }
    //verilen grup id göre grup bilgilerini getir.
    @GetMapping("pageUrl/{pageUrlId}")
    public ResponseEntity<PageUrl> getPageUrlById(@PathVariable("pageUrlId") Integer id){
        PageUrl oldPageUrl = pageUrlService.getPageUrlById(id);
        if(oldPageUrl != null){
            return ResponseEntity.ok(oldPageUrl);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Yeni bir grup eklemek için bilgileri yollanır.
    @PostMapping("pageUrl")
    public ResponseEntity<PageUrl> createPageUrl(@RequestBody PageUrl pageUrl){
        PageUrl newPageUrl = new PageUrl();
        newPageUrl.setName(pageUrl.getName());
        newPageUrl.setDescription(pageUrl.getDescription());
        return ResponseEntity.ok(pageUrlService.createPageUrl(newPageUrl));
    }
    //Tüm grup içinde grup adı ve açıklaması araması yapılıyor
    @GetMapping("search_pageUrls/{searchText}")
    public ResponseEntity<Iterable<PageUrl>> searchPageUrl(@PathVariable("searchText") String searchText){
        if(!Objects.equals(searchText, "")){
            return ResponseEntity.ok(pageUrlService.getPageUrlsByName(searchText,searchText));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Belirlenen grup id göre grup bilgisi getiriliyor
    @PutMapping("pageUrl/{pageUrlId}")
    public ResponseEntity<PageUrl> updatePageUrl(@PathVariable("pageUrlId") Integer id, @RequestBody PageUrl pageUrl){
        PageUrl oldPageUrl = pageUrlService.getPageUrlById(id);
        if(oldPageUrl != null){
            return ResponseEntity.ok(pageUrlService.updatePageUrl(oldPageUrl, pageUrl));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Grup bilgisi silme işlemi grubu pasif hale getirilerek yapılmaktadır.
    @DeleteMapping("pageUrl/{pageUrlId}")
    public ResponseEntity<PageUrl> deletePageUrl(@PathVariable("pageUrlId") Integer id){
        PageUrl oldPageUrl = pageUrlService.getPageUrlById(id);
        if(oldPageUrl != null){
            return ResponseEntity.ok(pageUrlService.deletePageUrl(oldPageUrl));
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
