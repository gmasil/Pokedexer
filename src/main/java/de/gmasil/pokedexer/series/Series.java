/**
 * Pokédexer
 * Copyright © 2021 Gmasil
 *
 * This file is part of Pokédexer.
 *
 * Pokédexer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Pokédexer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Pokédexer. If not, see <https://www.gnu.org/licenses/>.
 */
package de.gmasil.pokedexer.series;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.gmasil.pokedexer.card.Card;

@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "The name must not be empty")
    private String name;

    @OneToMany(mappedBy = "series")
    private Set<Card> cards;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = true)
    private Date createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = true)
    private Date updatedAt;

    public Series() {
        // required by hibernate
    }

    /**
     * Important to remove foreign keys from referenced objects before deleting
     * entries to maintain data integrity
     */
    @PreRemove
    private void preRemove() {
        for (Card card : cards) {
            card.setSeries(null);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
