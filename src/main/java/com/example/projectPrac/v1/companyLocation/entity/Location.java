package com.example.projectPrac.v1.companyLocation.entity;

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
@AllArgsConstructor
@NoArgsConstructor
public class Location extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long locationId;

    private String country;
    private String class1;
    private String class2;

    @OneToMany(mappedBy = "location")
    private List<User> users = new ArrayList<>();

    public String getLocation(){
        StringBuilder sb = new StringBuilder();
        sb.append(country).append(", ").append(class1).append(class2);
        return sb.toString();
    }
}
