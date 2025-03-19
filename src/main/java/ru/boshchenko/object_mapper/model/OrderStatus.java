package ru.boshchenko.object_mapper.model;

public enum OrderStatus {

    CREATED("СОЗДАН"), PAID("ОПЛАЧЕН"), RECEIVED("ПОЛУЧЕН"), CLOSED("ЗАКРЫТ");

    private String value;

    OrderStatus(String value) {
        this.value = value;
    }
}
