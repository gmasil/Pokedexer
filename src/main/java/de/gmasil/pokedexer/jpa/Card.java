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
package de.gmasil.pokedexer.jpa;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public class Card implements Serializable {
    private static final List<String> PROGRESS_VALUES = Collections
            .unmodifiableList(Arrays.asList("none", "interested", "bought", "ungraded", "in grading", "graded"));

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 1)
    @Column(nullable = false)
    private String name;

    private Long certNumber;

    private Integer grade;

    private Integer population;

    private LocalDate purchaseDate;

    private Double purchasePrice;

    private LocalDate gradingSendOffDate;

    private LocalDate gradingReceivedDate;

    @ManyToOne
    @JoinColumn(name = "series_id", nullable = true)
    private Series series;

    private Integer cardNumber;

    private String status;

    private int progress = 0;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = true)
    private Date createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = true)
    private Date updatedAt;

    public Card() {
        // required by hibernate
    }

    public Card(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCertNumber() {
        return certNumber;
    }

    public void setCertNumber(Long certNumber) {
        this.certNumber = certNumber;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public LocalDate getGradingSendOffDate() {
        return gradingSendOffDate;
    }

    public void setGradingSendOffDate(LocalDate gradingSendOffDate) {
        this.gradingSendOffDate = gradingSendOffDate;
    }

    public LocalDate getGradingReceivedDate() {
        return gradingReceivedDate;
    }

    public void setGradingReceivedDate(LocalDate gradingReceivedDate) {
        this.gradingReceivedDate = gradingReceivedDate;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getProgressValue() {
        return getProgressValue(progress);
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public static List<String> getProgressValues() {
        return PROGRESS_VALUES;
    }

    public static String getProgressValue(int value) {
        return PROGRESS_VALUES.get(value);
    }
}
