package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Colour;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYardView;

public interface ScoreCalculator {

    int getMrXscore(ScotMask mask);

    int getDetectiveScore(ScotMask mask, int Xloc, Colour player);

    ScotMask PlayerMoves(ScotMask mask, Colour colour, Move move);

}
