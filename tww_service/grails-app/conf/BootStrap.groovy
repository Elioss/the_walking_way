import tww_service.Position;

class BootStrap {

    def init = { servletContext ->
        def position1 = new Position(id : 1, latitude : 43.433358, longitude : 6.743820);
        position1.save(flush : true);
    }
    def destroy = {
    }
}
