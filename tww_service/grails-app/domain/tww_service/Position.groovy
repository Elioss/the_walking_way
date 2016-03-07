package tww_service



class Position {
    
    int id;
    String utilisateur;
    double latitude;
    double longitude;

    static constraints = {
        id  (nullable: false)
        utilisateur (nullable: true)
        latitude (nullable: false)
        longitude (nullable: false)
    }
}
