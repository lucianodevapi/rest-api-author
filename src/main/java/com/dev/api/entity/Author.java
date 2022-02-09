package com.dev.api.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import com.dev.api.validator.Validator;

@Entity
@Table(name = "autor")
public class Author implements Serializable {

	private static final long serialVersionUID = 7345498188457367182L;

	@Id
	@SequenceGenerator(name = "sequence_author", sequenceName = "sequence_author", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_author")
	@Column(name = "id")
	private Long id;
	@Column(name = "NAME", nullable = false, length = 50)
	private String name;
	@Column(name = "EMAIL", nullable = false, length = 320)
	private String email;
	@Column(name = "LINKEDIN", length = 100)
	private String linkedIn;
	@Column(name = "FACEBOOK", length = 100)
	private String faceBook;
	@Column(name = "TWITTER", length = 100)
	private String twitter;
	@Column(name = "SUMMARY")
	private String summary;

	@Deprecated
    public Author() {
    }

    private Author(@NonNull String name, @NonNull String email) {
        setName(name);
        setEmail(email);
    }

    public static AuthorBuilder builder(@NonNull String name, @NonNull String email) {
        return new AuthorBuilder(name, email);
    }

    public static class AuthorBuilder {

        private final Author author;

        public AuthorBuilder(@NonNull String name, @NonNull String email) {
            this.author = new Author(name, email);
        }

        public AuthorBuilder linkedIn(String linkedIn) {
            this.author.linkedIn = linkedIn;
            return this;
        }

        public AuthorBuilder faceBook(String faceBook) {
            this.author.faceBook = faceBook;
            return this;
        }

        public AuthorBuilder twitter(String twitter) {
            this.author.twitter = twitter;
            return this;
        }

        public AuthorBuilder summary(String summary) {
            this.author.summary = summary;
            return this;
        }

        public Author build() {
            return this.author;
        }

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public String getFaceBook() {
        return faceBook;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getSummary() {
        return summary;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    public void setEmail(String email) {
        this.email = Objects.requireNonNull(email, "email must not be null");
        if (!Validator.isNotEmpty.and(Validator.isEmail).test(email)) {
            throw new IllegalArgumentException("email is not valid");
        }
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public void setFaceBook(String faceBook) {
        this.faceBook = faceBook;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return name.equals(author.name) && email.equals(author.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }
}
