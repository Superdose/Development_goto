package com.axontest.own.productservice.core.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "productlookup")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductLookupEntity implements Serializable {

    private static final long serialVersionUID = -1536251032126136861L;

    @Id
    private String productId;

    @Column(unique = true)
    private String title;

}
