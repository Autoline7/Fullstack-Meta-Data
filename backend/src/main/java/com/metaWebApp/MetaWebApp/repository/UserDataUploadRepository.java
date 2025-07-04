package com.metaWebApp.MetaWebApp.repository;

import com.metaWebApp.MetaWebApp.model.User;
import com.metaWebApp.MetaWebApp.model.UserDataUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA repository for the UserDataUpload entity.
 * Provides CRUD operations and custom query methods for UserDataUpload data.
 */
public interface UserDataUploadRepository extends JpaRepository<UserDataUpload, UUID> {

    /**
     * Finds all UserDataUploads associated with a specific user.
     *
     * @param user The User entity whose uploads are to be retrieved.
     * @return A list of UserDataUploads belonging to the user.
     */
    List<UserDataUpload> findByUser(User user);

    /**
     * Finds all UserDataUploads by the ID of the associated user.
     *
     * @param userId The UUID of the user.
     * @return A list of UserDataUploads belonging to the user.
     */
    List<UserDataUpload> findByUserId(UUID userId);

    /**
     * Finds UserDataUploads by their status.
     *
     * @param status The UploadStatus to filter by.
     * @return A list of UserDataUploads matching the given status.
     */
    List<UserDataUpload> findByStatus(String status); // Or UploadStatus enum if preferred
    // If you define UploadStatus enum correctly and use @Enumerated(EnumType.STRING)
    // in the entity, you can use: List<UserDataUpload> findByStatus(UploadStatus status);
}
