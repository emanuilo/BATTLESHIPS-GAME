package battleships.server;

public class BattleOverseer extends Thread {
	private Game myGame;
	
	
	public BattleOverseer(Game game) {
		myGame=game;
	}
	@Override
	public void run() {
		
		try{
			while(!interrupted()){
				myGame.changeState(new DeployState());
				sleep(30000);
				
				while(true){
					myGame.changeState(new RoundState());
					sleep(60000);
					myGame.changeState(new UpdateState());
				}
			}
		}catch(InterruptedException e){}
	}
}
