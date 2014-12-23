package com.angelis.tera.game.process.model;

import java.util.Date;

import com.angelis.tera.common.process.model.AbstractModel;

public class WelcomeMessage extends AbstractModel {

    private String title;

    private String content;

    private Date creationDate;

    public WelcomeMessage() {
        super(null);
    }
    
    public WelcomeMessage(final Date creationDate) {
        super(null);
        this.title = "";
        this.content = "";
        this.creationDate = creationDate;
    }

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
