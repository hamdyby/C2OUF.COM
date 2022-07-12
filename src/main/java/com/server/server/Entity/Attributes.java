package com.server.server.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Attributes {
  @Id
  private long id;
  private  String name;
  private  String isoCode;

  @ManyToOne
  private AttributeGroup attributeGroup;

  @ManyToOne
  private Product product;
}
