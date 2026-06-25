package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions", "hooks"},
        tags = "@buying",
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber-report.json"
        }
)
public class TestRunner {

        @BeforeClass
        public static void cleanOldRecordings() throws IOException {
                Path recordingsDir = Paths.get("evidence", "recordings");

                if (!Files.exists(recordingsDir)) {
                        Files.createDirectories(recordingsDir);
                        return;
                }

                try (Stream<Path> files = Files.list(recordingsDir)) {
                        files
                                .filter(path -> path.toString().endsWith(".mp4"))
                                .forEach(path -> {
                                        try {
                                                Files.deleteIfExists(path);
                                        } catch (IOException e) {
                                                throw new RuntimeException("Failed to delete old recording: " + path, e);
                                        }
                                });
                }
        }
}