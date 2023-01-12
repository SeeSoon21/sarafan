package letscode.sarafan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
@ToString(of = {"id", "text"})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //над классом должна быть аннотация, говорящая, что включаем только это поле... но её нет -- ругается
    @EqualsAndHashCode.Include
    @JsonView(Views.Id.class)
    private Long id;
    @JsonView(Views.IdName.class)
    private String text;

    @Column(updatable = false)
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonView(Views.FullMessage.class)
    private LocalDateTime creationDate;

}
