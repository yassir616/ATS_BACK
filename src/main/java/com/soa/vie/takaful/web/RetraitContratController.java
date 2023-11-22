package com.soa.vie.takaful.web;

import java.io.OutputStream;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import com.soa.vie.takaful.requestmodels.CreateUpdateRetraiteContrat;
import com.soa.vie.takaful.requestmodels.UpdateRetraiteContratStatusModel;
import com.soa.vie.takaful.responsemodels.RetraiteContratResponseModel;
import com.soa.vie.takaful.services.IRetraiteContratService;
import com.soa.vie.takaful.util.ContratStatus;

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
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/private")
@Slf4j
public class RetraitContratController {

    @Autowired
    private IRetraiteContratService retraiteContratService;

    @PostMapping("retraitcontrat")
    public RetraiteContratResponseModel createRetraiteContrat(@RequestBody CreateUpdateRetraiteContrat requestModel) throws InterruptedException, ExecutionException {
        return retraiteContratService.createRetraitContrat(requestModel);
    }

    @PutMapping("retraitcontrat/{id}")
    public RetraiteContratResponseModel updateRetraiteContrat(@PathVariable String id,
            @RequestBody CreateUpdateRetraiteContrat requestModel) throws InterruptedException, ExecutionException {
        return retraiteContratService.updateRetraitContrat(id, requestModel);
    }

    @PutMapping("retraitcontratStatus/{id}")
    public RetraiteContratResponseModel updateRetraiteContratStatus(@PathVariable String id,
            @RequestBody UpdateRetraiteContratStatusModel requestModel) throws InterruptedException, ExecutionException {
        return retraiteContratService.updateRetraitContratStatus(id, requestModel);
    }

    @GetMapping("retraitcontratbynumero/{numero}")
    public RetraiteContratResponseModel getRetraiteContratByNumero(@PathVariable String numero) throws InterruptedException, ExecutionException {
        return retraiteContratService.getRetraitContratByNumeroContrat(numero);
    }

    @GetMapping("retraitcontrat/status/{status}")
    public Page<RetraiteContratResponseModel> getRetraiteContratByStatus(@PathVariable ContratStatus status,
            @RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = "" + Integer.MAX_VALUE) int limit) throws InterruptedException, ExecutionException {
        return retraiteContratService.getRetraitContratsByStatus(status, page, limit);
    }

    @GetMapping("retraitcontrat")
    public Page<RetraiteContratResponseModel> getAllRetraiteContrats(@RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = "" + Integer.MAX_VALUE) int limit,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {
        return retraiteContratService.getRetraitContrats(page, limit, sort, direction);
    }

    @GetMapping("retraitcontrat/search")
    public Page<RetraiteContratResponseModel> getContratSearch(@RequestParam("page") final int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") final int limit,
            @RequestParam("by") String by, @RequestParam("for") String sfor) throws InterruptedException, ExecutionException {
        return retraiteContratService.searchContrat(page, limit, by, sfor);
    }

    @GetMapping("retraitcontrat/pdf")
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

}
