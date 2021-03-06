import java.util.HashMap;
import java.util.Observable;
import java.util.Stack;

/**
 * Model
 *
 * @author Louis DESPLANCHE
 * @version 1.0
 */
public class GameModel
{
    private Player aPlayer;
    private UserInterface aGui;

    private Room sStartRoom;

    /**
     *
     */
    public GameModel() {
        Room.CreateRoom();

        sStartRoom = Room.getRoom("jardin");

        this.aPlayer = new Player("Joueur 1", sStartRoom);
    }

    /**
     * Recupere la salle actuelle
     * @return
     */
    public Room getCurrentRoom() {
        return this.aPlayer.getCurrentRoom();
    }

    /**
     * Retourne à la salle précédente
     */
    public void goBack() {
        aGui.println(aPlayer.goBack());
        sendSignal();
    }

    /**
     * Change de salle
     * @param nextRoom
     */
    public void goRoom(Room nextRoom) {
        this.aPlayer.changeCurrentRoom(nextRoom);
        sendSignal();
    }

    /**
     * Récupère l'image de la salle actuelle
     * @return
     */
    public String getImageName() {
        return this.aPlayer.getCurrentRoom().getImageName();
    }

    /**
     * Retourne le message de bienvenue
     * @return
     */
    public String getWelcomeString()  {
        return "Bienvenue dans Antisophia" + "\n" + 
        "Si besoin, appuyez sur '" + CommandWord.HELP + "' \n";
    }

    /**
     * Retourne le message d'aide
     * @return
     */
    public String getHelpString() {
        return "Vous êtes dans un état totalitaire" + "\n" +
        "Votre but, renverser le gouvernement grace à la culture";
    }

    /**
     * Retourne les informations de la salle actuelle
     * @return
     */
    public String getLocationInfo() {
        return "Vous êtes " + getCurrentRoom().getDescription() + "\n" +
        getCurrentRoom().getExitString() + "\n" +
        getCurrentRoom().getItemString() + "\n" +
        "il vous reste " + (Player.sTimeLimit - getPlayer().getCurrentTime()) + " déplacement(s)";
    }

    /**
     * Retourne la liste des items du joueur
     * @return
     */
    public String getInventoryString() {
        return "Inventaire : " + this.aPlayer.itemList();
    }

    /**
     * Change l'affichage
     * @param pGui
     */
    public void setGUI(final UserInterface pGui) {
        this.aGui = pGui;
    }

    /**
     * Vérifie sur le point cardinal entré est une sortie
     * @param pDir
     * @return
     */
    public boolean isExit(final String pDir) {
        return this.aPlayer.getCurrentRoom().getExit(pDir) != null;
    }

    /**
     * Ajoute un objet au joueur
     * @param pItem
     */
    public void addItem(final Item pItem) throws Exception {
        this.aPlayer.addItem(pItem);
    }

    /**
     * Enleve un item au joueur
     */
    public void removeItem(final Item pItem) {
        this.aPlayer.removeItem(pItem);
    }

    /**
     * Enleve un item au joueur grace à son nom
     */
    public void removeItem(final String pName) {
        this.aPlayer.removeItem(pName);
    }

    public void increasePower(final int pFactor) {
        aPlayer.setMaxWeigth(aPlayer.getMaxWeigth() * (double) pFactor);
    }

    /**
     * Recupere un objet du joueur
     * @return
     */
    public Item getItem(final String pName) {
        return this.aPlayer.getItemByName(pName);
    }

    public Player getPlayer(){
        return this.aPlayer;
    }

    /**
     * Signal au GUI qu'une modification vient d'être effectué
     */
    private void sendSignal() {
        if(aGui != null)
            aGui.update();
    }
}