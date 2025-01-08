package com.whatsapp.SpringDemo.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisResponse {

    private String message;       // Success or error message
    private int statusCode;       // HTTP
}
