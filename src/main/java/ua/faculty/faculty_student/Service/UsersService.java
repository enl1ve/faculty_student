package ua.faculty.faculty_student.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
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

  private final UsersRepository usersRepository;
  private String bucketName = "faculty-student-bucket";
  private final AmazonS3 s3Client;

  @Autowired
  public UsersService(UsersRepository usersRepository, AmazonS3 s3Client) {
    this.usersRepository = usersRepository;
    this.s3Client = s3Client;
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
    s3Client.deleteObject(bucketName, user != null ? user.getAvatar() : null);

    File convertedFile = convertMultipartToFile(file);
    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
    s3Client.putObject(new PutObjectRequest(bucketName, fileName, convertedFile));

    String avatarUrl = "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
    user.setAvatar(avatarUrl);
    usersRepository.save(user);
  }

//  public String deleteFile(String fileName) {
//    s3Client.deleteObject(bucketName, fileName);
//    return fileName + "removed";
//  }

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
