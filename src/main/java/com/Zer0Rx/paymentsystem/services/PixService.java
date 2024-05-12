package com.Zer0Rx.paymentsystem.services;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Zer0Rx.paymentsystem.config.exceptions.ErrorCreatingKey;
import com.Zer0Rx.paymentsystem.config.exceptions.ErrorCreatingPayment;
import com.Zer0Rx.paymentsystem.config.exceptions.UserAlreadyHavePaymentKeyException;
import com.Zer0Rx.paymentsystem.config.exceptions.UserDoesNotHavePaymentKey;
import com.Zer0Rx.paymentsystem.entities.User;
import com.Zer0Rx.paymentsystem.pix.Credentials;
import com.Zer0Rx.paymentsystem.repositories.UserRepository;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;

@Service
public class PixService {

    @Autowired
    private UserRepository userRepository;

    public JSONObject pixCreateEVP(String email) throws Exception{
        JSONObject options = this.buildJsonObject();
        User user = this.userRepository.findByEmail(email);
        if(user.getPaymentKey() != null){
            throw new UserAlreadyHavePaymentKeyException();
        }
        try {
            EfiPay efi = new EfiPay(options);
            JSONObject response = efi.call("pixCreateEvp", new HashMap<String,String>(), new JSONObject());
            user.setPaymentKey(response.getString("chave"));
            this.userRepository.save(user);
            return response;
        }catch (EfiPayException e){
            throw new ErrorCreatingKey();
        }
        
    }

public JSONObject pixCreateCharge(String valor, String email) throws Exception{

    User user = this.userRepository.findByEmail(email);
    if(user.getPaymentKey() == null){
        throw new UserDoesNotHavePaymentKey();
    }
    JSONObject options = this.buildJsonObject();

    JSONObject body =  new JSONObject();

    body.put("calendario", new JSONObject().put("expiracao", 3600));
    body.put("devedor", new JSONObject().put("cpf", "12345678909").put("nome", "Francisco da Silva"));
    body.put("valor", new JSONObject().put("original", valor));
    body.put("chave", user.getPaymentKey());

    JSONArray infoAdicionais = new JSONArray();
    infoAdicionais.put(new JSONObject().put("nome", "Campo 1").put("valor", "Informação Adicional1 do PSP-Recebedor"));
    infoAdicionais.put(new JSONObject().put("nome", "Campo 2").put("valor", "Informação Adicional2 do PSP-Recebedor"));
    body.put("infoAdicionais", infoAdicionais);
    try{
        EfiPay efi = new EfiPay(options);
        JSONObject response = efi.call("pixCreateImmediateCharge", new HashMap<String,String>(), body);
        int chargeId =  response.getJSONObject("loc").getInt("id");
        Map<String, String> params  = new HashMap<String, String>();
        params.put("id", String.valueOf(chargeId));
        JSONObject pixQrCode = efi.call("pixGenerateQRCode", params, new JSONObject());
        return pixQrCode;
    }catch(EfiPayException e){
        throw new ErrorCreatingPayment();
    }
    
}
    private JSONObject buildJsonObject(){
        Credentials credentials = new Credentials();
        JSONObject options = new JSONObject();

        options.put("client_id", credentials.getClientId());
        options.put("certificate", credentials.getCertificate());
        options.put("client_secret", credentials.getClientSecret());
        options.put("sandbox", credentials.isSandbox());
        return options;
    }
}
