package com.example.webshopspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdateCommand {
    @NotNull
    @Size(min = 4, message = "Túl rövid a név")
    private String customerName;
}

