package ru.sadv1r.openfms;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

/**
 * Created on 5/11/15.
 *
 * @author sadv1r
 * @version 0.1
 */
@Entity
@NamedQuery(name = "VkIdList.getVkIds", query = "SELECT x.vkId FROM VkIdList x")
public class VkIdList implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int VK_MIN_ID = 0;
    private static final int VK_MAX_ID = 1000_000_000;

    @Id
    @Min(value = VK_MIN_ID, message = "минимальный id пользователя должен быть: " + VK_MIN_ID)
    @Max(value = VK_MAX_ID, message = "максимальный id пользователя должен быть: " + VK_MAX_ID)
    private int vkId;

    @ManyToOne
    private VkIdList createdBy;

    @Min(value = 0, message = "минимальный тип, с которым добавили пользователя должен быть: " + 0)
    @Max(value = 10, message = "максимальный тип, с которым добавили пользователя должен быть: " + 10)
    private int type = 0;

    @Min(value = 0, message = "минимальный приоритет должен быть: " + 0)
    @Max(value = 10, message = "максимальный приоритет должен быть: " + 10)
    private int priority = 0;

    private boolean inBlacklist;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;


    public int getVkId() {
        return vkId;
    }

    public void setVkId(int vkId) {
        this.vkId = vkId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isInBlacklist() {
        return inBlacklist;
    }

    public void setInBlacklist(boolean inBlacklist) {
        this.inBlacklist = inBlacklist;
    }

    public Date getCreated() {
        return created;
    }

    @PrePersist
    protected void onCreate() {
        this.created = new Date();
        setInBlacklist(false);
    }
}
