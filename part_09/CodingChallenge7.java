package part_09;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class CodingChallenge7 {
    public static void main(String[] args) throws IOException {

        // Define and initializes the file input stream.
        FileInputStream in = null;
        FileOutputStream out = null;

        try {
            in = new FileInputStream("/home/matthew/Documents/CodingNomads/labs/java_labs_bali/part_09/thefoxandthegrapes.txt");
            out = new FileOutputStream("/home/matthew/Documents/CodingNomads/labs/java_labs_bali/part_09/encrypted.txt");
            int value;

            while ((value = in.read()) != -1) {
                out.write(value);
            }
        }
        finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
