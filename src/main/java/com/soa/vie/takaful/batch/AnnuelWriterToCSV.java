package com.soa.vie.takaful.batch;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateCotisation;
import com.soa.vie.takaful.util.Constants;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
public class AnnuelWriterToCSV extends FlatFileItemWriter<CreateAndUpdateCotisation> {

    public AnnuelWriterToCSV() {
        setResource(new FileSystemResource(Constants.ANNUEL_COTISATION_FILE));
        setHeaderCallback(new StringHeaderWriter(Constants.PROPERTY_CSV_EXPORT_FILE_HEADER));
        setLineAggregator(new CustomWriterToCSV().getDelimitedLineAggregator());
    }
}
