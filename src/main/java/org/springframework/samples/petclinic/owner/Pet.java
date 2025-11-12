/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.NamedEntity;

/**
 * Simple business object representing a pet.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 */
@Entity
@Table(name = "pets")
public class Pet extends NamedEntity {

	@Column(name = "birth_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private PetType type;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "pet")
	private Set<Visit> visits = new LinkedHashSet<>();

	public Pet() {
		this.birthDate = LocalDate.now();
		this.type = new PetType();
		this.owner = new Owner();
	}

	public LocalDate getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public PetType getType() {
		return this.type;
	}

	public void setType(PetType type) {
		this.type = type;
	}

	public Owner getOwner() {
		return this.owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Set<Visit> getVisits() {
		return this.visits;
	}

	public void addVisit(Visit visit) {
		getVisits().add(visit);
		visit.setPet(this);
	}

	/**
	 * Calculates and returns the pet's age as a formatted string.
	 * @return formatted age string: - "Unknown" if birth date is null - "Not yet
	 * born" if birth date is in the future - "Less than 1 year old" if pet is under
	 * 1 year old - "1 year old" if pet is exactly 1 year old - "X years old" if pet
	 * is X years old (plural)
	 */
	public String getAge() {
		// Handle null birth date
		if (this.birthDate == null) {
			return "Unknown";
		}

		LocalDate today = LocalDate.now();

		// Handle future birth date
		if (this.birthDate.isAfter(today)) {
			return "Not yet born";
		}

		// Calculate years
		int years = Period.between(this.birthDate, today).getYears();

		// Format output based on age
		if (years == 0) {
			return "Less than 1 year old";
		}
		else if (years == 1) {
			return "1 year old";
		}
		else {
			return years + " years old";
		}
	}

}
