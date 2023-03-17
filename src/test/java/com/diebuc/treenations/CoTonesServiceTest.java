package com.diebuc.treenations;

import com.diebuc.treenations.adapter.FileDataAdapter;
import com.diebuc.treenations.model.CoTonesData;
import com.diebuc.treenations.service.CoTonesService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CoTonesServiceTest {

    public static final Integer MINIMUM_CO_TONES_COMPENSATION = 200;
    public static final String YEAR = "2022";

    @Test
    void testGetLowImpactMonths() throws IOException {

        FileDataAdapter adapter = Mockito.mock(FileDataAdapter.class);
        when(adapter.readData(anyString())).thenReturn(Arrays.asList(
                new CoTonesData("2022", "MARCH", 200, 100),
                new CoTonesData("2022", "APRIL", 200, 250),
                new CoTonesData("2022", "SEPTEMBER", 200, 190),
                new CoTonesData("2022", "DECEMBER", 200, 300)
        ));

        CoTonesService service = new CoTonesService(adapter);
        String filePath = "2022-COtonesInfo.csv";

        List<CoTonesData> lowImpactMonths = service.getLowImpactMonths(filePath, YEAR, MINIMUM_CO_TONES_COMPENSATION);

        assertNotNull(lowImpactMonths);
        assertEquals(2, lowImpactMonths.size());

        CoTonesData firstLowImpactMonth = lowImpactMonths.get(0);
        assertEquals("2022", firstLowImpactMonth.getYear());
        assertEquals("MARCH", firstLowImpactMonth.getMonth());
        assertEquals(200, firstLowImpactMonth.getMinimumExpected());
        assertEquals(100, firstLowImpactMonth.getCoTonesCompensationReached());

        CoTonesData secondLowImpactMonth = lowImpactMonths.get(1);
        assertEquals("2022", secondLowImpactMonth.getYear());
        assertEquals("SEPTEMBER", secondLowImpactMonth.getMonth());
        assertEquals(200, secondLowImpactMonth.getMinimumExpected());
        assertEquals(190, secondLowImpactMonth.getCoTonesCompensationReached());
    }
}
