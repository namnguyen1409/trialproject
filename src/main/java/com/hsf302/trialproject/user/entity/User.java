package com.hsf302.trialproject.user.entity;

import com.hsf302.trialproject.common.entity.BaseEntity;
import com.hsf302.trialproject.inventory.entity.Inventory;
import com.hsf302.trialproject.product.entity.Product;
import com.hsf302.trialproject.user.enums.RoleType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Data
@Table(name = "users")
public class User extends BaseEntity  {

    @Column(name = "username", nullable = false, unique = true, columnDefinition = "nvarchar(50)")
    private String username;

    @Column(name = "password", nullable = false, columnDefinition = "nvarchar(100)")
    private String password;

    @Column(name = "first_name", nullable = false, columnDefinition = "nvarchar(50)")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "nvarchar(50)")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, columnDefinition = "nvarchar(100)")
    private String email;

    @Column(name = "phone", nullable = false, unique = true, columnDefinition = "nvarchar(20)")
    private String phone;

    @Column(name = "gender", nullable = false, columnDefinition = "bit")
    private Boolean gender;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "address", nullable = false, columnDefinition = "nvarchar(255)")
    private String address;

    @Column(name = "is_locked", nullable = false, columnDefinition = "bit")
    private boolean isLocked = false;

    @Column(name = "lock_reason", columnDefinition = "nvarchar(255)")
    private String lockReason;

    @Column(name = "avatar", columnDefinition = "nvarchar(255)")
    private String avatar;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<User> subordinates = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, columnDefinition = "nvarchar(20)")
    private RoleType role;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private Set<Inventory> inventories = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory assignedInventory;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<RefreshToken> refreshTokens = new HashSet<>();

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private Set<RegistrationToken> registrationTokens = new HashSet<>();

}
