public class VideoFile implements Downloadable{
    private String name;
    private long size;

    public VideoFile(String name, long size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public void download() throws InterruptedException {
        Thread.sleep(2000);
    }
}
