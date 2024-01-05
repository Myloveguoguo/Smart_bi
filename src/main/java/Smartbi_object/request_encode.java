package Smartbi_object;


import com.google.common.primitives.Bytes;
import smartbi.util.codeutil.CodeHandler;


public class request_encode {

    private static byte[] encodeArray = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 87, 0, 0, 0, 47, 0, 56, 97, 89, 84, 43, 0, 103, 106, 37, 113, 49, 121, 78, 114, 112, 110, 48, 76, 55, 123, 0, 0, 0, 0, 0, 0, 40, 88, 120, 115, 41, 77, 107, 71, 104, 53, 52, 80, 54, 51, 65, 33, 117, 105, 108, 68, 90, 66, 83, 122, 81, 86, 93, 0, 91, 0, 102, 0, 69, 119, 73, 109, 126, 45, 118, 100, 99, 82, 116, 75, 57, 39, 79, 101, 46, 72, 42, 67, 50, 74, 111, 70, 95, 85, 58, 0, 0, 98, 0};

    public static void test(){
        byte[] ret =  new byte[encodeArray.length];
        for ( byte i: encodeArray){
            if (i>0){
                ret[i] = (byte)Bytes.indexOf(encodeArray,i);
            }
        }
        encodeArray = ret;
    }
    public static byte[] encode(byte[] dataByte) {
        int i = 0;

        for (int j = 0; j < dataByte.length; ++j) {
            byte tmp = dataByte[i];
            if (tmp != 0 && tmp < encodeArray.length) {
                byte decodeChar = encodeArray[tmp];
                if (decodeChar != 0) {
                    dataByte[i] = decodeChar;
                }
            }

            ++i;
        }

        return dataByte;
    }

    public static void main(String[] args) {
        test();
        byte[] dataByte = CodeHandler.strToByteArrayByUTF8("%5B%22system%22%2C%220a%22%5D");
        dataByte = encode(dataByte);

        System.out.println(new String (dataByte));

    }

}
