package com.Zer0Rx.paymentsystem.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.Zer0Rx.paymentsystem.services.PixService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController 
@RequestMapping("/pix")
public class PixController {
    @Autowired
    private PixService pixService;

    @GetMapping("/create")
    public ResponseEntity<JSONObject> createPix(){
       JSONObject response =  this.pixService.pixCreateEVP();
       return ResponseEntity.ok().body(response);
    }
}
