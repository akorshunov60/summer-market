package ru.geekbrains.summer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.summer.model.ProductEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@ApiModel(description = "Product dto in the application.")
public class ProductDto {

    @ApiModelProperty(notes = "Unique identifier of the product.", example = "1", required = true, position = 0)
    private Long id;

    @ApiModelProperty(notes = "Title of the product's category.", example = "Food", required = true, position = 2)
    @Size(min = 4, max = 255)
    private String title;

    @ApiModelProperty(notes = "Title of the product's category.", example = "Food", required = true, position = 2)
    @Size(min = 4, max = 255)
    private String categoryTitle;

    @ApiModelProperty(notes = "Price of the product (RUB).", example = "450.00", required = true, position = 3)
    @Min(0)
    private BigDecimal price;

    public ProductDto(ProductEntity productEntity) {
        this.id = productEntity.getId();
        this.title = productEntity.getTitle();
        this.categoryTitle = productEntity.getCategoryEntity().getTitle();
        this.price = productEntity.getPrice();
    }
}
