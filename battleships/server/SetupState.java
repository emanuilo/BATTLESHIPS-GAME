package battleships.server;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SetupState extends State {

	public SetupState(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String behavior(String s, Player player) {
		
		for(int i=0; i<myGame.players.size(); i++){
			Player receiver=myGame.players.get(i);
			
			for(int j=0; j<myGame.players.size(); j++){
				if(receiver==myGame.players.get(j))
					continue;
				
				StringBuilder sb=new StringBuilder();
				sb.append("IMAGE {")
				  .append(myGame.players.get(j).getName()+"} ")
				  .append("[");
				BufferedImage image=null;
				try {
					image=ImageIO.read(new File(myGame.players.get(j).getName()+".png"));
				} catch (IOException e) {
					try {
						image=ImageIO.read(new File("default.jpg"));
					} catch (IOException e1) {}
				}
				
				boolean first=true;
				for(int k=0;k<64;k++){
					for(int l=0;l<64;l++){
						int rgb=image.getRGB(k, l);
						Color color=new Color(rgb);
						int r=color.getRed();
						int g=color.getGreen();
						int b=color.getBlue();
						if(first==false){
							sb.append(",");
						}
						else
							first=false;
						sb.append(r)
						  .append(",")
						  .append(g)
						  .append(",")
						  .append(b);
					}
				}
				sb.append("]");
				
				System.out.println(sb.toString());
				receiver.reportMessage(sb.toString());
			}
		}
		return null;
	}

}
