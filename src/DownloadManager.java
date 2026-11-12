import java.util.ArrayList;
import java.util.List;

class DownloadManager {

    private List<Downloadable> files = new ArrayList<>();
    private DownloadListener listener;
    private long totalSize = 0;
    private List<String> finishedFiles = new ArrayList<>();

    public DownloadManager(DownloadListener listener) {
        this.listener = listener;
    }

    public void addFile(Downloadable file) {
        files.add(file);
    }

    public void start() {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    work();
                }
            });
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            try { t.join(); } catch (InterruptedException e) {}
        }
        System.out.println("Загружено: " + finishedFiles);
        System.out.println("Общий размер: " + totalSize + " байт");
    }

    private void work() {
        while (true) {
            Downloadable file = getNextFile();
            if (file == null) break;

            try {
                System.out.println("Старт: " + file.getName());
                file.download();

                synchronized (this) {
                    finishedFiles.add(file.getName());
                    totalSize += file.getSize();
                    System.out.println("Готово: " + file.getName());
                }

                if (listener != null) {
                    listener.downloadComplete(file.getName());
                }
            } catch (InterruptedException e) {
                System.out.println("Прервано: " + file.getName());
            }
        }
    }

    private synchronized Downloadable getNextFile() {
        if (files.isEmpty()) return null;
        return files.remove(0);
    }
}