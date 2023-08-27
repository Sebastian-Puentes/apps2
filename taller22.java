import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class taller22 {

    public static void main(String[] args) {

        String rutaCancion = "C:\\Users\\Sebastian Puentes\\OneDrive\\Escritorio\\TRABAJOS UNIVERSIDAD TERCER SEMESTRE\\musica/si.wav";

        Thread reproduccionThread = new Thread(new Reproduccion(rutaCancion));
        reproduccionThread.start();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingresa tu nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingresa tu profesión: ");
        String profesion = scanner.nextLine();

        System.out.print("Ingresa tu canción favorita: ");
        String cancionFavorita = scanner.nextLine();
    }
}

class Reproduccion implements Runnable {
    private String rutaCancion;

    public Reproduccion(String rutaCancion) {
        this.rutaCancion = rutaCancion;
    }

    @Override
    public void run() {
        try {
            
            File archivoCancion = new File(rutaCancion);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivoCancion);

            AudioFormat formatoAudio = audioInputStream.getFormat();

            SourceDataLine lineaDeSalida = AudioSystem.getSourceDataLine(formatoAudio);
            lineaDeSalida.open(formatoAudio);
            lineaDeSalida.start();

            byte[] buffer = new byte[4096];
            int bytesRead = 0;

            while ((bytesRead = audioInputStream.read(buffer)) != -1) {
                lineaDeSalida.write(buffer, 0, bytesRead);
            }

            lineaDeSalida.drain();
            lineaDeSalida.close();

            audioInputStream.close();

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}

