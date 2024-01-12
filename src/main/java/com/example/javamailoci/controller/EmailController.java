package com.example.javamailoci.controller;

import com.example.javamailoci.model.EmailModel;
import com.example.javamailoci.service.OCIEmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final OCIEmailService service;

    public EmailController(OCIEmailService service) {
        this.service = service;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailModel email) throws MessagingException, UnsupportedEncodingException {

        try {
            service.sendMessage(email);

            return new ResponseEntity<>("e-mail sent!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error to send e-mail: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
