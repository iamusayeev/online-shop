package az.online.shop.dto;


import az.online.shop.model.Status;

public record OrderFilter(Status status,
                          String address,
                          Integer sum) {
}