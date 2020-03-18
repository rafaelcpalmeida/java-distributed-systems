package edu.ufp.inf.sd.rmi.diglib.server;

import java.io.Serializable;

/**
 * @author rmoreira
 */
public class Book implements Serializable {

    private String author;
    private String title;

    public Book(String t, String a) {
        author = a;
        title = t;
    }

    @Override
    public String toString() {
        return "Book{" + "author=" + getAuthor() + ", title=" + getTitle() + '}';
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
}