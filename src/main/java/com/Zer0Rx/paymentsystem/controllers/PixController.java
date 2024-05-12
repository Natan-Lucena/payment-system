package com.Zer0Rx.paymentsystem.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.Zer0Rx.paymentsystem.dtos.PixChargeRequest;
import com.Zer0Rx.paymentsystem.services.PixService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





@RestController 
@RequestMapping("/pix")
public class PixController {
    @Autowired
    private PixService pixService;

    @GetMapping("/create-payment-key")
    public ResponseEntity<String> createPix(Authentication authentication) throws Exception{
      JSONObject response =  this.pixService.pixCreateEVP(authentication.getName());
       return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response.toString());
    }

    @PostMapping("/pay")
    public ResponseEntity<String> pixCreateCharge(@RequestBody PixChargeRequest pixChargeRequest, Authentication authentication) throws Exception{
       JSONObject response = this.pixService.pixCreateCharge(pixChargeRequest.valor(), authentication.getName());
       return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response.toString());

    }
    
}
