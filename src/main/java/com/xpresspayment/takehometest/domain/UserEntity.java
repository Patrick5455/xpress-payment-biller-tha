package com.xpresspayment.takehometest.domain;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.xpresspayment.takehometest.common.enumconstants.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user" )
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    public Long id;

    @Column(name = "uuid", nullable=false, unique=true)
    @NotNull
    @NotBlank
    private String uuid;

    @Column(name = "first_name")
    @NotBlank
    @NotNull
    private String firstname;

    @Column(name = "last_name")
    @NotBlank
    @NotNull
    private String lastname;

    @Column(name = "email", nullable=false, unique=true)
    @NotBlank
    private String email;

    @Column(name = "password")
    @NotBlank
    @JsonIgnore
    private String password;

    @Column(name = "created_at")
    @JsonIgnore
    @NotNull
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @JsonIgnore
    private Timestamp updatedAt;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;




    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserEntity other = (UserEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
