package edu.huce.store.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    private Integer id;
    private Integer employeeId;
    private String typeInvoice;
    private String createAt;
    private Integer total;
    private String note;
    private List<DetailInvoice> detailInvoices;
}
