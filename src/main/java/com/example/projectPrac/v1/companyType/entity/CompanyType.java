package com.example.projectPrac.v1.companyType.entity;

import com.example.projectPrac.v1.audit.Auditable;
import com.example.projectPrac.v1.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyType extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long companyTypeId;

    private String companyType;

    @OneToMany(mappedBy = "companyType")
    private List<User> users = new ArrayList<>();
}
