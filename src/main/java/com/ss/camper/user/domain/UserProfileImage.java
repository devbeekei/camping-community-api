package com.ss.camper.user.domain;

import com.ss.camper.uploadFile.domain.UploadFile;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuperBuilder
@Getter
@AllArgsConstructor
@DiscriminatorValue("USER_PROFILE")
@Entity
public class UserProfileImage extends UploadFile {

}
