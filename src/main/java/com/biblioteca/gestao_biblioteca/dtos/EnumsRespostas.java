package com.biblioteca.gestao_biblioteca.dtos;

import lombok.Data;

@Data
public class EnumsRespostas {

    private String name;
    private String value;

    public EnumsRespostas(String name, String value){
        this.name=name;
        this.value=value;
    }

}