package com.util;

import java.util.UUID;  

public class UUIDTools {  
  
    public UUIDTools() {  
        // TODO Auto-generated constructor stub  
    }  
      
    public static String getUUID(){  
          
        UUID uuid = UUID.randomUUID();  
        return uuid.toString().replaceAll("-", "").substring(0, 6);   
          
    }  
  
}  