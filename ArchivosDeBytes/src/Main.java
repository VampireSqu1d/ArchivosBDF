import java.io.IOException;
import java.io.RandomAccessFile;

public class Main {

    private static void LeeArchivo(RandomAccessFile archivo) throws IOException {
        System.out.println("tipo de archivo: " + archivo.readByte());
        int anio = archivo.readByte() + 1900;
        byte mes = archivo.readByte();
        byte dia = archivo.readByte();
        System.out.printf("%3d / %3d / %5d %n", dia, mes, anio);

        System.out.println("numero de registros: " + Integer.reverseBytes(archivo.readInt()));// imprime el numero de registros
        short header = Short.reverseBytes(archivo.readShort());// varialbe con el tamanio del header
        System.out.println("Tamanio del header: " + header);// imprime el tamanio del header
        System.out.println( "Tamanio del cuerpo: "+ Short.reverseBytes(archivo.readShort()));// imprime el tamanio del body
        int campos = (header - 32) / 32;
        System.out.println("campos: " + campos);
        archivo.skipBytes(20);
        System.out.println("=======================");

        for (int i = 0; i < campos ; i++) {
            byte[] chars = new byte[10];
            for (int j = 0; j < 10; j++)   chars[j] = archivo.readByte();

            archivo.seek(archivo.getFilePointer()  + 1);
            byte[] Campo = new byte[1];
            Campo[0] = archivo.readByte();

            archivo.skipBytes(3);//salta el  byte de long de campo
            short des = archivo.readShort();
            byte decimales = archivo.readByte();//numero de decimales

            System.out.printf("| %s |   %s ,  %4d, %4d %n", new String(chars), new String(Campo), des, decimales );

            archivo.skipBytes(14);// se salta los bytes que no interesan
        }
        System.out.println("=======================");
    }

    public static void main(String[] args) throws IOException {
        RandomAccessFile bytes = new RandomAccessFile(args[0], "r");
        LeeArchivo(bytes);
    }
}
