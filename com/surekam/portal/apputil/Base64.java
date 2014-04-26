package com.surekam.portal.apputil;


public final class Base64
{

    private static final byte BASE64_ALPHABET[];
    private static final byte LOOKUP_BASE64_ALPHABET[];

    private Base64()
    {
    }

    public static String decode(String data){
      return new String(decode(data.getBytes()));
    }

    public static byte[] decode(byte base64Data[])
    {
        if(base64Data.length == 0)
            return new byte[0];
        int numberQuadruple = base64Data.length / 4;
        byte decodedData[] = null;
        byte b1 = 0;
        byte b2 = 0;
        byte b3 = 0;
        byte b4 = 0;
        byte marker0 = 0;
        byte marker1 = 0;
        int encodedIndex = 0;
        int dataIndex = 0;
        int lastData;
        for(lastData = base64Data.length; base64Data[lastData - 1] == 61;)
            if(--lastData == 0)
                return new byte[0];

        decodedData = new byte[lastData - numberQuadruple];
        for(int i = 0; i < numberQuadruple; i++)
        {
            dataIndex = i * 4;
            marker0 = base64Data[dataIndex + 2];
            marker1 = base64Data[dataIndex + 3];
            b1 = BASE64_ALPHABET[base64Data[dataIndex]];
            b2 = BASE64_ALPHABET[base64Data[dataIndex + 1]];
            if(marker0 != 61 && marker1 != 61)
            {
                b3 = BASE64_ALPHABET[marker0];
                b4 = BASE64_ALPHABET[marker1];
                decodedData[encodedIndex] = (byte)(b1 << 2 | b2 >> 4);
                decodedData[encodedIndex + 1] = (byte)((b2 & 0xf) << 4 | b3 >> 2 & 0xf);
                decodedData[encodedIndex + 2] = (byte)(b3 << 6 | b4);
            } else
            if(marker0 == 61)
                decodedData[encodedIndex] = (byte)(b1 << 2 | b2 >> 4);
            else
            if(marker1 == 61)
            {
                b3 = BASE64_ALPHABET[marker0];
                decodedData[encodedIndex] = (byte)(b1 << 2 | b2 >> 4);
                decodedData[encodedIndex + 1] = (byte)((b2 & 0xf) << 4 | b3 >> 2 & 0xf);
            }
            encodedIndex += 3;
        }

        return decodedData;
    }

