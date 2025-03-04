package com.biblioteca.gestao_biblioteca.dtos;

public class EnumsRespostas {

    private String name;
    private String value;

    public EnumsRespostas() {
    }

    public EnumsRespostas(String name, String value){
        this.name=name;
        this.value=value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}