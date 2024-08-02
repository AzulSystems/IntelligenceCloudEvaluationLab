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
package org.springframework.samples.petclinic.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.samples.petclinic.model.StoreModel;

import jakarta.persistence.Entity;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlElement;

/**
 * Simple JavaBean domain object representing a store
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Arjen Poutsma
 * @author Robert Statsinger
 */
@Entity
@Table(name = "stores")
public class Store extends StoreModel {

	@Column(name = "name")
	@NotEmpty
	private String name;

	/*
	 * @Column(name = "address")
	 *
	 * @NotEmpty
	 */
	private String address;

	@Column(name = "city")
	@NotEmpty
	private String city;

	@Column(name = "state")
	@NotEmpty
	private String state;

	public void setName(String name) {
		this.name = name;
	}

	public String getNamed() {
		return this.address;
	}

}
