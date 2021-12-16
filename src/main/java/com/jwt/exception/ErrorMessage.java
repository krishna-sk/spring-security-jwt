package com.jwt.exception;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ErrorMessage {

    private Date timestamp;

    private Integer status;

    private List<String> message;

}
