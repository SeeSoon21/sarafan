package letscode.sarafan.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "usr")
@Getter
@Setter
public class User implements Serializable {
    @Id
    private String id;
    //поля, которые будут приходить от гугла(id он тоже генерирует сам)
    private String name;
    private String userpic;
    private String email;
    private String gender;
    private String locale;
    //а вот это поле заполняем вручную
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private LocalDateTime lastVisit;
}
