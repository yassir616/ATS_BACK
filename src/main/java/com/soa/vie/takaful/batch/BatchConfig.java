package com.soa.vie.takaful.batch;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.sql.DataSource;
import com.ibm.icu.text.SimpleDateFormat;
import com.soa.vie.takaful.entitymodels.Cotisation;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateCotisation;
import com.soa.vie.takaful.util.Constants;
import com.soa.vie.takaful.util.EtatCotisation;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.validation.BindException;

@Configuration
@EnableBatchProcessing

public class BatchConfig {

        @Autowired
        public JobBuilderFactory jobBuilderFactory;

        @Autowired
        public StepBuilderFactory stepBuilderFactory;
        @Autowired
        AnnuelReaderFromDB myCustomAnnuelReader;

        @Autowired
        MensuelCustomReader myCustomMensuelReader;

        @Autowired
        AnnuelWriterToCSV annuelCustomWriter;
        @Autowired
        CotisationWriterToDB cotisationWriter;

        @Autowired
        MensuelCustomWriter mensuelCustomWriter;

        @Autowired
        JobLauncher jobLauncher;

        @Autowired
        DataSource dataSource;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

        @Bean
        public CotisationItemProcessor processor() {
                return new CotisationItemProcessor();
        }

        @Bean
        public Job annuelJobWriterToCSV() {
                return jobBuilderFactory.get("annuelJobWriterToCSV")
                                .incrementer(new RunIdIncrementer())
                                .flow(createAnnuelStepWriterToCSV()).end().build();
        }

        @Bean
        public Job mensuelJobWriterToCSV() {
                return jobBuilderFactory.get("mensuelJobWriterToCSV")
                                .incrementer(new RunIdIncrementer())
                                .flow(createMensuelStepWriterToCSV()).end().build();
        }

        @Bean
        public Step createMensuelStepWriterToCSV() {

                return stepBuilderFactory.get("createMensuelStepWriterToCSV")
                                .<Cotisation, CreateAndUpdateCotisation>chunk(5)
                                .reader(myCustomMensuelReader)
                                .writer(mensuelCustomWriter)
                                .build();
        }

        @Bean
        public Step createAnnuelStepWriterToCSV() {
                return stepBuilderFactory.get("createAnnuelStepWriterToCSV")
                                .<Cotisation, CreateAndUpdateCotisation>chunk(5)
                                .reader(myCustomAnnuelReader)
                                .writer(annuelCustomWriter)
                                .build();
        }

        @Bean

        public Job readMensuelCotisationFromCSVJob() {
                return jobBuilderFactory
                                .get("readMensuelCotisationFromCSVJob")
                                .incrementer(new RunIdIncrementer())
                                .flow(readMensuelCotisationStep()).end().build();
        }

        @Bean

        public Job readAnnuelCotisationFromCSVJob() {
                return jobBuilderFactory
                                .get("readAnnuelCotisationFromCSVJob")
                                .incrementer(new RunIdIncrementer())
                                .flow(readAnnuelCotisationStep()).end().build();
        }

        @Bean
        public Step readMensuelCotisationStep() {
                return stepBuilderFactory
                                .get("readMensuelCotisationStep")
                                .<CreateAndUpdateCotisation, Cotisation>chunk(5)
                                .reader(getReaderCotisation(Constants.MENSUEL_COTISATION_FILE))
                                .processor(processor())
                                .writer(cotisationWriter)
                                .build();
        }

        @Bean
        public Step readAnnuelCotisationStep() {
                return stepBuilderFactory
                                .get("readAnnueluelCotisationStep")
                                .<CreateAndUpdateCotisation, Cotisation>chunk(5)
                                .reader(getReaderCotisation(Constants.ANNUEL_COTISATION_FILE))
                                .processor(processor())
                                .writer(cotisationWriter)
                                .build();
        }

        // @Scheduled(cron = "0 0 0 1 1 ?")
        public void scheduleOfExportAnnuelCotisation() throws Exception {

                JobParameters jobParameters = new JobParametersBuilder()
                                .addString("time",
                                                format.format(Calendar.getInstance().getTime()))
                                .toJobParameters();
                jobLauncher.run(annuelJobWriterToCSV(), jobParameters);

        }

