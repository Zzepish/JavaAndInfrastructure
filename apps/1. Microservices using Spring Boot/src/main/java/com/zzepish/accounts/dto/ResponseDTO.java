package com.zzepish.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResponseDTO {
    private String code;
    private String message;
    private LocalDateTime timestamp;
}
