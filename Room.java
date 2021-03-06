import java.awt.image.Kernel;
import java.util.*;

/**
 * @author Louis DESPLANCHE
 */
public class Room
{
    private static HashMap<String, Room> aRoomList;
    private String aDescription;
    private HashMap<String, Room> aExits;
    private String aImageName;
    private ItemList aItems;
    
    /**
     * Constructeur de room
     */
    public Room(final String pDescription, String pImage) {
        this.aDescription = pDescription;
        this.aImageName = pImage;
        this.aExits = new HashMap<>();
        this.aItems = new ItemList();
    }
    
    /**
     * @return description de la salle
     */
    public String getDescription()
    {
        return this.aDescription;
    }
    
    /**
    * Paramètre toutes les salles voisines
    */
    public void setExits(final Room pNorthExit, final Room pEastExit, final Room pSouthExit,
        final Room pWestExit, final Room pTopExit, final Room pDownExit) {
        if(pNorthExit != null) aExits.put("nord", pNorthExit);
        if(pEastExit != null) aExits.put("est", pEastExit);
        if(pSouthExit != null) aExits.put("sud", pSouthExit);
        if(pWestExit != null) aExits.put("ouest", pWestExit);
        if(pTopExit != null) aExits.put("haut", pTopExit);
        if(pDownExit != null) aExits.put("bas", pDownExit);
    }
    
    /**
     * Parametre une salle avec ca direction
     */
    public void setExit(final String pDirection, final Room pNeighbor)
    {
        aExits.put(pDirection, pNeighbor);
    }
    
    /**
     * @return Recupere la salle spécifié
     */
    public Room getExit(final String pDirection)
    {
        return aExits.get(pDirection);
    }

    /**
     *
     * @return Liste les sorties de la piece associées aux point cardinaux
     */
    public HashMap<String, Room> getExits() {
        return aExits;
    }
    
    /**
     * Récupere la liste des salles disponibles
     */
    public String getExitString() {
        StringBuilder vRes = new StringBuilder(10 + aExits.size()*10);
        vRes.append("Sorties : ");
        Set<String> vKeys = aExits.keySet();
        for(String vExit : vKeys)
        {
            vRes.append(" " + vExit);
        }
        return vRes.toString();
    }
    
    /**
     * Récupere la liste des salles disponibles
     */
    public String getItemString() {
        return "Items : " + aItems.toString();
    }

    /**
     * Ajoute un item à la list des objets
     * @param pItem
     */
    public void addItem(final Item pItem) {
        aItems.add(pItem);
    }

    /**
     * Recupere un objet de la salle
     * @return
     */
    public Item getItem(final String pName) {
        return this.aItems.getByName(pName);
    }

    /**
     * Retire un objet à la liste des objets
     * @param pItem
     */
    public void removeItem(final Item pItem) {
        aItems.remove(pItem);
    }


    
    /**
     * Créé les salles et les place en mémoires
     */
    public static void CreateRoom() {
        aRoomList = new HashMap<>();
        
        //Piece maison
        aRoomList.put("jardin", new Room("devant chez vous, dans le jardin", "images/jardin.jpg"));
        aRoomList.put("salon", new Room("dans votre lieu de vie, très agréable pour écouter les nouvelles à la radio", "images/salon.jpg"));
        aRoomList.put("cuisine", new Room("dans votre cuisine très bien équipé", "images/cuisine.jpg"));
        aRoomList.put("chambre", new Room("dans votre chambre : une pièce austère mais confortable", "images/chambre.jpg"));
        
        //Extérieur
        aRoomList.put("rue", new Room("dans une grande rue très large mais personne ne s'y promène...", "images/rue.jpg"));
        aRoomList.put("egouts", new Room("dans les égouts, un endroit sal et mal odorant", "images/egout.jpg"));
        aRoomList.put("tour", new Room("dans la tour : certainement l'endroit le plus haut de la ville", "images/hall.jpg"));
        aRoomList.put("bibliotheque", new Room("dans la Grande Bibliothèque : pièce refermant tous le savoir d'une civilisation", ""));
        
        //Ministère
        aRoomList.put("ministere", new Room("dans le hall du ministère : salle grise avec un bureau et un standardiste", "images/hall.jpg"));
        aRoomList.put("bureau", new Room("dans le bureau du ministre orné de souvenir du coup d'état", "images/bureau.jpg"));
        
        getRoom("jardin").setExit("est", getRoom("rue"));
        getRoom("jardin").setExit("ouest", getRoom("salon"));
        getRoom("jardin").addItem(new Item(0,"Un caillou (inutile)", "Caillou"));
        getRoom("jardin").addItem(new Item(0,"cookie magic", "cookie"));
        
        getRoom("salon").setExit("est", getRoom("jardin"));
        getRoom("salon").setExit("sud", getRoom("cuisine"));
        getRoom("salon").setExit("haut", getRoom("chambre"));
        getRoom("salon").addItem(new Item(1,"Un verre (inutile)", "Verre"));
        
        getRoom("cuisine").setExit("nord", getRoom("salon"));
        
        getRoom("chambre").setExit("bas", getRoom("salon"));
        
        getRoom("rue").setExit("ouest", getRoom("jardin"));
        getRoom("rue").setExit("est", getRoom("ministere"));
        getRoom("rue").setExit("haut", getRoom("tour"));

        getRoom("egouts").setExit("nord", getRoom("bibliotheque"));
        getRoom("egouts").setExit("haut", getRoom("rue"));

        getRoom("tour").setExit("bas", getRoom("rue"));
        getRoom("tour").addItem(new Item(99,"Epée certainement médiéval", "Epée"));
        getRoom("tour").addItem(new Beamer(2, "Téléporteur magique", "Téléporteur"));
        
        getRoom("bibliotheque").setExit("sud", getRoom("egouts"));
        
        getRoom("ministere").setExit("haut", getRoom("bureau"));
        getRoom("ministere").setExit("ouest", getRoom("rue"));
        
        getRoom("bureau").setExit("bas", getRoom("ministere"));
        getRoom("bureau").addItem(new Beamer(2, "Carte", "Carte", getRoom("egouts")));
    }
    
    /**
     * Récupere la salle à la direction donnée
     */
    public static Room getRoom(final String pId) {
        return aRoomList.get(pId);
    }
    
    /**
     * Return a string describing the room's image name
     */
    public String getImageName() {
        return aImageName;
    }

    public boolean isExit(final Room pRoom) {
        for (Map.Entry<String, Room> vExit : aExits.entrySet()){
            if(vExit.getValue() == pRoom) return true;
        }
        return false;
    }
} // Room
