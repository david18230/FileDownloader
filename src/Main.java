public class Main {
    public static void main(String[] args) {

        DownloadListener listener = new DownloadListener() {
            @Override
            public void downloadComplete(String fileName) {
                System.out.println(fileName + " загружен!");
            }
        };

        DownloadManager manager = new DownloadManager(listener);

        manager.addFile(new ImageFile("photo.jpg", 5000));
        manager.addFile(new VideoFile("movie.mp4", 100000));
        manager.addFile(new DocumentFile("doc.pdf", 2000));
        manager.addFile(new ImageFile("avatar.png", 1000));
        manager.addFile(new VideoFile("clip.mp4", 50000));

        Thread managerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                manager.start();
            }
        });
        managerThread.start();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("прерываю одну загрузку");
        for (Thread t : Thread.getAllStackTraces().keySet()) {

            if (t.isAlive() && t != Thread.currentThread() && t != managerThread) {
                t.interrupt();
                break;
            }
        }

        try {
            managerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}