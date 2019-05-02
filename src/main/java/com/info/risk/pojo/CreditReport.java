package com.info.risk.pojo;

import com.info.risk.pojo.Advice;
import com.info.risk.pojo.CreditMessage;
import com.info.risk.pojo.CreditUser;
import com.info.risk.pojo.Reason;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.*;

/**
 * Created by zhang on 2017-11-06.
 */
@Document(collection = "CreditReport")
public class CreditReport implements Serializable {
    private static final long serialVersionUID = -5805931227153505971L;
    @Id
    private String id;

    private String identityCard;

    //@DBRef
    private CreditUser creditUser;

    @DBRef
    private Map<String, CreditMessage> creditMessages;

    private Advice advice;
    private Set<Reason> reasons;
    private String explain;

    @CreatedDate
    private Date createTime = new Date();
    @LastModifiedDate
    private Date updateTime = new Date();

    public void addReason(Reason reason) {
        if (this.reasons == null) {
            this.reasons = new TreeSet<Reason>();
        }
        this.reasons.add(reason);
    }

    public void addCreditMessage(CreditMessage creditMessage) {

        if (this.creditMessages == null) {
            this.creditMessages = new TreeMap<String, CreditMessage>();
        }
        this.creditMessages.put(creditMessage.getSupplier(), creditMessage);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public CreditUser getCreditUser() {
        return creditUser;
    }

    public void setCreditUser(CreditUser creditUser) {
        this.creditUser = creditUser;
    }

    public Map<String, CreditMessage> getCreditMessages() {
        return creditMessages;
    }

    public void setCreditMessages(Map<String, CreditMessage> creditMessages) {
        this.creditMessages = creditMessages;
    }

    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public Set<Reason> getReasons() {
        return reasons;
    }

    public void setReasons(Set<Reason> reasons) {
        this.reasons = reasons;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    @Override
    public String toString() {
        return "CreditReport{" +
                "id='" + id + '\'' +
                ", identityCard='" + identityCard + '\'' +
                ", creditUser=" + creditUser +
                ", creditMessages=" + creditMessages +
                ", advice=" + advice +
                ", reasons=" + reasons +
                ", explain='" + explain + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
