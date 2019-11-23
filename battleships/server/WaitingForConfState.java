package battleships.server;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import battleships.communication.CommunicationCommands;

public class WaitingForConfState extends State {

	public WaitingForConfState(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String behavior(String s, Player player) {
		String []parts=s.trim().split(" ");
		
		if(parts[0].equals(CommunicationCommands.STATE_REQUEST)){
			StringBuilder sb=new StringBuilder();
			sb.append("WFP ")
			.append(myGame.players.size())
			.append("/")
			.append(myGame.getMaxNumOfPl());
			return sb.toString();
		}
		else if(parts[0].equals(CommunicationCommands.IMAGE_MESSAGE)){
			String[] pixels=parts[2].split(",");
			pixels[0]=pixels[0].replaceAll("[^0-9]", "");
			pixels[pixels.length-1]=pixels[pixels.length-1].replaceAll("[^0-9]", "");
			
			BufferedImage image=new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
			
			int cnt=0;
			int[] rgb=new int[3];
			for(int i=0;i<64;i++){
				for(int j=0;j<64;j++){
					for(int k=0;k<3;k++){
						System.out.println(pixels[cnt]);
						rgb[k]=Integer.parseInt(pixels[cnt++]);
					}
					Color color=new Color(rgb[0], rgb[1], rgb[2]);
					image.setRGB(i, j, color.getRGB());
				}
			}
			try{
				ImageIO.write(image, "png", new File(player.getName()+".png"));
			} catch (IOException e) {}
		}
		return null;
	}

}
