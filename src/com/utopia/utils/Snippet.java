package com.utopia.utils;

import java.util.UUID;

public class Snippet {
	//产生全世界唯一的id
    public static String generateID(){        
        return UUID.randomUUID().toString();
    }
}
