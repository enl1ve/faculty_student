package ua.faculty.faculty_student.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.faculty.faculty_student.Entity.Users;
import ua.faculty.faculty_student.Repository.UsersRepository;

@Service
public class UsersService {
  private final Storage gcsStorage;

  private final UsersRepository usersRepository;
  private String bucketName = "faculty-student-project";

  @Autowired
  public UsersService(Storage gcsStorage, UsersRepository usersRepository) {
    this.gcsStorage = gcsStorage;
    this.usersRepository = usersRepository;
  }

  public boolean getLogicByUser(String username) {
    return !usersRepository.findAllByUsername(username).isEmpty();
  }

  //Save new user to DB and encrypt pass
  public Users saveUserToDB(Users users) {
    users.setPassword(new BCryptPasswordEncoder().encode(users.getPassword()));

    return usersRepository.save(users);
  }

  public void updateUserInfo(Long id, String fullName, String bio) {
    Users user = usersRepository.findById(id).orElse(null);

    user.setFullName(fullName);
    user.setBio(bio);

    usersRepository.save(user);
  }

  public void updatePhotoUser(Long id, MultipartFile file) {
    Users user = usersRepository.findById(id).orElse(null);

    if (user != null) {
      String currentAvatar = user.getAvatar();
      if (currentAvatar != null) {
        // Удалите текущий аватар из GCS (необязательно)
        String currentObject = currentAvatar.substring(currentAvatar.lastIndexOf('/') + 1);
        gcsStorage.delete(bucketName, currentObject);
      }

      String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
      BlobId blobId = BlobId.of(bucketName, fileName);
      BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

      try {
        byte[] fileBytes = file.getBytes();
        gcsStorage.create(blobInfo, fileBytes);

        String avatarUrl = "https://storage.googleapis.com/" + bucketName + "/" + fileName;
        user.setAvatar(avatarUrl);
        usersRepository.save(user);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private File convertMultipartToFile(MultipartFile file) {
    File convertedFile = new File(file.getOriginalFilename());
    try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
      fos.write(file.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }

    return convertedFile;
  }
}
