package Entity;

public class Author {
    private int authorID;
    private String authorName, authorDetail;

    public Author(int authorID, String authorName, String authorDetail) {
        this.authorID = authorID;
        this.authorName = authorName;
        this.authorDetail = authorDetail;
    }

    public int getAuthorID() {
        return authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorDetail() {
        return authorDetail;
    }

    public void setAuthorDetail(String authorDetail) {
        this.authorDetail = authorDetail;
    }

}
