package resourcerest;
import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.validation.constraints.Null;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/Etudiant")
public class restEtudiant {
    public static EtudiantBusiness etT = new EtudiantBusiness();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant et) {
        if (etT.addEtudiant(et)) {
            return Response.status(200).build();
        } else return Response.status(404).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiant() {
        List<Etudiant> l = etT.getAllEtudiants();
        return Response.status(200).entity(l).build();
    }
    @GET
    @Path("{identifiant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantByIdentifiant(@PathParam("identifiant") String identifiant) {
        Etudiant et = etT.getEtudiantByIdentifiant(identifiant);
        if (et == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(et).build();
    }

    @DELETE
    @Path("{code}")
    public Response deleteEtudiant(@PathParam ("code")String identifiant) {
        if (etT.deleteEtudiant(identifiant)) {
            return Response.status(204).build();
        }
        return Response.status(404).build();

    }
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("id") String identifiant, Etudiant et){
        et.setIdentifiant(identifiant);
        if (etT.updateEtudiant(identifiant,et)){
            return Response.status(200).entity(et).build();
        }
        return Response.status(404).build();
    }
    @GET
    @Path("Opp")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeO) {
        Option op = restOption.optB.getOptionByCode(codeO);
        if (op == null) {
            return Response.status(404).build();
        }

        List<Etudiant> res = new ArrayList<>();
        for (Etudiant et : etT.getEtudiantsByOption(op)) {
            res.add(new Etudiant(et.getIdentifiant(), et.getNom(), et.getPrenom(),
                    null, et.getAnneeEtude(), et.getEmail()));
        }
        return Response.status(200).entity(new EtudiantList(res)).build();
    }

}