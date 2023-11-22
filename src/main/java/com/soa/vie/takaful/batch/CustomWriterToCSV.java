package com.soa.vie.takaful.batch;

import com.soa.vie.takaful.entitymodels.Cotisation;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateCotisation;
import com.soa.vie.takaful.util.Constants;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;

public class CustomWriterToCSV extends FlatFileItemWriter<Cotisation> {

    public DelimitedLineAggregator<CreateAndUpdateCotisation> getDelimitedLineAggregator() {
        BeanWrapperFieldExtractor<CreateAndUpdateCotisation> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<CreateAndUpdateCotisation>();

        beanWrapperFieldExtractor
                .setNames(Constants.GETFIELDS);

        DelimitedLineAggregator<CreateAndUpdateCotisation> delimitedLineAggregator = new DelimitedLineAggregator<CreateAndUpdateCotisation>();
        delimitedLineAggregator.setDelimiter(",");
        delimitedLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);
        return delimitedLineAggregator;

    }
}
