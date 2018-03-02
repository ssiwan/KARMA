package com.stanfieldsystems.karma.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SpaceHistory.
 */
@Entity
@Table(name = "space_history")
public class SpaceHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_accessed", nullable = false)
    private ZonedDateTime dateAccessed;

    @ManyToOne
    private Space space;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateAccessed() {
        return dateAccessed;
    }

    public SpaceHistory dateAccessed(ZonedDateTime dateAccessed) {
        this.dateAccessed = dateAccessed;
        return this;
    }

    public void setDateAccessed(ZonedDateTime dateAccessed) {
        this.dateAccessed = dateAccessed;
    }

    public Space getSpace() {
        return space;
    }

    public SpaceHistory space(Space space) {
        this.space = space;
        return this;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public User getUser() {
        return user;
    }

    public SpaceHistory user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SpaceHistory spaceHistory = (SpaceHistory) o;
        if (spaceHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), spaceHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SpaceHistory{" +
            "id=" + getId() +
            ", dateAccessed='" + getDateAccessed() + "'" +
            "}";
    }
}
