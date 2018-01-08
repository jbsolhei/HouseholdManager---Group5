package services;

import analysis.Analyser;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service that analyzes text sentiment
 * @author nilstes
 */
@Path("sentiment")
public class SentimentService {
    
    private static final Logger log = Logger.getLogger(SentimentService.class.getName());

    private static Analyser analyser = new Analyser();

    @POST
    @Consumes("text/plain")
    @Produces("text/plain")
    public int getSentiment(@Context HttpServletRequest req, String text) {
        String userAgent = req.getHeader("User-Agent");
        log.log(Level.INFO, "SentimentService.getSentiment(): text={0}, userAgent={1}", new Object[] {text, userAgent});
        int sentiment = analyser.findSentiment(text);
        log.log(Level.INFO, "Returning {0}", sentiment);
        return sentiment;
    }    
}
