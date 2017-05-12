package Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class Teams {
	
    static Text Team1 = Text.of(TextColors.DARK_GRAY, "<", TextColors.RED, "Spetsnaz", TextColors.DARK_GRAY, ">");
    
    static Text Team2 = Text.of(TextColors.DARK_RED, "<", TextColors.DARK_AQUA, "Tropas", TextColors.DARK_RED, ">");
    
    static Text Team3 = Text.of(TextColors.GOLD, "<", TextColors.RED, "N.V.A", TextColors.GOLD, ">");
    
    static Text Team4 = Text.of(TextColors.GRAY, "<", TextColors.BLUE, "Black Ops", TextColors.GRAY, ">");
    
    static Text Team5 = Text.of(TextColors.GRAY, "<", TextColors.DARK_GREEN, "S.O.G", TextColors.GRAY, ">");
    
    static Text Team6 = Text.of(TextColors.BLUE, "<", TextColors.WHITE, "Op 40", TextColors.BLUE, ">");
    
    public static List<Text> Teams = new ArrayList<Text>(Arrays.asList(Team1, Team2, Team3, Team4, Team5, Team6));
    
}
