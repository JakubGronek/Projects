import java.io.*;
import java.util.Scanner;

public class FileOperations {
    File myFile;

    public FileOperations(){
        myFile = new File("data.sgf");
        try{
            myFile.createNewFile();
        }catch (IOException ignored){};
    }

    public String readData() {
        String data = "";
        try {
            Scanner read = new Scanner(myFile);
            while (read.hasNextLine()) {
                data += read.nextLine();
            }
            read.close();
        } catch (FileNotFoundException e) {
            System.err.println("File does not exist");
            e.printStackTrace();
        }
        return data;
    }

    public void writeData(String data){
        try{
            FileWriter writer = new FileWriter(myFile);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(data);
            bw.close();
        }catch(IOException ignored){}
    }
}

