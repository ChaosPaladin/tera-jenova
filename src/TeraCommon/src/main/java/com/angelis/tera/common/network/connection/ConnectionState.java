package com.angelis.tera.common.network.connection;

public enum ConnectionState {
    /** Client initialization */
    NONE,
    
    /** Client has been accepted by AcceptProcessor*/
    CONNECTED,
    
    /** Client has sent crypt keys and is authenticated*/
    AUTHENTICATED;
}
