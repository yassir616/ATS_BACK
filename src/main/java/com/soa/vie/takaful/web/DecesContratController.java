package com.soa.vie.takaful.web;

import java.io.OutputStream;

import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import com.soa.vie.takaful.entitymodels.Role;
import com.soa.vie.takaful.entitymodels.TakafulUser;
import com.soa.vie.takaful.requestmodels.CreateUpdateDecesContrat;
import com.soa.vie.takaful.requestmodels.UpdateDecesContrat;
import com.soa.vie.takaful.requestmodels.UpdateDecesContratStatusModel;
import com.soa.vie.takaful.requestmodels.updateDecesContratDateEffetModel;
import com.soa.vie.takaful.responsemodels.ContractResponseModel;
import com.soa.vie.takaful.responsemodels.DecesContratResponseModel;
import com.soa.vie.takaful.responsemodels.updateDecesContratDateEffetResponse;
import com.soa.vie.takaful.security.SecurityCanstants;
import com.soa.vie.takaful.services.IDecesContratService;
import com.soa.vie.takaful.services.IUserService;
import com.soa.vie.takaful.util.ContratStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/private")
@Slf4j
public class DecesContratController {

    @Autowired
    private IDecesContratService decesContratService;

    @Autowired
    private IUserService userService;

    @PostMapping("decescontrat")
    public DecesContratResponseModel createDecesContrat(@RequestBody CreateUpdateDecesContrat requestModel)
            throws InterruptedException, ExecutionException {

        return decesContratService.createDecesContrat(requestModel);
    }

    @PutMapping("decescontrat/{id}")
    public DecesContratResponseModel updateDecesContrat(@PathVariable String id,
            @RequestBody UpdateDecesContrat requestModel) throws InterruptedException, ExecutionException {
        return decesContratService.updateDecesContrat(id, requestModel);
    }

    @PutMapping("updateContratDateEffet/{id}")
    public updateDecesContratDateEffetResponse updateContratDateEffet(@PathVariable String id,
            @RequestBody updateDecesContratDateEffetModel requestModel) throws InterruptedException, ExecutionException {
        return decesContratService.updateContratDateEffet(id, requestModel);
    }

    @PutMapping("decescontratStatus/{id}")
    public DecesContratResponseModel updateDecesContratStatus(@PathVariable String id,
            @RequestBody UpdateDecesContratStatusModel requestModel) throws InterruptedException, ExecutionException {
        return decesContratService.updateDecesContratStatus(id, requestModel);
    }

    @GetMapping("decescontrat/status/{status}")
    public Page<DecesContratResponseModel> getDecesContratByStatus(@PathVariable ContratStatus status,
            @RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = "" + Integer.MAX_VALUE) int limit,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction)
            throws InterruptedException, ExecutionException {
        return decesContratService.getDecesContartsByStatus(status, page, limit, sort, direction);
    }

    @GetMapping("decescontrat")
    public Page<DecesContratResponseModel> getAllDecesContrats(@RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = "" + Integer.MAX_VALUE) int limit,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction)
            throws InterruptedException, ExecutionException {
        return decesContratService.getDecesContrats(page, limit, sort, direction);
    }

    @GetMapping("contrat/search")
    public Page<ContractResponseModel> getContratSearch(@RequestParam("page") final int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") final int limit,
            @RequestParam("by") String by, @RequestParam("for") String sfor)
            throws InterruptedException, ExecutionException {

        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TakafulUser user = userService.getUser(authentication.toString());

        boolean isAdmin = false;

        for (Role role : user.getRoles()) {
            if (role.getName().equals(SecurityCanstants.ADMIN))
                isAdmin = true;
        }
        if (isAdmin)
            return decesContratService.searchContrat(page, limit, by, sfor);
        else
            return decesContratService.searchContratParPartenaire(page, limit, by, sfor,
                    user.getPointVentes().get(0).getPartenairepv().getId());
    }

    @GetMapping("decescontrat/pdf")
    public void generatePdf(HttpServletResponse response) {
        try {
            ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
            templateResolver.setSuffix(".html");
            templateResolver.setTemplateMode("HTML");
            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(templateResolver);
            Context context = new Context();
            context.setVariable("name", "Natsu");
            String html = templateEngine.process("html/contratTakafulCP", context);
            String fileName = "natsu.pdf";
            response.addHeader("Content-disposition", "attachment;filename=" + fileName);
            response.setContentType("application/pdf");
            OutputStream outputStream = response.getOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception ex) {
            log.error("Context :" + ex);
        }
    }

    @GetMapping("contratId/{id}")
    public ContractResponseModel getContratSearch(@PathVariable String id)
            throws InterruptedException, ExecutionException {
        return decesContratService.findContratById(id);
    }

    @GetMapping("decescontrats")
    public Page<DecesContratResponseModel> getContratByPartenaire(@RequestParam("page") final int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "" + Integer.MAX_VALUE) int limit,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction)
            throws InterruptedException, ExecutionException {

        boolean isAdmin = false;

        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TakafulUser user = userService.getUser(authentication.toString());
        for (Role role : user.getRoles()) {
            if (role.getName().equals(SecurityCanstants.ADMIN))
                isAdmin = true;
        }
        if (isAdmin)
            return decesContratService.getDecesContrats(page, limit, sort, direction);
        else
            return decesContratService.getContratByPartenaire(page, limit, sort, direction,
                    user.getPointVentes().get(0).getPartenairepv().getId());

    }

    @PutMapping("decescontratOrientation/{id}/{orientation}")
    public DecesContratResponseModel updateDecesContratOrientation(@PathVariable String id,
            @PathVariable String orientation) throws InterruptedException, ExecutionException {
        return decesContratService.updateDecesContratOrientation(id, orientation);
    }

}
