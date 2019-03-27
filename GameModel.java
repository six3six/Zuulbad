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
    private Room aCurrentRoom;
    private Stack<Room> aPreviousRooms;
    private UserInterface aGui;

    static private Room sStartRoom;
    
    public GameModel()
    {
        Room.CreateRoom();

        sStartRoom = Room.getRoom("jardin");
        aCurrentRoom = sStartRoom;
        aPreviousRooms = new Stack<>();
    }

    public Room getCurrentRoom()
    {
        return aCurrentRoom;
    }

    public Room getPreviousRoom()
    {
        return aPreviousRooms.peek();
    }

    public void goBack()
    {
        if(!aPreviousRooms.empty()) aCurrentRoom = aPreviousRooms.pop();
        else aCurrentRoom = sStartRoom;
        sendSignal();
    }

    public void goRoom(Room nextRoom)
    {
        aPreviousRooms.push(aCurrentRoom);
        aCurrentRoom = nextRoom;
        sendSignal();
    }

    public String getImageName()
    {
        return aCurrentRoom.getImageName();
    }

    public String getWelcomeString() 
    {
        return "Bienvenue dans Antisophia" + "\n" + 
        "Si besoin, appuyez sur 'Aide' \n";
    }

    public String getGoodByeString()
    {
        return "Merci d'avoir joué, au revoir";
    }

    public String getHelpString()
    {
        return "Vous êtes dans un état totalitaire" + "\n" +
        "Votre but, renverser le gouvernement grace à la culture";
    }

    public String getLocationInfo() {
        return "Vous êtes " + getCurrentRoom().getDescription() + "\n" +
        getCurrentRoom().getExitString() + "\n" +
        getCurrentRoom().getItemString();
    }

    public void setGUI(final UserInterface pGui)
    {
        this.aGui = pGui;
    }

    //Signal au GUI qu'une modification vient d'être effectué
    private void sendSignal()
    {
        if(aGui != null)
            aGui.update();
    }
}