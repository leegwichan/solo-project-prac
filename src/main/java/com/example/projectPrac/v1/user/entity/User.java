package com.example.projectPrac.v1.user.entity;

import com.example.projectPrac.v1.audit.Auditable;
import com.example.projectPrac.v1.companyLocation.entity.Location;
import com.example.projectPrac.v1.companyType.entity.CompanyType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "MEMBER")
@Getter
@Setter
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;
    private String password;

    @ManyToOne
    @JoinColumn(name = "COMPANY_LOCATION")
    private Location location;

    private String companyName;
    @ManyToOne
    @JoinColumn(name = "COMPANY_TYPE")
    private CompanyType companyType;

}
