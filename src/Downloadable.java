public interface Downloadable {
    String getName();
    long getSize();
    void download() throws InterruptedException;
}
