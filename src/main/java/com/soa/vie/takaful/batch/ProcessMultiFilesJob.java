package com.soa.vie.takaful.batch;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import java.math.BigDecimal;
import com.ibm.icu.text.SimpleDateFormat;

import com.soa.vie.takaful.entitymodels.CotisationDTO;
import com.soa.vie.takaful.repositories.ICotisationRepository;
import com.soa.vie.takaful.repositories.IDecesProduitRepository;

import com.soa.vie.takaful.responsemodels.CotisationModelResponse;

import com.soa.vie.takaful.services.ICotisationService;
import com.soa.vie.takaful.services.IDecesProduitService;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemReader;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableBatchProcessing
@Slf4j
public class ProcessMultiFilesJob {

	public static String type;

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;
	public String typeProduit;

	@Autowired
	public ProcessMultiFilesJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			ICotisationService cotisationService, IDecesProduitService decesProduitService) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.cotisationService = cotisationService;
		this.decesProduitService = decesProduitService;

	}

	@Autowired
	JobLauncher jobLauncher;

	private ICotisationService cotisationService;
	@Autowired
	ICotisationRepository cotisationRepository;

	private IDecesProduitService decesProduitService;
	@Autowired
	IDecesProduitRepository decesProduitRepository;

	// private Resource outputResource = new
	// FileSystemResource("output/outputDat.csv");

	// @Bean
	// @StepScope
	// public FlatFileFooterCallback customFooterCallback() {
	// return new CustomFooterCallback();
	// }

	@Bean
	@StepScope
	public FlatFileItemWriter<CotisationDTO> writer(@Value("#{jobParameters['type']}") String type,
			@Value("#{jobParameters['id']}") String id)
			throws InterruptedException, ExecutionException {

		FlatFileItemWriter<CotisationDTO> writers = new FlatFileItemWriter<>();

		String partenaire = "";
		List<CotisationModelResponse> cot = cotisationService.getCotisationByContrats(id);
		for (CotisationModelResponse item : cot) {
			partenaire = item.getContrat().getProduit().getPartenaire().getCode();
			cotisationRepository.updateNumeroOrdre(item.getContrat().getNumOrdrePrelevement() + 1,
					item.getContrat().getId());

		}
		// final Date current = new Date("DD-MM-YYYY");
		final String pattern = "ddMMyyyy";
		String dateInString = new SimpleDateFormat(pattern).format(new Date());
		Resource outputResource = new FileSystemResource(
				"output/" + partenaire + "_" + type + "_PRL_" + id + "_" + dateInString + ".txt");
		writers.setResource(outputResource);
		writers.setAppendAllowed(true);

		String sum = formatInteger(cotisationRepository.sumSolde(id));
		int count = cotisationRepository.countData(id);

		DelimitedLineAggregator<CotisationDTO> delimitedLineAggregator = new DelimitedLineAggregator<>();
		BeanWrapperFieldExtractor<CotisationDTO> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<>();
		beanWrapperFieldExtractor.setNames(new String[] { "codePartenaire", "codeProduit",
				"numeroContrat", "dateEffet", "dateEcheance", "dureeContrat", "cin", "nom", "prenom", "rib",
				"numeroDossierFinancement", "datePrelevement", "periodicite", "solde", "commissionYsr", "montantTTT",
				"numeroQuittance", "numeroOrdrePrelevement", "nombreAppel" });

		delimitedLineAggregator.setDelimiter("$");
		delimitedLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);
		writers.setLineAggregator(delimitedLineAggregator);
		writers.setFooterCallback(writer -> writer.write("FIN$" + count + "$" + sum + "$"));
		return writers;
	}

	public String formatInteger(double number) {
		DecimalFormat df = new DecimalFormat("000000000000000");
		double roundOff = Math.round(number * 100.0) / 100.0;
		String formatted = df.format(roundOff * 100);
		return formatted;
	}
	
	public String formatPrelevement(double number) {
		DecimalFormat df = new DecimalFormat("000000000000000");
		String formatted = df.format(number * 100);
		return formatted;
	}
	public String formatOrdrePrelevement(int number) {
		DecimalFormat df = new DecimalFormat("000");
		String formatted = df.format(number);
		return formatted;
	}

	public String formatQuittance(float number) {
		DecimalFormat df = new DecimalFormat("0000000000");
		String formatted = df.format(number);
		return formatted;
	}

	public String formatDuree(Integer number) {
		DecimalFormat df = new DecimalFormat("000");
		String formatted = df.format(number);
		return formatted;
	}

	public String formaterDate(Date date) {
		final String pattern = "dd-MM-yyyy";
		String dateInString = new SimpleDateFormat(pattern).format(date);
		return dateInString;

	}

	public String formaterString(String nom) {
		String padded = String.format("%-10s", nom);
		return padded;
	}

	public String formatString(String nom) {
		String padded = String.format("%-30s", nom);
		return padded;
	}

	public String periodicite(String periodicite) {
		String period = "00";
		if (periodicite.equals("Annuelle")) {
			period = "12";

		} else if (periodicite.equals("Mensuelle")) {
			period = "01";
		}
		return period;
	}

	public String codeProduit(String risque, String partenaire) {
		String code = "";
		if (risque.equals("DECES FINANCEMENT")) {
			if (partenaire.equals("BAY")) {
				code = "DFY";
			} else if (partenaire.equals("AAB")) {
				code = "DFA";
			}

		}
		return code;
	}

	//@Scheduled(cron = "0 0 19 * * ?")
	public void perform() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException, InterruptedException, ExecutionException {
		log.info("hello world job started at :" + new Date());
		List<String> cprod = cotisationService.productByContrats();
		List<String> cproduit = cotisationService.productByContrat();
		for (String item : cprod) {
			if (item.equals("DECES FINANCEMENT")) {
				typeProduit = "DFY";
				for (String items : cproduit) {
					JobParameters params = new JobParametersBuilder()
							.addString("JobID", String.valueOf(System.currentTimeMillis()))
							.addString("type", typeProduit)
							.addString("id", items)
							.toJobParameters();
					jobLauncher.run(readFiles(), params);

				}

			}

		}

	}

	@Bean
	public Job readFiles() throws InterruptedException, ExecutionException {
		return jobBuilderFactory.get("readFiles").incrementer(new RunIdIncrementer())
				.flow(step1()).end().build();

	}

	@Bean
	@StepScope
	public ItemReader<CotisationDTO> itemReader(@Value("#{jobParameters['id']}") String id)
			throws InterruptedException, ExecutionException {

		List<CotisationModelResponse> cot = cotisationService.getCotisationByContrats(id);
		List<CotisationDTO> dto = new ArrayList<>();
		for (CotisationModelResponse item : cot) {

			CotisationDTO cotdto = new CotisationDTO();
			BeanUtils.copyProperties(item, cotdto);

			double montantTTC = item.getMontantTTC();
			BigDecimal Output = new BigDecimal(montantTTC).setScale(2, RoundingMode.HALF_EVEN);
			double TTC = montantTTC > Output.doubleValue() ? Output.doubleValue() : (Output.doubleValue() - 0.01);
			System.out.println("Montant TTC = " + TTC);

			double montantHT = (TTC / 1.1);
			BigDecimal OutputContHT = new BigDecimal(montantHT).setScale(2, RoundingMode.HALF_EVEN);
			double montantContributionHT = montantHT > OutputContHT.doubleValue() ? OutputContHT.doubleValue() : (OutputContHT.doubleValue() - 0.01);
			System.out.println("montantContributionHT = " + montantContributionHT);

			double commTTC = (((montantContributionHT * 15) / 100));
			BigDecimal OutputComTTC = new BigDecimal(commTTC).setScale(2, RoundingMode.HALF_EVEN);
			double commissionTTC = commTTC > OutputComTTC.doubleValue() ? OutputComTTC.doubleValue() : (OutputComTTC.doubleValue() - 0.01);
			System.out.println("commissionTTC: " + commissionTTC);

			double taxCom = (((commissionTTC * 12.281) / 100));
			BigDecimal OutputtaxCom = new BigDecimal(taxCom).setScale(2, RoundingMode.HALF_EVEN);
			double taxSurCommission = taxCom > OutputtaxCom.doubleValue() ? OutputtaxCom.doubleValue() : (OutputtaxCom.doubleValue() - 0.01);
			System.out.println("taxSurCommission: " + taxSurCommission);

			double attakaful = (TTC - commissionTTC + taxSurCommission);
			// BigDecimal OutputTakaful = new BigDecimal(attak).setScale(2, RoundingMode.HALF_EVEN);
			// double attakaful = attak > OutputTakaful.doubleValue() ? OutputTakaful.doubleValue() : (OutputTakaful.doubleValue() - 0.01);
			System.out.println("attakaful: " + attakaful);

			double commissionHT =(commissionTTC - taxSurCommission);
			System.out.println("commission HT TTT: " + commissionHT);
			// BigDecimal OutputTTT = new BigDecimal(TTT).setScale(2, RoundingMode.HALF_EVEN);
			// double commissionHT = TTT > OutputTTT.doubleValue() ? OutputTTT.doubleValue() : (OutputTTT.doubleValue() - 0.01);
			// System.out.println("commissionHT: " + commissionHT);
			
			String numeroDossierFinance = formatString(item.getContrat().getNumeroDossierFinancement());
			String nom = formatString(item.getContrat().getAssure().getNom());
			String prenom = formatString(item.getContrat().getAssure().getPrenom());
			String periodicite = periodicite(item.getContrat().getPeriodicite().getLibelle());
			String ordrePrelevement = formatOrdrePrelevement(item.getContrat().getNumOrdrePrelevement());
			String codeParticipant = formaterString(item.getContrat().getAssure().getCin());
			
			String monTTC= formatPrelevement(TTC);
			String montantCommissionYsr = formatPrelevement(commissionHT);
			String MontantContributionTTT = formatPrelevement(attakaful);
			String nbrAppel = "00";
			String duree = formatDuree(item.getContrat().getDureeContrat());
			String Quittance = formatQuittance(item.getNumQuittance());
			String datePrelevement = formaterDate(item.getDatePrelevement());
			String dateEffet = formaterDate(item.getContrat().getDateEffet());
			String dateEcheance = formaterDate(item.getContrat().getDateEcheance());
			String codeProduit = codeProduit(item.getContrat().getProduit().getRisque().getLibelle(),
					item.getContrat().getProduit().getPartenaire().getCode());
			
			cotdto.setSolde(monTTC);
			cotdto.setDatePrelevement(datePrelevement);
			cotdto.setPeriodicite(periodicite);
			cotdto.setCodePartenaire(item.getContrat().getProduit().getPartenaire().getCode());
			cotdto.setCodeProduit(codeProduit);
			cotdto.setNumeroDossierFinancement(numeroDossierFinance);
			cotdto.setNumeroContrat(item.getContrat().getNumeroContrat());
			cotdto.setNom(nom);
			cotdto.setPrenom(prenom);
			cotdto.setCin(codeParticipant);
			cotdto.setDateEffet(dateEffet);
			cotdto.setDateEcheance(dateEcheance);
			cotdto.setNumeroQuittance(Quittance);
			cotdto.setRib(item.getContrat().getAssure().getRib());
			cotdto.setDureeContrat(duree);
			cotdto.setMontantTTT(MontantContributionTTT);
			cotdto.setCommissionYsr(montantCommissionYsr);
			cotdto.setNombreAppel(nbrAppel);
			cotdto.setNumeroOrdrePrelevement(ordrePrelevement);

			dto.add(cotdto);
		}

		return new ListItemReader<>(dto);
	}

	@Bean
	public Step step1() throws InterruptedException, ExecutionException {

		return stepBuilderFactory.get("step1").<CotisationDTO, CotisationDTO>chunk(5)
				.reader(itemReader(null))
				.writer(writer(null, null))
				.build();
	}

}