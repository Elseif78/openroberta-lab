package de.fhg.iais.roberta.javaServer.restServices.all;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import de.fhg.iais.roberta.robotCommunication.RobotCommunicator;

@Path("/robotdata")
public class RobotData {
    private static final Logger LOG = LoggerFactory.getLogger(RobotData.class);
    private RobotCommunicator robotCommunicator;

    @Inject
    public RobotData(RobotCommunicator robotCommunicator) {
        this.robotCommunicator = robotCommunicator;
    }

    @Path("/summary")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response returnSummary(@QueryParam("log") String log) throws Exception {
        String summary = this.robotCommunicator.getSummaryOfRobotCommunicator();
        if ( log != null && log.equalsIgnoreCase("true") ) {
            LOG.info(summary);
        }
        return Response.ok(summary).build();
    }

    @Path("/detail")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response returnDetail() throws Exception {
        String detail = this.robotCommunicator.getDetailsOfRobotConnections();
        if ( detail.equals("") ) {
            LOG.info("no robots connected");
        } else {
            LOG.info("details of the robots connected:\n" + detail);
        }
        return Response.ok("done and written to the log file").build();
    }
}