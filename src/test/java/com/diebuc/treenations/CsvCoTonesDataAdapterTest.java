package com.diebuc.treenations;

import com.diebuc.treenations.adapter.CsvDataAdapter;
import com.diebuc.treenations.model.CoTonesData;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvCoTonesDataAdapterTest {

    @Test
    void testReadCoTonesData() throws IOException {
        Resource resource = new ClassPathResource("2022-COtonesInfo.csv");
        CsvDataAdapter adapter = new CsvDataAdapter();

        String filePath = resource.getFile().getAbsolutePath();
        List<CoTonesData> coTonesDataList = adapter.readData(filePath);

        assertNotNull(coTonesDataList);
        assertEquals(12, coTonesDataList.size());

        CoTonesData firstMonth = coTonesDataList.get(0);
        assertEquals("January", firstMonth.getMonth());
        assertEquals(200, firstMonth.getCoTonesCompensationReached());
    }
}
