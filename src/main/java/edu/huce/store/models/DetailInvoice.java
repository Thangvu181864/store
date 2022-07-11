package edu.huce.store.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailInvoice {
    private Integer id;
    private Integer invoiceId;
    private Integer productId;
    private Integer priceSell;
    private Integer quantity;
    private Integer total;
    private String note;
}
