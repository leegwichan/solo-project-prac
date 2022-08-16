package com.example.projectPrac.v1.companyLocation.entity;

import com.example.projectPrac.v1.audit.Auditable;
import com.example.projectPrac.v1.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Location extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long locationId;

    private String country;
    private String class1;
    private String class2;

    @OneToMany(mappedBy = "location")
    private List<User> users = new ArrayList<>();
}
