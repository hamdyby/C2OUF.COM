package com.server.server.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import java.util.Date;

import javax.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProduitDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int manufacturer;
    private String  sku;
    private  int ean13;
    private  int weight;
    private  int height;
    private  int width;
    private  int depth;
    private Date dateUpd;
    private int categorie;
    private Date dateUpdDescription;
    private Date dateUpdImages;
    private Date dateUpdStock;
    private float wholesalePrice;
    private float retailPrice;
    private Date dateAdd;
    private int video;
    private int active;
    private boolean attributes;
    private boolean categories;
    private boolean images;
    private float taxRate;
    private int taxId;
    private float inShopsPrice;
    private String condition;
    private String logisticClass;
    private boolean tags;
    private Date dateUpdProperties;
    private  Date dateUpdCategories;
    private float priceLargeQuantities;


}
