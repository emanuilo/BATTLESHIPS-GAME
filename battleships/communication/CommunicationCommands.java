/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.communication;

/**
 *
 * @author POOP
 */
public class CommunicationCommands 
{
    private CommunicationCommands() { }
    public static final String JOIN_MESSAGE = "JOIN";
    public static final String WELCOME_MESSAGE = "WELCOME";
    public static final String QUIT_MESSAGE = "QUIT";
    public static final String QUIT_RESPONSE = "BYE";
    public static final String STATE_REQUEST = "STATE";
    public static final String LAYOUT_MESSAGE="SHIP_LAYOUT";
    public static final String LAYOUT_ACCEPTED="LAYOUT_ACCEPTED";
    public static final String LAYOUT_REJECTED="LAYOUT_REJECTED";
}
