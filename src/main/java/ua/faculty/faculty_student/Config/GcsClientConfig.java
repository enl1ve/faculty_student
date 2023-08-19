package ua.faculty.faculty_student.Config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.FileInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GcsClientConfig {
  @Bean
  public static Storage initializeStorage() {
    try {
      // Путь к JSON-ключу служебного аккаунт
      FileInputStream serviceAccountStream = new FileInputStream("E:\\IdeaProjects\\faculty_student\\src\\main\\resources\\linen-option-396400-6cb89d2e47d3.json");
      GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccountStream);

      return StorageOptions.newBuilder()
          .setCredentials(credentials)
          .build()
          .getService();
    } catch (Exception e) {
      throw new RuntimeException("Failed to initialize Google Cloud Storage client.", e);
    }
  }
}
