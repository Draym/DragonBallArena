package com.andres_k.utils.configs;

/**
 * Created by andres_k on 13/03/2015.
 */
public class CurrentUser {
    private static String pseudo;
    private static String id;
    private static String idTeam;
    private static boolean inGame;

    public static void init(String newPseudo, String newId, String newIdTeam) {
        pseudo = newPseudo;
        id = newId;
        idTeam = newIdTeam;
        inGame = false;
    }

    // GETTERS
    public static String getId() {
        return id;
    }

    public static String getPseudo() {
        return pseudo;
    }

    public static boolean isInGame() {
        return inGame;
    }

    public static String getIdTeam() {
        return idTeam;
    }

    // SETTERS
    public static void setPseudo(String pseudo) {
        CurrentUser.pseudo = pseudo;
    }

    public static void setId(String id) {
        CurrentUser.id = id;
    }

    public static void setInGame(boolean inGame) {
        CurrentUser.inGame = inGame;
    }

    public static void setIdTeam(String idTeam) {
        CurrentUser.idTeam = idTeam;
    }
}
