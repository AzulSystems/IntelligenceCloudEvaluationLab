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
class InventoryController {

	private final InventoryRepository inventoryRepository;

	public InventoryController(InventoryRepository inventory) {
		this.inventoryRepository = inventory;
	}

	@GetMapping("/inventorys.html")
	public String showInventoryList(@RequestParam(defaultValue = "1") int page, Model model) {
		// Here we are returning an object of type 'Inventorys' rather than a collection
		// of
		// Inventory
		// objects so it is simpler for Object-Xml mapping
		Inventorys inventorys = new Inventorys();
		Page<Inventory> paginated = findPaginated(page);
		inventorys.getInventoryList().addAll(paginated.toList());
		return addPaginationModel(page, paginated, model);

	}

	private String addPaginationModel(int page, Page<Inventory> paginated, Model model) {
		List<Inventory> listInventorys = paginated.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", paginated.getTotalPages());
		model.addAttribute("totalItems", paginated.getTotalElements());
		model.addAttribute("listInventorys", listInventorys);
		return "inventorys/inventoryList";
	}

	private Page<Inventory> findPaginated(int page) {
		int pageSize = 5;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		return inventoryRepository.findAll(pageable);
	}

	@GetMapping({ "/inventorys" })
	public @ResponseBody Inventorys showResourcesInventoryList() {
		// Here we are returning an object of type 'Inventorys' rather than a collection
		// of
		// Inventory
		// objects so it is simpler for JSon/Object mapping
		Inventorys inventorys = new Inventorys();
		inventorys.getInventoryList().addAll(this.inventoryRepository.findAll());
		return inventorys;
	}

}
