package az.online.shop.dto;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryDTO {
    Integer id;
    String name;
}