    public static String encode (String data){
      return new String(encode(data.getBytes()));
    }
    public static byte[] encode(byte binaryData[])
    {
        int lengthDataBits = binaryData.length * 8;
        int fewerThan24bits = lengthDataBits % 24;
        int numberTriplets = lengthDataBits / 24;
        byte encodedData[] = null;
        if(fewerThan24bits != 0)
            encodedData = new byte[(numberTriplets + 1) * 4];
        else
            encodedData = new byte[numberTriplets * 4];
        byte k = 0;
        byte l = 0;
        byte b1 = 0;
        byte b2 = 0;
        byte b3 = 0;
        int encodedIndex = 0;
        int dataIndex = 0;
        int i = 0;
        for(i = 0; i < numberTriplets; i++)
        {
            dataIndex = i * 3;
            b1 = binaryData[dataIndex];
            b2 = binaryData[dataIndex + 1];
            b3 = binaryData[dataIndex + 2];
            l = (byte)(b2 & 0xf);
            k = (byte)(b1 & 0x3);
            encodedIndex = i * 4;
            byte val1 = (b1 & 0xffffff80) != 0 ? (byte)(b1 >> 2 ^ 0xc0) : (byte)(b1 >> 2);
            byte val2 = (b2 & 0xffffff80) != 0 ? (byte)(b2 >> 4 ^ 0xf0) : (byte)(b2 >> 4);
            byte val3 = (b3 & 0xffffff80) != 0 ? (byte)(b3 >> 6 ^ 0xfc) : (byte)(b3 >> 6);
            encodedData[encodedIndex] = LOOKUP_BASE64_ALPHABET[val1];
            encodedData[encodedIndex + 1] = LOOKUP_BASE64_ALPHABET[val2 | k << 4];
            encodedData[encodedIndex + 2] = LOOKUP_BASE64_ALPHABET[l << 2 | val3];
            encodedData[encodedIndex + 3] = LOOKUP_BASE64_ALPHABET[b3 & 0x3f];
        }

        dataIndex = i * 3;
        encodedIndex = i * 4;
        if(fewerThan24bits == 8)
        {
            b1 = binaryData[dataIndex];
            k = (byte)(b1 & 0x3);
            byte val1 = (b1 & 0xffffff80) != 0 ? (byte)(b1 >> 2 ^ 0xc0) : (byte)(b1 >> 2);
            encodedData[encodedIndex] = LOOKUP_BASE64_ALPHABET[val1];
            encodedData[encodedIndex + 1] = LOOKUP_BASE64_ALPHABET[k << 4];
            encodedData[encodedIndex + 2] = 61;
            encodedData[encodedIndex + 3] = 61;
        } else
        if(fewerThan24bits == 16)
        {
            b1 = binaryData[dataIndex];
            b2 = binaryData[dataIndex + 1];
            l = (byte)(b2 & 0xf);
            k = (byte)(b1 & 0x3);
            byte val1 = (b1 & 0xffffff80) != 0 ? (byte)(b1 >> 2 ^ 0xc0) : (byte)(b1 >> 2);
            byte val2 = (b2 & 0xffffff80) != 0 ? (byte)(b2 >> 4 ^ 0xf0) : (byte)(b2 >> 4);
            encodedData[encodedIndex] = LOOKUP_BASE64_ALPHABET[val1];
            encodedData[encodedIndex + 1] = LOOKUP_BASE64_ALPHABET[val2 | k << 4];
            encodedData[encodedIndex + 2] = LOOKUP_BASE64_ALPHABET[l << 2];
            encodedData[encodedIndex + 3] = 61;
        }
        return encodedData;
    }

    public static boolean isArrayByteBase64(byte arrayOctect[])
    {
        int length = arrayOctect.length;
        if(length == 0)
            return true;
        for(int i = 0; i < length; i++)
            if(!isBase64(arrayOctect[i]))
                return false;

        return true;
    }

    static boolean isBase64(byte octect)
    {
        return octect == 61 || BASE64_ALPHABET[octect] != -1;
    }


    static
    {
        BASE64_ALPHABET = new byte[255];
        LOOKUP_BASE64_ALPHABET = new byte[64];
        for(int i = 0; i < 255; i++)
            BASE64_ALPHABET[i] = -1;

        for(int i = 90; i >= 65; i--)
            BASE64_ALPHABET[i] = (byte)(i - 65);

        for(int i = 122; i >= 97; i--)
            BASE64_ALPHABET[i] = (byte)((i - 97) + 26);

        for(int i = 57; i >= 48; i--)
            BASE64_ALPHABET[i] = (byte)((i - 48) + 52);

        BASE64_ALPHABET[43] = 62;
        BASE64_ALPHABET[47] = 63;
        for(int i = 0; i <= 25; i++)
            LOOKUP_BASE64_ALPHABET[i] = (byte)(65 + i);

        int i = 26;
        for(int j = 0; i <= 51; j++)
        {
            LOOKUP_BASE64_ALPHABET[i] = (byte)(97 + j);
            i++;
        }

      i = 52;
        for(int j = 0; i <= 61; j++)
        {
            LOOKUP_BASE64_ALPHABET[i] = (byte)(48 + j);
            i++;
        }

        LOOKUP_BASE64_ALPHABET[62] = 43;
        LOOKUP_BASE64_ALPHABET[63] = 47;
    }
}
