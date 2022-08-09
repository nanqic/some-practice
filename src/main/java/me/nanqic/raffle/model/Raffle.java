package me.nanqic.raffle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_raffle")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Raffle implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private String biliName;
    private String prizeName;
    private String raffleStatus;
    private String remark;
    @Column(updatable = false)
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date updateTime;


}