        //@Scheduled(cron = "0 51 13 * * ?")
        public void scheduleOfExportMensueldRate() throws Exception {

                JobParameters jobParameters = new JobParametersBuilder()
                                .addString("time",
                                                format.format(Calendar.getInstance().getTime()))
                                .toJobParameters();
                jobLauncher.run(mensuelJobWriterToCSV(), jobParameters);

        }

        // @Scheduled(cron = "0 05 0 1 1 ?")

        public void importAnnuelCotisatioToDB() throws Exception {
                JobParameters params = new JobParametersBuilder()
                                .addString("JobAnnuelCotToDBID", String.valueOf(System.currentTimeMillis()))
                                .toJobParameters();
                jobLauncher.run(readAnnuelCotisationFromCSVJob(), params);
        }
               
       // @Scheduled(cron = "0 58 13 * * ?")
        public void importMensuelCotisationToDB() throws Exception {
                JobParameters params = new JobParametersBuilder()
                                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                                .toJobParameters();
                jobLauncher.run(readMensuelCotisationFromCSVJob(), params);
        }

        public FlatFileItemReader<CreateAndUpdateCotisation> getReaderCotisation(String file) {

                return new FlatFileItemReaderBuilder<CreateAndUpdateCotisation>()
                                .name("cotisationItemReader")
                                .resource(new FileSystemResource(file))
                                .linesToSkip(1)
                                .delimited()
                                .names(getNames())
                                .fieldSetMapper(costumisedMapper())
                                .build();
        }

        private static String[] getNames() {

                return Constants.GETFIELDSS;
        }

        private static FieldSetMapper<CreateAndUpdateCotisation> costumisedMapper() {
                return new FieldSetMapper<CreateAndUpdateCotisation>() {
                        @Override
                        public CreateAndUpdateCotisation mapFieldSet(FieldSet fieldSet) throws BindException {
                        CreateAndUpdateCotisation cotisation = new CreateAndUpdateCotisation();

                        // Map other fields as needed
                        cotisation.setSolde(fieldSet.readBigDecimal("solde").floatValue());
                        cotisation.setNumQuittance(fieldSet.readString("numQuittance"));
                        cotisation.setMontantCotisation(fieldSet.readBigDecimal("montantCotisation").floatValue());
                        cotisation.setMontantTaxe(fieldSet.readBigDecimal("montantTaxe").floatValue());
                        cotisation.setMontantAccessoire(fieldSet.readBigDecimal("montantAccessoire").floatValue());
                        cotisation.setMontantTTC(fieldSet.readBigDecimal("montantTTC").floatValue());
                        cotisation.setFraisAcquisitionTTC(fieldSet.readBigDecimal("fraisAcquisitionTTC").floatValue());
                        cotisation.setFraisGestionTTC(fieldSet.readBigDecimal("fraisGestionTTC").floatValue());
                        cotisation.setContributionPure(fieldSet.readBigDecimal("contributionPure").floatValue());
                        cotisation.setContrat(fieldSet.readString("contrat_id"));
                        cotisation.setEtatCotisation(EtatCotisation.EMIS);
                        String dateString = fieldSet.readString("datePrelevement");
                        if (dateString != null && !dateString.isEmpty()) {
                                String modifiedDateString = dateString.substring(0, 8) + "01";
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date date=new Date();
                                try {
                                        date = dateFormat.parse(modifiedDateString);
                                } catch (ParseException e) {
                                        e.printStackTrace();
                                }
                                cotisation.setDatePrelevement(date);
                                cotisation.setDateEmission(date);
                        }

                        return cotisation;
                        }
                        };
}




        // private static BeanWrapperFieldSetMapper<CreateAndUpdateCotisation> getFieldSetMapper() {
        //         return new BeanWrapperFieldSetMapper<CreateAndUpdateCotisation>() {
        //             {
        //                 setTargetType(CreateAndUpdateCotisation.class);
        //             }
            
        //             @Override
        //             public CreateAndUpdateCotisation mapFieldSet(FieldSet fieldSet) throws BindException {
        //                 CreateAndUpdateCotisation cotisation = super.mapFieldSet(fieldSet);
            
        //                 // If the field is 'datePrelevement', convert the string to a Date
        //                 String dateString = fieldSet.readString("datePrelevement");
        //                 LocalDate localDate = LocalDate.parse(dateString);
        //                 Date date = java.sql.Date.valueOf(localDate);
        //                 cotisation.setDatePrelevement(date);
            
        //                 return cotisation;
        //             }
        //         };
        //     }


}