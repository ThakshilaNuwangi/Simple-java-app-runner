package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainFormController {
    public TextArea txtInput;
    public TextArea txtOutput;
    private boolean compiled = false;
    String tempDir = System.getProperty("java.io.tmpdir");

    public void initialize () {
        txtInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue!=newValue) {
                compiled=false;
            }
        });
    }

    public void btnCompileOnAction(ActionEvent actionEvent) {
        compile();
    }

    public void btnRunOnAction(ActionEvent actionEvent) {
        if (!compiled) {
            txtOutput.setText("Please compile first");
        }
        run(compiled);
    }

    public void btnCompileAndRunOnAction(ActionEvent actionEvent) {
        run(compile());
    }

    private boolean compile() {
        String code = "public class Demo {" +
                "public static void main(String[] args) {" +
                txtInput.getText() +
                "}" +
                "}";


        try {
            Path path = Paths.get(System.getProperty("java.io.tmpdir"), "Demo.java");
            Files.write(path, code.getBytes());
            Process javac = Runtime.getRuntime().exec("javac " + path);
            int exitCode = javac.waitFor();
            if (exitCode == 0) {
                txtOutput.setText("Compiled Successfully");
                compiled = true;
                return true;
            } else {
                readStream(javac.getErrorStream());
                return false;
            }
        } catch (IOException | InterruptedException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, Try again");
            return false;
        } finally {
            System.out.println("ncdkj");
        }
    }

    private void clearMemory(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void run(boolean compiled) {
        if (compiled) {
            try {
                Process java = Runtime.getRuntime().exec("java -cp " + tempDir + " Demo");
                int exitCode = java.waitFor();
                if (exitCode==0) {
                    readStream(java.getInputStream());
                } else {
                    readStream(java.getErrorStream());
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                System.out.println("hdbs");
            } finally {
                clearMemory(Paths.get(System.getProperty("java.io.tmpdir"), "DEP8IDEDemo.java"));
                clearMemory(Paths.get(System.getProperty("java.io.tmpdir"), "Demo.java"));
            }
        }
    }

    private void readStream(InputStream inputStream) {
        try {
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            txtOutput.setText(new String(buffer));
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
