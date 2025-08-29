package util;

import java.io.*;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class BackupUtil {

    public static void criarBackup(String pastaData, File arquivoZipDestino) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(arquivoZipDestino))) {
            Path base = Paths.get(pastaData);
            if (!Files.exists(base)) throw new FileNotFoundException("Pasta de dados não encontrada: " + pastaData);

            Files.walk(base).forEach(path -> {
                if (Files.isDirectory(path)) return;
                String entryName = base.relativize(path).toString().replace("\\", "/");
                try (InputStream in = Files.newInputStream(path)) {
                    ZipEntry entry = new ZipEntry(entryName);
                    zos.putNextEntry(entry);
                    in.transferTo(zos);
                    zos.closeEntry();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
        }
    }

    public static void restaurarBackup(String pastaData, File arquivoZip) throws IOException {
        Path base = Paths.get(pastaData);
        Files.createDirectories(base);

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(arquivoZip))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Path outPath = base.resolve(entry.getName()).normalize();
                Files.createDirectories(outPath.getParent());
                try (OutputStream out = Files.newOutputStream(outPath)) {
                    zis.transferTo(out);
                }
                zis.closeEntry();
            }
        }
    }
}
