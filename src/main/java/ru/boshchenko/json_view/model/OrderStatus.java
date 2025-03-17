package ru.boshchenko.json_view.model;

public enum OrderStatus {

    CREATED("СОЗДАН"), PAID("ОПЛАЧЕН"), RECEIVED("ПОЛУЧЕН"), CLOSED("ЗАКРЫТ");

    private String value;

    OrderStatus(String value) {
        this.value = value;
    }
}
