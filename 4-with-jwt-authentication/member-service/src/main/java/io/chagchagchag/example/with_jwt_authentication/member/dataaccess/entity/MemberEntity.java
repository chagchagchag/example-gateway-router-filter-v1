package io.chagchagchag.example.with_jwt_authentication.member.dataaccess.entity;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table("member")
@AllArgsConstructor(staticName = "ofAll")
public class MemberEntity {
  @Id
  private Long id;

  private String email;

  private String password;

  private String authorities;

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  @PersistenceCreator
  public MemberEntity(
    Long id,
    String email,
    String password,
    String authorities
  ){
    this.id = id;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  @Builder(builderClassName = "CreationBuilder", builderMethodName = "creationBuilder")
  public MemberEntity(
      String email,
      String password,
      String authorities
  ){
    this(null, email, password, authorities);
  }

  public List<String> parseAuthorities(){
    if(authorities.length() > 0){
      return List.of(authorities.split(","));
    }
    else{
      return List.of();
    }
  }
}
