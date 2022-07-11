package edu.huce.store.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Integer id;
    private String fullname;
    private String birthday;
    private Integer gender;
    private String address;
    private String phone;
    private Integer accountId;
}
