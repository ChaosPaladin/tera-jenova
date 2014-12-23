package com.angelis.tera.game.domain.entity.database;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;

@Entity
@Table(name = "welcome_message")
public class WelcomeMessageEntity extends AbstractDatabaseEntity {

    private static final long serialVersionUID = 3261451305802105303L;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "creation_date")
    private Date creationDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }

}
