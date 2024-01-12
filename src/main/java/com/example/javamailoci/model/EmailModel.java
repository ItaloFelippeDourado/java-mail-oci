package com.example.javamailoci.model;

import lombok.Builder;
import lombok.Data;

/**
 * Model of internal email object.
 * It is adaptable to your application.
 */
@Data
@Builder
public class EmailModel {

    private String subject;
    private String sender;
    private String senderName;
    private String recipient;
    private String content;

}
