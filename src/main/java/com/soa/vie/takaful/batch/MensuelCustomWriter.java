package com.soa.vie.takaful.batch;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateCotisation;
import com.soa.vie.takaful.util.Constants;

@Component
public class MensuelCustomWriter extends FlatFileItemWriter<CreateAndUpdateCotisation> {

    public MensuelCustomWriter() {

        setResource(new FileSystemResource(Constants.MENSUEL_COTISATION_FILE));
        setHeaderCallback(new StringHeaderWriter(Constants.PROPERTY_CSV_EXPORT_FILE_HEADER));
        setLineAggregator(new CustomWriterToCSV().getDelimitedLineAggregator());
    }
}
