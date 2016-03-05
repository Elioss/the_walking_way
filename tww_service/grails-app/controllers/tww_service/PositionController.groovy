package tww_service

import grails.converters.JSON

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.*

@Transactional(readOnly = true)
class PositionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def list(){
        def allPosition = tww_service.Position.list()
        render allPosition as JSON
        
    }
    
    def create_position(id, latitude, longitude) {
        def position = new Position(id, latitude, longitude)
        render position as JSON
        position.save()
    }    
    
    
    
    
    
    
    
    
    
    
    
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Position.list(params), model:[positionInstanceCount: Position.count()]
    }

    def show(Position positionInstance) {
        respond positionInstance
    }

    def create() {
        respond new Position(params)
    }

    @Transactional
    def save(Position positionInstance) {
        if (positionInstance == null) {
            notFound()
            return
        }

        if (positionInstance.hasErrors()) {
            respond positionInstance.errors, view:'create'
            return
        }

        positionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'position.label', default: 'Position'), positionInstance.id])
                redirect positionInstance
            }
            '*' { respond positionInstance, [status: CREATED] }
        }
    }

    def edit(Position positionInstance) {
        respond positionInstance
    }

    @Transactional
    def update(Position positionInstance) {
        if (positionInstance == null) {
            notFound()
            return
        }

        if (positionInstance.hasErrors()) {
            respond positionInstance.errors, view:'edit'
            return
        }

        positionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Position.label', default: 'Position'), positionInstance.id])
                redirect positionInstance
            }
            '*'{ respond positionInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Position positionInstance) {

        if (positionInstance == null) {
            notFound()
            return
        }

        positionInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Position.label', default: 'Position'), positionInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'position.label', default: 'Position'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
