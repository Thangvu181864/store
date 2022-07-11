package edu.huce.store.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {
    private Integer id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String note;
}
