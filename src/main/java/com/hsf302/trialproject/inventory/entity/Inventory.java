package com.hsf302.trialproject.inventory.entity;

import com.hsf302.trialproject.common.entity.BaseEntity;
import com.hsf302.trialproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder(toBuilder = true)
@Table(name = "inventories")
@NoArgsConstructor
public class Inventory extends BaseEntity {
    @Column(name = "name", nullable = false, columnDefinition = "nvarchar(50)")
    private String name;

    @Column(name = "location", nullable = false, columnDefinition = "nvarchar(255)")
    private String location;

    @Column(name = "description", nullable = true, columnDefinition = "nvarchar(max)")
    private String description;

    @OneToMany(mappedBy = "inventory", fetch = FetchType.LAZY)
    private Set<Zone> zones = new HashSet<>();

    @OneToMany(mappedBy = "assignedInventory", fetch = FetchType.LAZY)
    private Set<User> staff = new HashSet<>();

}
