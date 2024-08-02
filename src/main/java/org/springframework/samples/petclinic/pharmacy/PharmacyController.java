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
package org.springframework.samples.petclinic.pharmacy;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Robert Statsinger
 */
@Controller
class PharmacyController {

	private final PharmacyRepository pharmacyRepository;

	public PharmacyController(PharmacyRepository pharmacy) {
		this.pharmacyRepository = pharmacy;
	}

	@GetMapping("/pharmacys.html")
	public String showPharmacyList(@RequestParam(defaultValue = "1") int page, Model model) {
		// Here we are returning an object of type 'Pharmacys' rather than a collection of
		// Pharmacy
		// objects so it is simpler for Object-Xml mapping
		Pharmacys pharmacys = new Pharmacys();
		Page<Pharmacy> paginated = findPaginated(page);
		pharmacys.getPharmacyList().addAll(paginated.toList());
		return addPaginationModel(page, paginated, model);

	}

	private String addPaginationModel(int page, Page<Pharmacy> paginated, Model model) {
		List<Pharmacy> listPharmacys = paginated.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", paginated.getTotalPages());
		model.addAttribute("totalItems", paginated.getTotalElements());
		model.addAttribute("listPharmacys", listPharmacys);
		return "pharmacys/pharmacyList";
	}

	private Page<Pharmacy> findPaginated(int page) {
		int pageSize = 5;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		return pharmacyRepository.findAll(pageable);
	}

	@GetMapping({ "/pharmacys" })
	public @ResponseBody Pharmacys showResourcesPharmacyList() {
		// Here we are returning an object of type 'Pharmacys' rather than a collection of
		// Pharmacy
		// objects so it is simpler for JSon/Object mapping
		Pharmacys pharmacys = new Pharmacys();
		pharmacys.getPharmacyList().addAll(this.pharmacyRepository.findAll());
		return pharmacys;
	}

}
