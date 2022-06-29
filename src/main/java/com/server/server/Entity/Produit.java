package com.server.server.Entity;




import javax.persistence.*;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")

public class Produit {
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
