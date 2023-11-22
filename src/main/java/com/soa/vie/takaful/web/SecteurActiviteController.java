package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import javax.ejb.EJBException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateSecteurActivite;
import com.soa.vie.takaful.responsemodels.SecteurActiviteModelResponse;
import com.soa.vie.takaful.services.ISecteurActiviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public class SecteurActiviteController {
	@Autowired
	private ISecteurActiviteService secteurService;

	@PostMapping("secteur")
	public SecteurActiviteModelResponse createSecteurActivite(@RequestBody CreateAndUpdateSecteurActivite secteur)
			throws InterruptedException, ExecutionException {

		return secteurService.createSecteurActivite(secteur);
	}

	@GetMapping("secteur/{id}")
	public SecteurActiviteModelResponse getSecteurActivite(@PathVariable String id)
			throws InterruptedException, ExecutionException {
		return secteurService.getsecteurActiviteById(id);
	}

	@PutMapping("secteur/{id}")
	public SecteurActiviteModelResponse updateSecteurActivite(@PathVariable String id,
			@RequestBody CreateAndUpdateSecteurActivite secteurModel) throws InterruptedException, ExecutionException {

		return secteurService.updateSecteurActivite(id, secteurModel);

	}

	@GetMapping("secteur")
	public Page<SecteurActiviteModelResponse> getAllSecteurActivite(@RequestParam("page") int page,
			@RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
			@RequestParam("sort") String sort, @RequestParam("direction") String direction)
			throws InterruptedException, ExecutionException {

		return secteurService.getsecteurActivites(page, limit, sort, direction);
	}

	@GetMapping("secteurByLibelle")
	public SecteurActiviteModelResponse getSecteurActiviteByLibelle(@RequestParam("libelle") String libelle)
			throws InterruptedException, EJBException, ExecutionException {
		return secteurService.getsecteurActiviteByLibelle(libelle);

	}
}
