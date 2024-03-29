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
    public static final String FIRE="FIRE";
    public static final String FIRE_ACCEPTED="FIRE_ACCEPTED";
    public static final String FIRE_REJECTED="FIRE_REJECTED";
    public static final String GAME_OVER="GAME_OVER";
    public static final String VICTORY="VICTORY";
    public static final String GAME_WON="GAME_WON";
    public static final String NO_VICTORY="NO_VICTORY";
    public static final String ACCESS_DENIED="ACCESS_DENIED";
    public static final String PASSWORD_REQUIRED="PASSWORD_REQUIRED";
    public static final String DUPLICATE_NAME="DUPLICATE_NAME";
    public static final String IMAGE_MESSAGE="IMAGE";
}
