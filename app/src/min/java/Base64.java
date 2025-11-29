package com.example.myketiap;

import android.util.Base64;

public class Base64 {
    public static byte[] decode(String str, int flags) throws IllegalArgumentException {
        return android.util.Base64.decode(str, flags);
    }
    
    public static String encodeToString(byte[] input, int flags) {
        return android.util.Base64.encodeToString(input, flags);
    }
    
    // Exception برای خطاهای decode
    public static class Base64DecoderException extends Exception {
        public Base64DecoderException() {
            super();
        }
        
        public Base64DecoderException(String s) {
            super(s);
        }
        
        private static final long serialVersionUID = 1L;
    }
}