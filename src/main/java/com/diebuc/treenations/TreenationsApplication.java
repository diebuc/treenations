package com.diebuc.treenations;

import com.diebuc.treenations.adapter.DataAdapterFactory;
import com.diebuc.treenations.adapter.FileDataAdapter;
import com.diebuc.treenations.model.CoTonesData;
import com.diebuc.treenations.service.CoTonesService;
import com.diebuc.treenations.util.FileNameHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.List;

/**
 * The TreenationsApplication is the main entry point for the Tree Nations application.
 * It can run both as a CLI tool and a web application.
 */
@SpringBootApplication
public class TreenationsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        if (args.length > 0 && "cli".equalsIgnoreCase(args[0])) {
            if (args.length != 3) {
                System.err.println("Usage: java -jar app.jar cli <file-path> <minimum-CO-tones-compensation>");
                System.exit(1);
            }

            String filePath = args[1];
            int minimumCOTonesCompensation = Integer.parseInt(args[2]);
            String year = FileNameHelper.getYearFromFileName(filePath);
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TreenationsApplication.class);
            DataAdapterFactory dataAdapterFactory = context.getBean(DataAdapterFactory.class);
            FileDataAdapter adapter = dataAdapterFactory.getAdapter(filePath);

            CoTonesService service = new CoTonesService(adapter);
            try {
                List<CoTonesData> lowImpactMonths = service.getLowImpactMonths(filePath, year, minimumCOTonesCompensation);

                System.out.println("| Year |   Month   | Minimum expected | CO tones compensation reached |");
                System.out.println("----------------------------------------------------------------------");
                for (CoTonesData data : lowImpactMonths) {
                    System.out.printf("| %-4s | %-10s | %-16d | %-27d |%n" ,data.getYear(), data.getMonth(), data.getMinimumExpected(), data.getCoTonesCompensationReached());
                }
            } catch (IOException e) {
                System.err.println("Error reading data: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
            }
        } else {
            SpringApplication.run(TreenationsApplication.class, args);
        }
    }
}
