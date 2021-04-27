import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        List<File> files = new ArrayList<>();
        List<GameProgress> games = new ArrayList<>();

        GameProgress gp1 = new GameProgress(100, 30, 2, 30);
        GameProgress gp2 = new GameProgress(80, 18, 4, 80);
        GameProgress gp3 = new GameProgress(60, 10, 6, 120);

        File file1 = new File("D://Games//savegames//save1.dat");
        File file2 = new File("D://Games//savegames//save2.dat");
        File file3 = new File("D://Games//savegames//save3.dat");

        files.add(file1);
        files.add(file2);
        files.add(file3);

        games.add(gp1);
        games.add(gp2);
        games.add(gp3);

        saveGame(files, games);
        zipFiles("D://Games//savegames//saveArchive.zip", files);

    }

    public static void saveGame(List<File> files, List<GameProgress> gp) {
        for (int i = 0; i < gp.size(); i++) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(files.get(i)))) {
                oos.writeObject(gp.get(i));
                System.out.println("File" + i + files.get(i).length());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void zipFiles(String zipPath, List<File> zipedFiles) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (int i = 0; i < zipedFiles.size(); i++) {
                byte[] buffer = null;
                try (FileInputStream fis = new FileInputStream(zipedFiles.get(i))) {

                    fis.read(buffer);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                ZipEntry ze = new ZipEntry("Save" + String.valueOf(i + 1));
                zout.putNextEntry(ze);
                zout.write(buffer);
                zout.closeEntry();
                zipedFiles.get(i).delete();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


