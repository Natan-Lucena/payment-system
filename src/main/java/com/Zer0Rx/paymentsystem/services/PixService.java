package com.Zer0Rx.paymentsystem.services;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.Zer0Rx.paymentsystem.dtos.PixChargeRequest;
import com.Zer0Rx.paymentsystem.pix.Credentials;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;

@Service
public class PixService {
    public JSONObject pixCreateEVP(){
        Credentials credentials = new Credentials();
        JSONObject options = this.buildJsonObject(credentials);


        try {
            EfiPay efi = new EfiPay(options);
            JSONObject response = efi.call("pixCreateEvp", new HashMap<String,String>(), new JSONObject());
            return response;
        }catch (EfiPayException e){
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

public JSONObject pixCreateCharge(PixChargeRequest pixChargeRequest) throws Exception{
    Credentials credentials =  new Credentials();
    JSONObject options = this.buildJsonObject(credentials);

    JSONObject body =  new JSONObject();

    body.put("calendario", new JSONObject().put("expiracao", 3600));
    body.put("devedor", new JSONObject().put("cpf", "12345678909").put("nome", "Francisco da Silva"));
    body.put("valor", new JSONObject().put("original", pixChargeRequest.valor()));
    body.put("chave", pixChargeRequest.chave());

    JSONArray infoAdicionais = new JSONArray();
    infoAdicionais.put(new JSONObject().put("nome", "Campo 1").put("valor", "Informação Adicional1 do PSP-Recebedor"));
    infoAdicionais.put(new JSONObject().put("nome", "Campo 2").put("valor", "Informação Adicional2 do PSP-Recebedor"));
    body.put("infoAdicionais", infoAdicionais);

    try{ 
        EfiPay efi = new EfiPay(options);
        JSONObject response = efi.call("pixCreateImmediateCharge", new HashMap<String,String>(), body);
        return response;
    }catch(EfiPayException e){
        System.out.println(e.getErrorDescription());
    }
    return null;
}
private JSONObject buildJsonObject(Credentials credentials){
    JSONObject options = new JSONObject();

    options.put("client_id", credentials.getClientId());
    options.put("certificate", credentials.getCertificate());
    options.put("client_secret", credentials.getClientSecret());
    options.put("sandbox", credentials.isSandbox());
    return options;
}
}
